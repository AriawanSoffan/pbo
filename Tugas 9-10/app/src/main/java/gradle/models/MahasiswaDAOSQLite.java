package gradle.models;

import gradle.MahasiswaDAO;
import gradle.database.mahasiswa_db.DBConnect;

import java.sql.*;
import java.util.*;

public class MahasiswaDAOSQLite implements MahasiswaDAO {
    @Override
    public void insert(Mahasiswa mahasiswa) {
        String sql = "INSERT INTO mahasiswa(nim, nama, ipk, total_sks) VALUES(?,?,?,?)";
        try (Connection conn = DBConnect.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mahasiswa.getNim());
            pstmt.setString(2, mahasiswa.getNama());
            pstmt.setDouble(3, mahasiswa.getIpk());
            pstmt.setInt(4, mahasiswa.getTotalSks());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Mahasiswa selectByNim(String nim) {
        String sql = "SELECT nim, nama, ipk, total_sks FROM mahasiswa WHERE nim = ?";
        Mahasiswa mahasiswa = null;
        try (Connection conn = DBConnect.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nim);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                mahasiswa = new Mahasiswa(
                        rs.getString("nim"),
                        rs.getString("nama"),
                        rs.getInt("total_sks"),
                        rs.getDouble("ipk"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return mahasiswa;
    }

    @Override
    public List<Mahasiswa> selectAll() {
        String sql = "SELECT nim, nama, ipk, total_sks FROM mahasiswa";
        List<Mahasiswa> mahasiswas = new ArrayList<>();
        try (Connection conn = DBConnect.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Mahasiswa mahasiswa = new Mahasiswa(
                        rs.getString("nim"),
                        rs.getString("nama"),
                        rs.getInt("total_sks"),
                        rs.getDouble("ipk"));
                mahasiswas.add(mahasiswa);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return mahasiswas;
    }

    @Override
    public void update(Mahasiswa mahasiswa) {
        String sql = "UPDATE mahasiswa SET nama = ?, ipk = ?, total_sks = ? WHERE nim = ?";
        try (Connection conn = DBConnect.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, mahasiswa.getNama());
            pstmt.setDouble(2, mahasiswa.getIpk());
            pstmt.setInt(3, mahasiswa.getTotalSks());
            pstmt.setString(4, mahasiswa.getNim());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(String nim) {
        String sql = "DELETE FROM mahasiswa WHERE nim = ?";
        try (Connection conn = DBConnect.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nim);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}