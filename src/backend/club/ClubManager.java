package src.backend.club;

import src.backend.dao.DBConnection;
import src.backend.dao.ClubDAO;
import src.backend.dao.MemberDAO;
import src.backend.utils.BackendUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClubManager {

    public static boolean createClub(String email, String clubName, int maxMembers, String category) {
        if (email == null || clubName == null || clubName.trim().isEmpty()) {
            return false;
        }
        email = BackendUtils.normalize(email);
        clubName = clubName.trim();

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            MemberDAO.ensureMemberExists(conn, email);

            if (MemberDAO.getClubsJoinedCount(conn, email) >= 2) {
                return false;
            }

            if (ClubDAO.isClubNameTaken(conn, clubName)) {
                return false;
            }

            ClubDAO.createClub(conn, clubName, email, maxMembers, category);
            ClubDAO.addMemberToClub(conn, email, clubName, "Club President");
            MemberDAO.updateClubsJoinedCount(conn, email, 1);

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            BackendUtils.rollback(conn);
            return false;
        } finally {
            BackendUtils.cleanup(conn);
        }
    }

    public static List<String> getAllClubs() {
        try {
            return ClubDAO.getAllClubNames();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static boolean joinClub(String email, String clubName) {
        if (email == null || clubName == null) {
            return false;
        }
        email = BackendUtils.normalize(email);
        clubName = clubName.trim();

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            if (ClubDAO.isMemberOfClub(conn, email, clubName)) {
                return false;
            }

            if (!getOwnedClubs(email).isEmpty()) {
                if (MemberDAO.getClubsJoinedCount(conn, email) >= 2) {
                    return false;
                }
            }

            ClubDAO.addMemberToClub(conn, email, clubName, "Normal User");
            MemberDAO.updateClubsJoinedCount(conn, email, 1);

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            BackendUtils.rollback(conn);
            return false;
        } finally {
            BackendUtils.cleanup(conn);
        }
    }

    public static List<ClubDAO.MemberRecord> getMembersByPresident(String presidentEmail) {
        try {
            List<String> ownedClubs = getOwnedClubs(presidentEmail);
            return ClubDAO.getMembersInClubs(ownedClubs);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static boolean quitClub(String email, String clubName) {
        if (email == null || clubName == null) {
            return false;
        }
        email = BackendUtils.normalize(email);
        clubName = clubName.trim();

        List<String> ownedClubs = getOwnedClubs(email);
        for (String owned : ownedClubs) {
            if (clubName.equalsIgnoreCase(owned.trim())) {
                return false;
            }
        }

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            int affectedRows = ClubDAO.removeMemberFromClub(conn, email, clubName);

            if (affectedRows > 0) {
                MemberDAO.updateClubsJoinedCount(conn, email, -1);
            }

            conn.commit();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            BackendUtils.rollback(conn);
            return false;
        } finally {
            BackendUtils.cleanup(conn);
        }
    }

    public static List<String> getJoinedClubs(String email) {
        if (email == null)
            return new ArrayList<>();
        email = BackendUtils.normalize(email);
        try {
            return ClubDAO.getJoinedClubNames(email);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<String> getOwnedClubs(String email) {
        if (email == null)
            return new ArrayList<>();
        email = BackendUtils.normalize(email);
        try {
            return ClubDAO.getOwnedClubNames(email);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static String getPresidentClub(String email) {
        List<String> owned = getOwnedClubs(email);
        return owned.isEmpty() ? null : owned.get(0);
    }
}