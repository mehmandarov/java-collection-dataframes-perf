package example.jtablesaw.conferences;

import tech.tablesaw.api.ColumnType;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvReadOptions;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class ConferenceExplorer
{
    private Table conferences;
    private Table countryCodes;

    private static String DATA_CONFERENCES_CSV = "data/conferences.csv";
    public static void setCSVSize(int size)
    {
        if (size > 0)
        {
            DATA_CONFERENCES_CSV = "data/conferences_" + size + ".csv";
        }
        else
        {
            DATA_CONFERENCES_CSV = "data/conferences.csv";
        }
    }

    public ConferenceExplorer(int year)
    {
        this.loadCountryCodesFromCsv();
        this.loadConferencesFromCsv(year);
    }

    private void loadConferencesFromCsv(int year)
    {
        URL url = ConferenceExplorer.class.getClassLoader().getResource(DATA_CONFERENCES_CSV);
        ColumnType[] columnTypes = this.createConferenceSchema();
        Table table = null;
        try
        {
            table = Table.read()
                    .usingOptions(CsvReadOptions.builder(url)
                            .columnTypes(columnTypes));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        this.addFunctionsAndColumns(table);
        this.conferences = table.where(table.dateColumn("StartDate").isInYear(year));
    }

    private void addFunctionsAndColumns(Table table)
    {
//        this.addDaysUntilFunctionAndDaysToEventColumn(table);
//        this.addDurationInDaysFunctionAndDurationColumn(table);
//        this.addYearOfFunction();
//        this.addMonthOfFunctionAndMonthColumn(table);
    }

    private static ColumnType[] createConferenceSchema()
    {
        return new ColumnType[]{
                ColumnType.STRING,
                ColumnType.STRING,
                ColumnType.STRING,
                ColumnType.LOCAL_DATE,
                ColumnType.LOCAL_DATE,
                ColumnType.STRING,
                ColumnType.SHORT,
                ColumnType.SHORT,
                ColumnType.SHORT,
                ColumnType.SHORT};
//                ColumnType.LONG,
//                ColumnType.LONG,
//                ColumnType.LONG,
//                ColumnType.LONG};
    }

    private void loadCountryCodesFromCsv()
    {
        URL url = ConferenceExplorer.class.getClassLoader()
                .getResource("data/country_codes.csv");
        Table table = Table.read().csv(url);
//        this.addFlagEmojiFunction();
        this.countryCodes = table;
    }

//    private void addDaysUntilFunctionAndDaysToEventColumn(Table table)
//    {
//        table.dateColumn("StateDate").da
//        BuiltInFunctions.addFunctionDescriptor(
//                new IntrinsicFunctionDescriptorBuilder("daysUntil")
//                        .parameterNames("date")
//                        .returnType(ValueType.LONG)
//                        .action(context -> new LongValue(
//                                ChronoUnit.DAYS.between(
//                                        LocalDate.now(),
//                                        context.getDate("date")))));
//        dataFrame.addColumn("DaysToEvent", "daysUntil(StartDate)");
//    }
//
//    private void addYearOfFunction()
//    {
//        BuiltInFunctions.addFunctionDescriptor(new IntrinsicFunctionDescriptorBuilder("yearOf")
//                .parameterNames("date")
//                .returnType(ValueType.LONG)
//                .action(context -> new LongValue(context.getDate("date").getYear())));
//    }
//
//    private void addMonthOfFunctionAndMonthColumn(DataFrame dataFrame)
//    {
//        BuiltInFunctions.addFunctionDescriptor(new IntrinsicFunctionDescriptorBuilder("monthOf")
//                .parameterNames("date")
//                .returnType(ValueType.STRING)
//                .action(context -> new StringValue(context.getDate("date").getMonth().toString())));
//        dataFrame.addColumn("Month", "monthOf(StartDate)");
//    }
//
//    private void addDurationInDaysFunctionAndDurationColumn(DataFrame dataFrame)
//    {
//        BuiltInFunctions.addFunctionDescriptor(new IntrinsicFunctionDescriptorBuilder("durationInDays")
//                .parameterNames("from", "to")
//                .returnType(ValueType.LONG)
//                .action(context -> new LongValue(ChronoUnit.DAYS.between(context.getDate("from"), context.getDate("to").plusDays(1L)))));
//        dataFrame.addColumn("Duration", "durationInDays(StartDate, EndDate)");
//    }
//
//    private void addFlagEmojiFunction()
//    {
//        BuiltInFunctions.addFunctionDescriptor(new IntrinsicFunctionDescriptorBuilder("toFlagEmoji")
//                .parameterNames("countryCode")
//                .returnType(ValueType.STRING)
//                .action(context -> new StringValue(new CountryToFlagUtil().toFlagEmoji(context.getString("countryCode")))));
//    }

    public Table getConferences()
    {
        return this.conferences;
    }

    public Table sortByDaysToEvent()
    {
        return this.conferences.sortOn("DaysToEvent");
    }

    public Table countByCountry()
    {
        return this.conferences.countBy("Country");
    }

    public Table countByMonth()
    {
        return this.conferences.countBy("Month");
    }

    public Table sumConferenceDaysByCountry()
    {
        return null;
    }

    public Table groupByCountry()
    {
        return null;
    }

    public Table groupByCity()
    {
        return null;
    }

    public Table getCountries()
    {
//        // Get distinct list of countries
//        DataFrame distinctCountries = this.conferences.distinct(Lists.immutable.of("Country"));
//
//        // Join two dataframes on 'Country' key. Add '**unknown**' if there is no hit in the countryCodes dataframe.
//        distinctCountries.lookup(DfJoin.to(this.countryCodes)
//                .match("Country", "Country")
//                .select("Alpha2Code")
//                .ifAbsent("**unknown**"));
//        distinctCountries.sortBy(Lists.immutable.of("Country"));
//
//        // Generate flag emojis using the two-letter country code and add them as a new column.
//        distinctCountries.addColumn("Flag", "toFlagEmoji( Alpha2Code )");
//
        return null;
    }

    public Map<String, Table> groupBySessionType()
    {
        return null;
    }

    public Table countBySessionType()
    {
        return null;
    }

    public String outputToJson(Object data)
    {
        return null;
    }
}
