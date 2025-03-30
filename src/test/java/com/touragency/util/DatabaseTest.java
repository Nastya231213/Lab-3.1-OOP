package com.touragency.util;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    @Test
    public void testDatabaseConnection() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            assertNotNull(conn, "Database connection should not be null");
            System.out.println("✅ Connection to the database is successful!");
        } catch (SQLException e) {
            fail("❌ Database connection failed: " + e.getMessage());
        }
    }
}