package src.backend.activity;

import src.backend.dao.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddActivity {
    public String activity;
    public String type;
    public String date;
    public String clubName;

    public AddActivity() {
    }

    public boolean save() {
        if (activity == null || activity.trim().isEmpty())
            return false;

        String sql = "INSERT INTO activities (activity_name, activity_type, activity_date, CLUB_NAME) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, activity.trim());
            pstmt.setString(2, (type != null) ? type.trim() : "");
            pstmt.setString(3, (date != null) ? date.trim() : "");
            pstmt.setString(4, (clubName != null) ? clubName.trim() : "");

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}