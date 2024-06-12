package gradle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import gradle.models.Mahasiswa;

public class Loader {
    public static ArrayList<Mahasiswa> csvMhsLoad() {
        String path = "app\\src\\main\\java\\gradle\\data-mahasiswa.csv";
        ArrayList<Mahasiswa> dataMhs = new ArrayList<>();
        String[] headers = { "nim", "nama", "sksTotal", "ipk" };
        dataMhs.clear();
        try {
            FileReader reader = new FileReader(path);
            Iterable<CSVRecord> recordData = CSVFormat.DEFAULT.builder().setHeader(headers)
                    .setSkipHeaderRecord(true)
                    .build().parse(reader);
            for (CSVRecord record : recordData) {
                dataMhs.add(new Mahasiswa(record.get("nim"), record.get("nama"),
                        Integer.parseInt(record.get("sksTotal")),
                        Double.parseDouble(record.get("ipk"))));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return dataMhs;
    }
}
