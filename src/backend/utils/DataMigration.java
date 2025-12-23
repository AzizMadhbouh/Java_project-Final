
package src.backend.utils;

import src.backend.dao.DBConnection;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DataMigration {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (Statement stmt = conn.createStatement()) {
                
                
                
                
                

                System.out.println("Starting email standardization...");

                
                try {
                    stmt.executeUpdate("UPDATE members SET email = LOWER(TRIM(email))");
                    stmt.executeUpdate("UPDATE users SET username = LOWER(TRIM(username))");
                    stmt.executeUpdate("UPDATE member_clubs SET email = LOWER(TRIM(email))");
                    stmt.executeUpdate("UPDATE clubs SET president_email = LOWER(TRIM(president_email))");
                    conn.commit();
                    System.out.println("Successfully standardized all emails to lowercase.");
                } catch (SQLException e) {
                    System.err.println("Migration failed: " + e.getMessage());
                    conn.rollback();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
