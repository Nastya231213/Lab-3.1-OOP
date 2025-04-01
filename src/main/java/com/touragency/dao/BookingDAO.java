package com.touragency.dao;

import com.touragency.model.ClientTour;
import com.touragency.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public List<ClientTour> getAllBookings() {
        List<ClientTour> bookings = new ArrayList<>();
        String query = "SELECT * FROM client_tour";
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

    public boolean createBooking(ClientTour booking) {
        String query = "INSERT INTO client_tour (client_id, tour_id, discount, payment_status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, booking.getClientId());
            stmt.setInt(2, booking.getTourId());
            stmt.setBigDecimal(3, booking.getDiscount());
            stmt.setBoolean(4, booking.isPaymentStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBooking(ClientTour booking) {
        String query = "UPDATE client_tour SET discount = ?, payment_status = ? WHERE client_id = ? AND tour_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBigDecimal(1, booking.getDiscount());
            stmt.setBoolean(2, booking.isPaymentStatus());
            stmt.setInt(3, booking.getClientId());
            stmt.setInt(4, booking.getTourId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBooking(int clientId, int tourId) {
        String query = "DELETE FROM client_tour WHERE client_id = ? AND tour_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            stmt.setInt(2, tourId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
