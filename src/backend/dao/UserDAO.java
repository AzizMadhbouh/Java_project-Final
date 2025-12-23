package src.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public static String authenticate(String username, String password) throws SQLException {
        String sql = "SELECT mc.role FROM users u " +
                "LEFT JOIN member_clubs mc ON LOWER(TRIM(u.username)) = LOWER(TRIM(mc.email)) " +
                "WHERE LOWER(TRIM(u.username)) = ? AND u.password = ? " +
                "ORDER BY CASE WHEN mc.role = 'Club President' THEN 1 ELSE 2 END ASC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role");
                    return (role != null) ? role : "Normal User";
                }
            }
        }
        return null;
    }

    public static boolean exists(String email) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE LOWER(TRIM(username)) = ? " +
                "UNION " +
                "SELECT 1 FROM members WHERE LOWER(TRIM(email)) = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static void createUser(Connection conn, String email, String password) throws SQLException {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        }
    }

    public static void createMember(Connection conn, String email, String firstName, String lastName)
            throws SQLException {
        String sql = "INSERT INTO members (first_name, last_name, email) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.executeUpdate();
        }
    }
}
