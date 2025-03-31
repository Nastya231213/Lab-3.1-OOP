package com.touragency.dao;
import com.touragency.model.ClientTour;
import com.touragency.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    public List<ClientTour> getAllBookings() {
        List<ClientTour> bookings = new ArrayList<>();
        String query = "SELECT * FROM bookings";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                bookings.add(new ClientTour(
                        rs.getInt("client_id"),
                        rs.getInt("tour_id"),
                        rs.getBigDecimal("discount"),
                        rs.getBoolean("payment_status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }
}
