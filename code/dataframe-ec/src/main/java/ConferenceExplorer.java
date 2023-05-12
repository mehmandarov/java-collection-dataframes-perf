import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.github.vmzakharov.ecdataframe.dataframe.AggregateFunction;
import io.github.vmzakharov.ecdataframe.dataframe.DataFrame;
import io.github.vmzakharov.ecdataframe.dataframe.DfIndex;
import io.github.vmzakharov.ecdataframe.dataframe.DfJoin;
import io.github.vmzakharov.ecdataframe.dataset.CsvDataSet;
import io.github.vmzakharov.ecdataframe.dataset.CsvSchema;
import io.github.vmzakharov.ecdataframe.dsl.function.BuiltInFunctions;
import io.github.vmzakharov.ecdataframe.dsl.function.IntrinsicFunctionDescriptorBuilder;
import io.github.vmzakharov.ecdataframe.dsl.value.LongValue;
import io.github.vmzakharov.ecdataframe.dsl.value.StringValue;
import io.github.vmzakharov.ecdataframe.dsl.value.ValueType;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;

public class ConferenceExplorer
{
    private DataFrame conferences;
    private DataFrame countryCodes;

    public ConferenceExplorer(int year)
    {
        this("yearOf(StartDate) == " + year);
    }

    public ConferenceExplorer(String intialFilter)
    {
        this.loadCountryCodesFromCsv();
        this.loadConferencesFromCsv(intialFilter);
        this.conferences.createIndex("Country", Lists.immutable.with("Country"));
        this.conferences.createIndex("City", Lists.immutable.with("City"));
    }

    private void loadConferencesFromCsv(String initialFilter)
    {
        CsvSchema schema = this.createConferenceSchema();
        URL url = ConferenceExplorer.class.getClassLoader()
                .getResource("data/conferences.csv");
        DataFrame dataFrame =
                new CsvDataSet(url.getPath(), "Conferences", schema)
                        .loadAsDataFrame();
        this.addFunctionsAndColumns(dataFrame);
        this.conferences = dataFrame.selectBy(initialFilter);
    }

    private void addFunctionsAndColumns(DataFrame dataFrame)
    {
        this.addDaysUntilFunctionAndDaysToEventColumn(dataFrame);
        this.addDurationInDaysFunctionAndDurationColumn(dataFrame);
        this.addYearOfFunction();
        this.addMonthOfFunctionAndMonthColumn(dataFrame);
    }

    private static CsvSchema createConferenceSchema()
    {
        CsvSchema conferenceSchema = new CsvSchema().separator(',');
        conferenceSchema.addColumn("EventName", ValueType.STRING);
        conferenceSchema.addColumn("Country", ValueType.STRING);
        conferenceSchema.addColumn("City", ValueType.STRING);
        conferenceSchema.addColumn("StartDate", ValueType.DATE);
        conferenceSchema.addColumn("EndDate", ValueType.DATE);
        conferenceSchema.addColumn("SessionTypes", ValueType.STRING);
        return conferenceSchema;
    }

    private void loadCountryCodesFromCsv()
    {
        CsvSchema schema = this.createCountrySchema();
        URL url = ConferenceExplorer.class.getClassLoader()
                .getResource("data/country_codes.csv");
        DataFrame countryCodesDataFrame =
                new CsvDataSet(url.getPath(), "CountryCodes", schema)
                        .loadAsDataFrame();
        this.addFlagEmojiFunction();
        this.countryCodes = countryCodesDataFrame;
    }

    private CsvSchema createCountrySchema()
    {
        CsvSchema countryCodesSchema = new CsvSchema().separator(',');
        countryCodesSchema.addColumn("Country", ValueType.STRING);
        countryCodesSchema.addColumn("Alpha2Code", ValueType.STRING);
        return countryCodesSchema;
    }

    private void addDaysUntilFunctionAndDaysToEventColumn(DataFrame dataFrame)
    {
        BuiltInFunctions.addFunctionDescriptor(
                new IntrinsicFunctionDescriptorBuilder("daysUntil")
                        .parameterNames("date")
                        .returnType(ValueType.LONG)
                        .action(context -> new LongValue(
                                ChronoUnit.DAYS.between(
                                        LocalDate.now(),
                                        context.getDate("date")))));
        dataFrame.addColumn("DaysToEvent", "daysUntil(StartDate)");
    }

    private void addYearOfFunction()
    {
        BuiltInFunctions.addFunctionDescriptor(new IntrinsicFunctionDescriptorBuilder("yearOf")
                .parameterNames("date")
                .returnType(ValueType.LONG)
                .action(context -> new LongValue(context.getDate("date").getYear())));
    }

