package FileHandling;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CsvProcessor {

    public static void main(String[] args) {
        String inputFilePath = "enrollmentFile.csv";

        try {
            List<Enrollee> enrollees = readCSV(inputFilePath);

            // Separate enrollees by insurance company
            Map<String, List<Enrollee>> companyEnrolleesMap = new HashMap<>();
            for (Enrollee enrollee : enrollees) {
                companyEnrolleesMap
                        .computeIfAbsent(enrollee.insuranceCompany, k -> new ArrayList<>())
                        .add(enrollee);
            }

            // Process each insurance company
            for (Map.Entry<String, List<Enrollee>> entry : companyEnrolleesMap.entrySet()) {
                String companyName = entry.getKey();
                List<Enrollee> companyEnrollees = entry.getValue();

                // Sort by last and first name
                Collections.sort(companyEnrollees, (e1, e2) -> {
                    int lastNameComparison = e1.lastName.compareTo(e2.lastName);
                    return (lastNameComparison != 0) ? lastNameComparison : e1.firstName.compareTo(e2.firstName);
                });

                // Remove duplicates based on the highest version
                Map<String, Enrollee> uniqueEnrolleesMap = new HashMap<>();
                for (Enrollee enrollee : companyEnrollees) {
                    String key = enrollee.userId;
                    if (!uniqueEnrolleesMap.containsKey(key) || enrollee.version > uniqueEnrolleesMap.get(key).version) {
                        uniqueEnrolleesMap.put(key, enrollee);
                    }
                }

                // Write to a new CSV file
                String outputFilePath = companyName + "_enrollees.csv";
                writeCSV(outputFilePath, new ArrayList<>(uniqueEnrolleesMap.values()));
            }

            System.out.println("Processing completed.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Enrollee> readCSV(String filePath) throws IOException {
        List<Enrollee> enrollees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] item = line.split(",");
                if (item.length == 5) {
                    Enrollee enrollee = new Enrollee(item[0], item[1], item[2], Integer.parseInt(item[3]), item[4]);
                    enrollees.add(enrollee);
                }
            }
        }
        return enrollees;
    }

    private static void writeCSV(String filePath, List<Enrollee> enrollees) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("UserId,FirstName,LastName,Version,InsuranceCompany\n");
            for (Enrollee enrollee : enrollees) {
                writer.write(String.format("%s,%s,%s,%d,%s\n",
                        enrollee.userId, enrollee.firstName, enrollee.lastName, enrollee.version, enrollee.insuranceCompany));
            }
        }
    }
}