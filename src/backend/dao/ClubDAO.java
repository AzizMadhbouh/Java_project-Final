package src.backend.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClubDAO {

    public static boolean isClubNameTaken(Connection conn, String clubName) throws SQLException {
        String sql = "SELECT 1 FROM clubs WHERE LOWER(TRIM(name)) = LOWER(TRIM(?))";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, clubName);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static boolean isMemberOfClub(Connection conn, String email, String clubName) throws SQLException {
        String sql = "SELECT 1 FROM member_clubs WHERE LOWER(TRIM(email)) = ? AND LOWER(TRIM(club_name)) = LOWER(TRIM(?))";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, clubName);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static void createClub(Connection conn, String clubName, String presidentEmail, int maxMembers,
            String category) throws SQLException {
        String sql = "INSERT INTO clubs (name, president_email, max_members, activity_category) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, clubName);
            pstmt.setString(2, presidentEmail);
            pstmt.setInt(3, maxMembers);
            pstmt.setString(4, category);
            pstmt.executeUpdate();
        }
    }

    public static void addMemberToClub(Connection conn, String email, String clubName, String role)
            throws SQLException {
        String sql = "INSERT INTO member_clubs (email, club_name, role) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, clubName);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
        }
    }

    public static int removeMemberFromClub(Connection conn, String email, String clubName) throws SQLException {
        String sql = "DELETE FROM member_clubs WHERE LOWER(TRIM(email)) = ? AND LOWER(TRIM(club_name)) = LOWER(TRIM(?))";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, clubName);
            return pstmt.executeUpdate();
        }
    }

    public static List<String> getAllClubNames() throws SQLException {
        List<String> clubs = new ArrayList<>();
        String sql = "SELECT name FROM clubs";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                clubs.add(rs.getString("name"));
            }
        }
        return clubs;
    }

    public static List<String> getJoinedClubNames(String email) throws SQLException {
        List<String> clubs = new ArrayList<>();
        String sql = "SELECT club_name FROM member_clubs WHERE LOWER(TRIM(email)) = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    clubs.add(rs.getString("club_name"));
                }
            }
        }
        return clubs;
    }

    public static List<String> getOwnedClubNames(String email) throws SQLException {
        List<String> clubs = new ArrayList<>();
        String sql = "SELECT name FROM clubs WHERE LOWER(TRIM(president_email)) = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    clubs.add(rs.getString("name"));
                }
            }
        }
        return clubs;
    }

    public static class MemberRecord {
        public String firstName;
        public String lastName;
        public String email;
        public String clubName;

        public MemberRecord(String firstName, String lastName, String email, String clubName) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.clubName = clubName;
        }
    }

    public static List<MemberRecord> getMembersInClubs(List<String> clubNames) throws SQLException {
        List<MemberRecord> members = new ArrayList<>();
        String sql = "SELECT m.first_name, m.last_name, m.email, mc.club_name FROM members m " +
                "JOIN member_clubs mc ON LOWER(TRIM(m.email)) = LOWER(TRIM(mc.email)) " +
                "WHERE LOWER(TRIM(mc.club_name)) = LOWER(TRIM(?))";
        try (Connection conn = DBConnection.getConnection()) {
            for (String clubName : clubNames) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, clubName);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            members.add(new MemberRecord(
                                    rs.getString("first_name"),
                                    rs.getString("last_name"),
                                    rs.getString("email"),
                                    rs.getString("club_name")));
                        }
                    }
                }
            }
        }
        return members;
    }
}
