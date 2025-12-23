package src.backend.auth;

import src.backend.dao.DBConnection;
import src.backend.dao.UserDAO;
import src.backend.dao.MemberDAO;
import src.backend.dao.ClubDAO;
import src.backend.utils.BackendUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class Register {
    public String firstName;
    public String lastName;
    public String email;
    public String club;
    public String role;

    public String generatedPassword;

    public Register() {
    }

    public boolean save() {
        if (firstName == null || lastName == null || email == null)
            return false;

        String standardizedEmail = BackendUtils.normalize(email);

        Connection conn = null;
        try {
            if (UserDAO.exists(standardizedEmail)) {
                System.out.println("Registration failed: User already exists for email " + standardizedEmail);
                return false;
            }

            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            UserDAO.createMember(conn, standardizedEmail, firstName, lastName);

            if (club != null && !club.trim().isEmpty()) {
                String clubName = club.trim();
                ClubDAO.addMemberToClub(conn, standardizedEmail, clubName, "Normal User");
                MemberDAO.updateClubsJoinedCount(conn, standardizedEmail, 1);
            }

            generatedPassword = BackendUtils.generateRandomPassword();
            UserDAO.createUser(conn, standardizedEmail, generatedPassword);

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            BackendUtils.rollback(conn);
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void cancel() {
    }
}
