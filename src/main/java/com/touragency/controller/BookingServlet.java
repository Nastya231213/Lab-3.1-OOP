package com.touragency.controller;

import com.google.gson.Gson;
import com.touragency.dao.BookingDAO;
import com.touragency.model.ClientTour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/bookings/*")
public class BookingServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(BookingServlet.class);
    private final BookingDAO bookingDAO = new BookingDAO();
    private final Gson gson = new Gson();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<ClientTour> bookings = bookingDAO.getAllBookings();
        log.info("Returned {} bookings", bookings.size());
        resp.getWriter().write(gson.toJson(bookings));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ClientTour booking = gson.fromJson(req.getReader(), ClientTour.class);
        boolean created = bookingDAO.createBooking(booking);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (created) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            log.info("Created booking: {}", gson.toJson(booking));
            resp.getWriter().write("{\"status\": \"created\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Failed to create booking\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ClientTour booking = gson.fromJson(req.getReader(), ClientTour.class);
        boolean updated = bookingDAO.updateBooking(booking);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (updated) {
            log.info("Updated booking: {}", gson.toJson(booking));
            resp.getWriter().write("{\"status\": \"updated\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\": \"Booking not found\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (pathInfo == null || pathInfo.split("/").length < 3) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid URL. Use /bookings/{clientId}/{tourId}\"}");
            return;
        }

        String[] pathParts = pathInfo.split("/");
        try {
            int clientId = Integer.parseInt(pathParts[1]);
            int tourId = Integer.parseInt(pathParts[2]);

            boolean deleted = bookingDAO.deleteBooking(clientId, tourId);
            if (deleted) {
                log.info("Deleted booking: client_id={}, tour_id={}", clientId, tourId);
                resp.getWriter().write("{\"status\": \"deleted\"}");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\": \"Booking not found\"}");
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid ID format\"}");
        }
    }
}
