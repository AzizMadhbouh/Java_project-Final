package src.backend.auth;

import src.backend.dao.UserDAO;
import src.backend.dao.MemberDAO;
import src.backend.utils.BackendUtils;

import java.sql.SQLException;

public class Login {

    public static String authenticate(String user, String pass) {
        if (user == null || pass == null)
            return null;

        try {
            return UserDAO.authenticate(BackendUtils.normalize(user), pass);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDisplayName(String user) {
        if (user == null)
            return "User";
        return MemberDAO.getDisplayName(BackendUtils.normalize(user));
    }
}
