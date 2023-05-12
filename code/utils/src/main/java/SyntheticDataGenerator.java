import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SyntheticDataGenerator {

    public static void main(String[] args) {
        List<String[]> syntheticData = generateSyntheticData(100);
        printDataToCSV(syntheticData);
    }

    private static List<String[]> generateSyntheticData(int numRows) {
        List<String[]> syntheticData = new ArrayList<>();
        Random random = new Random();

        String[] countries = {"Greece", "Poland", "United States", "Germany", "Romania", "Sweden", "WWW"};
        String[] cities = {"Athens", "Krakow", "Atlanta", "Brühl", "Bucharest", "Stockholm"};
        String[] sessionTypes = {"talks", "workshops"};

        for (int i = 0; i < numRows; i++) {
            int rndNumber = random.nextInt(countries.length);
            String eventName = "Synthetically Generated Event " + (i + 1);
            String country = countries[rndNumber];
            String city = country.equals("WWW") ? "Online event" : cities[rndNumber];

            String startDate = randomDate("2022-01-01", "2022-12-31");
            String endDate = randomDate(startDate, "2022-12-31");

            String[] sessionTypeArray = randomSessionTypes(sessionTypes);
            String sessionTypesStr = String.join(",", sessionTypeArray);

            String[] row = {eventName, country, city, startDate, endDate, sessionTypesStr};
            syntheticData.add(row);
        }

        return syntheticData;
    }

    private static String randomDate(String startDateStr, String endDateStr) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = format.parse(startDateStr);
            Date endDate = format.parse(endDateStr);

            long startMillis = startDate.getTime();
            long endMillis = endDate.getTime();

            long randomMillis = startMillis + (long) (Math.random() * (endMillis - startMillis));
            Date randomDate = new Date(randomMillis);

            return format.format(randomDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String[] randomSessionTypes(String[] sessionTypes) {
        Random random = new Random();
        int numTypes = random.nextInt(2) + 1;  // Randomly choose 1 or 2 types

        String[] selectedTypes = new String[numTypes];
        List<String> sessionTypesList = List.of(sessionTypes);

        for (int i = 0; i < numTypes; i++) {
            String type = sessionTypesList.get(random.nextInt(sessionTypesList.size()));
            selectedTypes[i] = type;
        }

        return selectedTypes;
    }

    private static void printDataToCSV(List<String[]> data) {
        String csvFileName = "synthetic_data.csv";
        try (PrintWriter writer = new PrintWriter(new FileWriter(csvFileName))) {
            String header = "Event Name,Country,City,Start Date,End Date,Session Types";
            writer.println(header);

            for (String[] row : data) {
                String rowStr = String.join(",", row);
                writer.println(rowStr);
            }

            System.out.println("CSV file '" + csvFileName + "' has been generated.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}