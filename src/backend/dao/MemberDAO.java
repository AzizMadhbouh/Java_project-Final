package src.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {

    public static void ensureMemberExists(Connection conn, String email) throws SQLException {
        if (email == null)
            return;
        String verifySql = "SELECT 1 FROM members WHERE LOWER(TRIM(email)) = LOWER(TRIM(?))";
        try (PreparedStatement verifyStmt = conn.prepareStatement(verifySql)) {
            verifyStmt.setString(1, email);
            try (ResultSet rs = verifyStmt.executeQuery()) {
                if (!rs.next()) {
                    String repairSql = "INSERT INTO members (email, first_name, last_name, clubs_joined) VALUES (?, 'Member', 'User', 0)";
                    try (PreparedStatement repairStmt = conn.prepareStatement(repairSql)) {
                        repairStmt.setString(1, email);
                        repairStmt.executeUpdate();
                    }
                }
            }
        }
    }

    public static int getClubsJoinedCount(Connection conn, String email) throws SQLException {
        String sql = "SELECT clubs_joined FROM members WHERE LOWER(TRIM(email)) = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("clubs_joined");
                }
            }
        }
        return 0;
    }

    public static boolean updateClubsJoinedCount(Connection conn, String email, int delta) throws SQLException {
        String sql = "UPDATE members SET clubs_joined = clubs_joined + ? WHERE LOWER(TRIM(email)) = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, delta);
            pstmt.setString(2, email);
            return pstmt.executeUpdate() > 0;
        }
    }

    public static String getDisplayName(String email) {
        String sql = "SELECT first_name, last_name FROM members WHERE LOWER(TRIM(email)) = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String f = rs.getString("first_name");
                    String l = rs.getString("last_name");
                    String name = ((f != null ? f : "") + " " + (l != null ? l : "")).trim();
                    return name.isEmpty() ? email : name;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }
}
