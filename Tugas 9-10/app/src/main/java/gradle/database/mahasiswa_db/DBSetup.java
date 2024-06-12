package gradle.database.mahasiswa_db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBSetup {
    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS mahasiswa (\n"
                + " nim TEXT PRIMARY KEY,\n"
                + " nama TEXT NOT NULL,\n"
                + " ipk REAL,\n"
                + " total_sks INTEGER\n"
                + ");";
        try (Connection conn = DBConnect.connect();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            // System.out.println("Tabel mahasiswa telah dibuat atau sudah ada.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
