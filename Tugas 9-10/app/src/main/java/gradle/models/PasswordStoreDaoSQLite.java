package gradle.models;

import java.sql.*;
import java.util.ArrayList;

import gradle.dao.PasswordStoreDAO;
import gradle.database.modul9.DBConnectM9;
import gradle.entities.Folder;
import gradle.entities.PasswordStores;
import gradle.entities.UserData;

public class PasswordStoreDaoSQLite implements PasswordStoreDAO {
    private Connection conn;

    public PasswordStoreDaoSQLite() throws SQLException {
        this.conn = DBConnectM9.connect();
        ;
    }

    @Override
    public int addPassword(PasswordStores newPassword, UserData user) {
        String query = "INSERT INTO passwordstore (name, username, password, hashkey, score, category,folder_id, user_id ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, newPassword.name);
            stmt.setString(2, newPassword.username);
            stmt.setString(3, newPassword.getEncPassword());
            stmt.setString(4, newPassword.hashkey);
            stmt.setDouble(5, newPassword.getScore());
            stmt.setInt(6, newPassword.getCategoryCode());
            stmt.setInt(7, user.id);
            stmt.setInt(8, newPassword.folder != null ? newPassword.folder.id : 0);

            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public ArrayList<PasswordStores> listPassword(UserData user) {
        String query = "SELECT p.*, f.name AS folder_name FROM passwordstore p LEFT JOIN folder f ON p.folder_id = f.id WHERE user_id = ?";
        ArrayList<PasswordStores> passwords = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, user.id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Folder folder = new Folder(rs.getInt("folder_id"), rs.getString("folder_name"));
                PasswordStores passwordStore = new PasswordStores(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("hashkey"),
                        rs.getDouble("score"),
                        rs.getInt("category"),
                        folder);
                passwords.add(passwordStore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passwords;
    }

    @Override
    public ArrayList<PasswordStores> findPassword(String name, UserData user) {
        String query = "SELECT p.*, f.name AS folder_name FROM passwordstore p LEFT JOIN folder f ON p.folder_id = f.id WHERE p.name = ? AND p.user_id = ?";
        ArrayList<PasswordStores> passwords = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, user.id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Folder folder = new Folder(rs.getInt("folder_id"), rs.getString("folder_name"));
                PasswordStores passwordStore = new PasswordStores(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("hashkey"),
                        rs.getDouble("score"),
                        rs.getInt("category"),
                        folder);
                passwords.add(passwordStore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passwords;
    }

    @Override
    public int updatePass(PasswordStores changedPass) {
        String query = "UPDATE passwordstore SET name = ?, username = ?, password = ?, hashkey = ?, score = ?, category = ?, folder_id = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, changedPass.name);
            stmt.setString(2, changedPass.username);
            stmt.setString(3, changedPass.getEncPassword());
            stmt.setString(4, changedPass.hashkey);
            stmt.setDouble(5, changedPass.getScore());
            stmt.setInt(6, changedPass.getCategoryCode());
            stmt.setInt(7, changedPass.folder != null ? changedPass.folder.id : null);
            stmt.setInt(8, changedPass.id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int deletePass(PasswordStores deletedPass) {
        String query = "DELETE FROM passwordstore WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, deletedPass.id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
