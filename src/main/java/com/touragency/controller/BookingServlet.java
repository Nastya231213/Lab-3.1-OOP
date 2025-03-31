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
import java.io.BufferedReader;
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
}
