package example.memory.conferences;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SyntheticDataGenerator
{
    final static int CONF_NAME_LENGTH_MAX = 20;
    final static int CONF_NAME_LENGTH_MIN = 6;
    final static String DATE_PERIOD_START = "2023-01-01";
    final static String DATE_PERIOD_END = "2023-12-31";


    public static void generateSyntheticData(int numRows, String filePrefix)
    {
        List<String[]> syntheticData = generateSyntheticData(numRows);
        URL data = SyntheticDataGenerator.class.getClassLoader().getResource("data");
        printDataToCSV(syntheticData, data.getPath() + "/" + filePrefix + "_" + numRows + ".csv");
    }

    private static List<String[]> generateSyntheticData(int numRows)
    {
        List<String[]> syntheticData = new ArrayList<>();
        Random random = new Random();

        String[] countries = {"Greece", "Poland", "United States", "Germany", "Romania", "Sweden", "WWW"};
        String[] cities = {"Athens", "Krakow", "Atlanta", "Brühl", "Bucharest", "Stockholm"};
        String[] sessionTypes = {"talks", "workshops", "lightning talks"};

        for (int i = 0; i < numRows; i++)
        {
            int rndNumber = random.nextInt(countries.length);
            String eventName = randomConfName(CONF_NAME_LENGTH_MIN, CONF_NAME_LENGTH_MAX);
            String country = countries[rndNumber];
            String city = country.equals("WWW") ? "Online event" : cities[rndNumber];

            String startDate = randomDate(DATE_PERIOD_START, DATE_PERIOD_END);
            String endDate = randomDate(startDate, DATE_PERIOD_END);

            String sessionTypeListString = randomSessionTypes(sessionTypes);

            String trackCount = String.valueOf(random.nextInt(1, 10));
            String sessionCount = String.valueOf(random.nextInt(50, 250));
            String speakerCount = String.valueOf(random.nextInt(50, 250));
            String cost = String.valueOf(random.nextInt(500, 3000));

            String[] row = {eventName, country, city, startDate, endDate, sessionTypeListString, trackCount, sessionCount, speakerCount, cost};
            syntheticData.add(row);
        }

        return syntheticData;
    }

    public static String randomConfName(int minLength, int maxLength)
    {
        Random random = new Random();

        final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
        int length = random.nextInt(maxLength - minLength + 1) + minLength;

        StringBuilder sb = new StringBuilder(length);

        // Generate first letter as uppercase
        char firstLetter = (char) (random.nextInt(26) + 'A');
        sb.append(firstLetter);

        // Generate remaining characters
        for (int i = 1; i < length; i++)
        {
            char randomChar = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
            sb.append(randomChar);
        }

        return sb.toString();
    }

    private static String randomDate(String startDateStr, String endDateStr)
    {
        try
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = format.parse(startDateStr);
            Date endDate = format.parse(endDateStr);

            long startMillis = startDate.getTime();
            long endMillis = endDate.getTime();

            long randomMillis = startMillis + (long) (Math.random() * (endMillis - startMillis));
            Date randomDate = new Date(randomMillis);

            return format.format(randomDate);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private static String randomSessionTypes(String[] sessionTypes)
    {
        ArrayList<String> selectedTypes = new ArrayList<>(sessionTypes.length);
        Random random = new Random();
        int randomNumberOfElements = random.nextInt(sessionTypes.length + 1);
        int counter = 0;

        for (int i = sessionTypes.length - 1; i >= 0; i--)
        {
            int randomIndex = random.nextInt(i + 1);
            String sessionType = sessionTypes[randomIndex];
            if (counter <= randomNumberOfElements && !selectedTypes.contains(sessionType))
            {
                selectedTypes.add(sessionType);
                counter++;
            }
        }

        return "\"" + selectedTypes + "\"";
    }

    private static void printDataToCSV(List<String[]> data, String csvFileName)
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFileName)))
        {
            String header = "EventName,Country,City,StartDate,EndDate,SessionTypes,TrackCount,SessionCount,SpeakerCount,Cost";
            writer.println(header);

            for (String[] row : data)
            {
                String rowStr = String.join(",", row);
                writer.println(rowStr);
            }

            System.out.println("CSV file '" + csvFileName + "' has been generated.");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
