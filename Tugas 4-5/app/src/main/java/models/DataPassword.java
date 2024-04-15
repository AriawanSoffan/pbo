import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class DataPassword {

    public static final ArrayList<PasswordStore> passData = new ArrayList<>();
    private static final Path csvPath = Paths.get("./datapassword.csv");
    private static final String[] headers = { "name", "username", "password",
            "hashkey", "category", "score" };

    public static void saveCSVData() {
        if (passData.isEmpty()) {
            System.out.println(getMessage("empty_data_message"));
            return;
        }

        try (CSVPrinter printer = CSVFormat.DEFAULT.builder().setHeader(headers).build()
                .print(new FileWriter(csvPath.toFile()))) {
            for (PasswordStore pass : passData) {
                printer.printRecord(pass.name, pass.username, pass.getEncPassword(),
                        pass.getHashkey(), pass.getCategoryCode(), pass.getScore());
            }
            printer.flush();

            System.out.println(getMessage("data_saved_message"));
        } catch (IOException ex) {
            Logger.getLogger(DataPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<PasswordStore> loadCSVData() {
        passData.clear();
        try (CSVParser parser = CSVFormat.DEFAULT.builder().setHeader(headers)
                .setSkipHeaderRecord(true).build().parse(new FileReader(csvPath.toFile()))) {
            for (CSVRecord record : parser) {
                PasswordStore newPass;
                String name = record.get("name");
                String username = record.get("username");
                String password = record.get("password");
                int category = Integer.parseInt(record.get("category"));
                String hashkey = record.get("hashkey");
                double score = record.isSet("score") ? Double.parseDouble(record.get("score")) : 0.0;

                newPass = hashkey == null ? new PasswordStore(name, username, password, category)
                                           : new PasswordStore(name, username, password, category, hashkey, score);
                passData.add(newPass);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(getMessage("empty_data_message"));
        } catch (IOException ex) {
            Logger.getLogger(DataPassword.class.getName()).log(Level.SEVERE, null, ex);
        }
        return passData;
    }

    private static String getMessage(String key) {
        // Implement your logic to retrieve messages from a message bundle or properties file.
        // Return messages based on the provided key.
        return "Message for key: " + key;
    }
}
