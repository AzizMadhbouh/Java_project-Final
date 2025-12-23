package src.backend.activity;

import src.backend.dao.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Calendar {

    public static class ActivityRecord {
        public String name;
        public String type;
        public String date;
        public String clubName;

        public ActivityRecord(String name, String type, String date, String clubName) {
            this.name = name;
            this.type = type;
            this.date = date;
            this.clubName = clubName;
        }
    }

    public static List<ActivityRecord> getActivities(String email) {
        List<ActivityRecord> activities = new ArrayList<>();
        String sql = "SELECT a.activity_name, a.activity_type, a.activity_date, a.CLUB_NAME " +
                "FROM activities a " +
                "JOIN member_clubs mc ON LOWER(TRIM(a.CLUB_NAME)) = LOWER(TRIM(mc.club_name)) " +
                "WHERE LOWER(TRIM(mc.email)) = LOWER(TRIM(?))";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    activities.add(new ActivityRecord(
                            rs.getString("activity_name"),
                            rs.getString("activity_type"),
                            rs.getString("activity_date"),
                            rs.getString("CLUB_NAME")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activities;
    }
}
