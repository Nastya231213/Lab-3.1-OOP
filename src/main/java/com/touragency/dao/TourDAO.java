package com.touragency.dao;

import com.touragency.model.Tour;
import com.touragency.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TourDAO {
    public List<Tour> getAllTours() {
        List<Tour> tours = new ArrayList<>();
        String query = "SELECT * FROM tours";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                tours.add(new Tour(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getBigDecimal("price"),
                        rs.getBoolean("is_hot")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tours;
    }

    public void addTour(Tour tour) {
        String query = "INSERT INTO tours (name, type, price, is_hot) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tour.getName());
            stmt.setString(2, tour.getType());
            stmt.setBigDecimal(3, tour.getPrice());
            stmt.setBoolean(4, tour.isHot());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateTour(Tour tour) {
        String query = "UPDATE tours SET name = ?, type = ?, price = ?, is_hot = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tour.getName());
            stmt.setString(2, tour.getType());
            stmt.setBigDecimal(3, tour.getPrice());
            stmt.setBoolean(4, tour.isHot());
            stmt.setInt(5, tour.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteTour(int tourId) {
        String query = "DELETE FROM tours WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, tourId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
