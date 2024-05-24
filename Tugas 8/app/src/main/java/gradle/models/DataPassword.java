package gradle.models;

import java.io.FileWriter;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class DataPassword {

    public static final ArrayList<PasswordStore> passData = new ArrayList<>();
    private static final String csvPath = "./datapassword.csv";
    private static final String[] headers = { "name", "username", "password",
            "hashkey", "category", "score" };

    public static void saveCSVData() {
        if (passData.isEmpty()) {
            System.out.println("Data masih kosong (Data is still empty)");
            return;
        }

        try {
            FileWriter writer = new FileWriter("./datapassword.csv");
            CSVFormat formater = CSVFormat.DEFAULT.builder().setHeader(headers).build();

            CSVPrinter printer = new CSVPrinter(writer, formater);
            for (PasswordStore pass : passData) {
                printer.printRecord(pass.name, pass.username, pass.getEncPassword(),
                        pass.getHashkey(), pass.getCategoryCode(), pass.getScore());
            }
            printer.flush();

            System.out.println("Data password berhasil disimpan (Password data saved successfully)");
        } catch (IOException ex) {
            Logger.getLogger(DataPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<PasswordStore> loadCSVData() {
        passData.clear();
        try {
            FileReader reader = new FileReader(csvPath);
            CSVFormat format = CSVFormat.DEFAULT.builder().setHeader(headers)
                    .setSkipHeaderRecord(true).build();
            Iterable<CSVRecord> data = format.parse(reader);
            for (CSVRecord record : data) {
                PasswordStore newPass;
                String name = record.get("name");
                String username = record.get("username");
                String password = record.get("password");
                int category = Integer.parseInt(record.get("category"));
                String hashkey = record.get("hashkey");
                double score = 0.0; // Default score if not available in CSV
                if (record.isSet("score")) {
                    score = Double.parseDouble(record.get("score"));
                }
                if (hashkey == null) {
                    newPass = new PasswordStore(name, username, password, category);
                } else {
                    newPass = new PasswordStore(name, username, password, category, hashkey, score);
                }
                passData.add(newPass);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Data password kosong ...");
        } catch (IOException ex) {
            Logger.getLogger(DataPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
        return passData;
    }

}