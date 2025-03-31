package com.touragency.controller;

import com.google.gson.Gson;
import com.touragency.dao.ClientDAO;
import com.touragency.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/clients/*")
public class ClientServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ClientServlet.class);
    private final ClientDAO clientDAO = new ClientDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (pathInfo == null || pathInfo.equals("/")) {
            List<Client> clients = clientDAO.getAllClients();
            log.info("Returned {} clients", clients.size());
            resp.getWriter().write(gson.toJson(clients));
        } else {
            int clientId = Integer.parseInt(pathInfo.substring(1));
            Client client = clientDAO.getClientById(clientId);
            if (client != null) {
                resp.getWriter().write(gson.toJson(client));
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Client not found");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        Client client = gson.fromJson(reader, Client.class);

        clientDAO.addClient(client);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        Client client = gson.fromJson(reader, Client.class);

        boolean success = clientDAO.updateClient(client);
        if (success) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Client not found");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Client ID is required");
            return;
        }

        int clientId = Integer.parseInt(pathInfo.substring(1));
        boolean success = clientDAO.deleteClient(clientId);
        if (success) {
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Client not found");
        }
    }
}
