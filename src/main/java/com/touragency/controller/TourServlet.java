package com.touragency.controller;


import com.google.gson.Gson;
import com.touragency.dao.TourDAO;
import com.touragency.model.Tour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/tours/*")
public class TourServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(TourServlet.class);
    private final TourDAO tourDAO = new TourDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Tour> tours = tourDAO.getAllTours();
        log.info("Returned {} tours", tours.size());
        resp.getWriter().write(gson.toJson(tours));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        Tour tour = gson.fromJson(reader, Tour.class);

        tourDAO.addTour(tour);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        Tour tour = gson.fromJson(reader, Tour.class);

        boolean success = tourDAO.updateTour(tour);
        if (success) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Tour not found");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int tourId = Integer.parseInt(req.getPathInfo().substring(1));
        boolean success = tourDAO.deleteTour(tourId);
        if (success) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Tour not found");
        }
    }
}
