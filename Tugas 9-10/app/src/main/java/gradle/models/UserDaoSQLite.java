package gradle.models;

import gradle.dao.UserDAO;
import gradle.database.modul9.DBConnectM9;
import gradle.entities.UserData;
import java.sql.*;

public class UserDaoSQLite implements UserDAO {
    private Connection conn;

    public UserDaoSQLite() throws SQLException {
        this.conn = DBConnectM9.connect();
    }

    public int register(String username, String password, String fullname) {
        int id = 0;
        String query = "insert into userdata values (null, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            UserData newUser = new UserData(username, password, fullname);
            stmt.setString(1, newUser.username);
            stmt.setString(2, newUser.getPassword());
            stmt.setString(3, newUser.fullname);
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public UserData login(String username, String password) {
        UserData user = null;
        String query = "select * from userdata where username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet result = stmt.executeQuery();

            if (!result.isAfterLast()) {
                result.next();
                if (UserData.checkPassword(password, result.getString("password"))) {
                    user = new UserData(result.getInt("id"),
                            result.getString("username"),
                            result.getString("password"),
                            result.getString("fullname"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public int update(int id, String fullname) {
        String query = "update userdata set fullname = ? where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, fullname);
            stmt.setInt(2, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(int id, String password, String fullname) {
        String query = "update userdata set fullname = ?, password = ? where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            UserData newUser = new UserData("", password, fullname);
            stmt.setString(1, newUser.fullname);
            stmt.setString(2, newUser.getPassword());
            stmt.setInt(3, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(int id) {
        String query = "delete from userdata where id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
