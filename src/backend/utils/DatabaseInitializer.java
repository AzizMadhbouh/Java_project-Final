package src.backend.utils;

import src.backend.dao.DBConnection;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

 
public class DatabaseInitializer {

    public static void main(String[] args) {
        try {
            System.out.println("Initializing SQLite database...");

            

            String schema = new String(Files.readAllBytes(Paths.get("schema.sql")));

            

            String[] statements = schema.split(";");

            

            try (Connection conn = DBConnection.getConnection();
                    Statement stmt = conn.createStatement()) {

                for (String sql : statements) {
                    String trimmed = sql.trim();
                    if (!trimmed.isEmpty() && !trimmed.startsWith("--")) {
                        stmt.execute(trimmed);
                    }
                }

                System.out.println("✓ Database initialized successfully!");
                System.out.println("✓ Database file created: club_app.db");
                System.out.println("✓ Tables created: users, members, clubs, member_clubs, activities");

            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading schema.sql: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
