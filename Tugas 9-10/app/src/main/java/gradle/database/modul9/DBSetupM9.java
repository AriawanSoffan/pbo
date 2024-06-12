package gradle.database.modul9;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DBSetupM9 {
        public static void createTable() {
                String createUserTable = "CREATE TABLE IF NOT EXISTS userdata (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "username TEXT UNIQUE, " +
                                "password TEXT, " +
                                "fullname TEXT);";

                String createFolderTable = "CREATE TABLE IF NOT EXISTS folder (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "name TEXT);";

                String createPasswordStoreTable = "CREATE TABLE IF NOT EXISTS passwordstore (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "name TEXT, " +
                                "username TEXT, " +
                                "password TEXT, " +
                                "hashkey TEXT, " +
                                "score REAL, " +
                                "category INTEGER, " +
                                "user_id INTEGER, " +
                                "folder_id INTEGER, " +
                                "CONSTRAINT passwordstore_user_FK FOREIGN KEY (user_id) REFERENCES userdata(id) " +
                                "ON DELETE RESTRICT ON UPDATE RESTRICT, " +
                                "CONSTRAINT passwordstore_folder_FK FOREIGN KEY (folder_id) REFERENCES folder(id) " +
                                "ON DELETE SET NULL ON UPDATE SET NULL);";

                String createAdditionalTable = "CREATE TABLE IF NOT EXISTS additional (" +
                                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                                "entry_key TEXT, " +
                                "entry_value TEXT, " +
                                "is_password INTEGER, " +
                                "password_id INTEGER, " +
                                "CONSTRAINT additional_passwordstore_FK FOREIGN KEY (password_id) REFERENCES " +
                                "passwordstore(id) ON DELETE CASCADE ON UPDATE CASCADE);";

                try (Connection conn = DBConnectM9.connect();
                                Statement stmt = conn.createStatement()) {
                        stmt.execute(createUserTable);
                        stmt.execute(createFolderTable);
                        stmt.execute(createPasswordStoreTable);
                        stmt.execute(createAdditionalTable);
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }
}