    private void addMonthOfFunctionAndMonthColumn(DataFrame dataFrame)
    {
        BuiltInFunctions.addFunctionDescriptor(new IntrinsicFunctionDescriptorBuilder("monthOf")
                .parameterNames("date")
                .returnType(ValueType.STRING)
                .action(context -> new StringValue(context.getDate("date").getMonth().toString())));
        dataFrame.addColumn("Month", "monthOf(StartDate)");
    }

    private void addDurationInDaysFunctionAndDurationColumn(DataFrame dataFrame)
    {
        BuiltInFunctions.addFunctionDescriptor(new IntrinsicFunctionDescriptorBuilder("durationInDays")
                .parameterNames("from", "to")
                .returnType(ValueType.LONG)
                .action(context -> new LongValue(ChronoUnit.DAYS.between(context.getDate("from"), context.getDate("to").plusDays(1L)))));
        dataFrame.addColumn("Duration", "durationInDays(StartDate, EndDate)");
    }

    private void addFlagEmojiFunction()
    {
        BuiltInFunctions.addFunctionDescriptor(new IntrinsicFunctionDescriptorBuilder("toFlagEmoji")
                .parameterNames("countryCode")
                .returnType(ValueType.STRING)
                .action(context -> new StringValue(new CountryToFlagUtil().toFlagEmoji(context.getString("countryCode")))));
    }

    public DataFrame getConferences()
    {
        return this.conferences;
    }

    public DataFrame sortByDaysToEvent()
    {
        return this.conferences.sortBy(Lists.immutable.with("DaysToEvent"));
    }

    public DataFrame countByCountry()
    {
        return this.conferences.aggregateBy(
                Lists.immutable.with(AggregateFunction.count("Country", "CountryCount")),
                Lists.immutable.with("Country"));
    }

    public DataFrame countByMonth()
    {
        return this.conferences.aggregateBy(
                Lists.immutable.with(AggregateFunction.count("Month", "MonthCount")),
                Lists.immutable.with("Month"));
    }

    public DataFrame sumConferenceDaysByCountry()
    {
        return this.conferences.sumBy(
                Lists.immutable.with("Duration"),
                Lists.immutable.with("Country"));
    }

    public DfIndex groupByCountry()
    {
        return this.conferences.index("Country");
    }

    public DfIndex groupByCity()
    {
        return this.conferences.index("City");
    }

    public DataFrame getCountries()
    {
        // Get distinct list of countries
        DataFrame distinctCountries = this.conferences.distinct(Lists.immutable.of("Country"));

        // Join two dataframes on 'Country' key. Add '**unknown**' if there is no hit in the countryCodes dataframe.
        distinctCountries.lookup(DfJoin.to(this.countryCodes)
                .match("Country", "Country")
                .select("Alpha2Code")
                .ifAbsent("**unknown**"));
        distinctCountries.sortBy(Lists.immutable.of("Country"));

        // Generate flag emojis using the two-letter country code and add them as a new column.
        distinctCountries.addColumn("Flag", "toFlagEmoji( Alpha2Code )");

        return distinctCountries;
    }

    public Map<String, DataFrame> groupBySessionType()
    {
        Map<String, DataFrame> groupedBySessionTypes = Maps.mutable.empty();
        groupedBySessionTypes.put("talks", this.conferences.selectBy("contains( SessionTypes, 'talks')"));
        groupedBySessionTypes.put("workshops", this.conferences.selectBy("contains( SessionTypes, 'workshops')"));
        return groupedBySessionTypes;
    }

    public DataFrame countBySessionType()
    {
        Map<String, DataFrame> groupedBySessionTypes = groupBySessionType();
        DataFrame countBySessionTypes = new DataFrame("countBySessionType")
                .addStringColumn("Session Type").addLongColumn("Count")
                .addRow("talks", (long) groupedBySessionTypes.get("talks").rowCount())
                .addRow("workshops", (long) groupedBySessionTypes.get("workshops").rowCount());
        return countBySessionTypes;
    }

    public String outputToJson(Object data)
    {
        try
        {
            return this.getObjectMapper().writeValueAsString(data);
        }
        catch (JsonProcessingException e)
        {
            throw new RuntimeException(e);
        }
    }

    private ObjectMapper getObjectMapper()
    {
        DataFrameCustomSerializer dfSerializer = new DataFrameCustomSerializer(DataFrame.class);
        SimpleModule module =
                new SimpleModule("DataFrameCustomSerializer", new Version(2, 1, 3, null, null, null));
        module.addSerializer(DataFrame.class, dfSerializer);
        return new ObjectMapper().registerModule(module);
    }
}
