package service;


import models.Client;
import models.Employe;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientManager {
    private Connection connection;

    public ClientManager(Connection connection) {
        this.connection = connection;
    }

    public void add(Client client) throws SQLException {
        String sql = "INSERT INTO client (nom, prenom, email, num_passport, cin, adresse)  VALUES " +
                "('" + client.getNom() + "'," +
                "'" + client.getPrenom() + "'," +
                "'" + client.getEmail() + "'," +
                "'" + client.getNumPassport() + "'," +
                "'" + client.getCin() + "'," +
                "'" + client.getAdresse() + "')";
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    public Client find(int clientId) throws SQLException {
        String sql = "SELECT * FROM client WHERE id = " + clientId;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs == null)
            throw new SQLException("Cannot get role ResultSet");
        Client client = null;
        if (rs.next()) {
            client = parseClient(rs);
        }
        return client;
    }

    private Client parseClient(ResultSet rs) throws SQLException {
        Client client = new Client();
        int id = rs.getInt("id");
        String nom = rs.getString("nom");
        String prenom = rs.getString("prenom");
        String email = rs.getString("email");
        String np = rs.getString("num_passport");
        String cin = rs.getString("cin");
        String adresse = rs.getString("adresse");

        client.setId(id);
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setEmail(email);
        client.setNumPassport(np);
        client.setCin(cin);
        client.setAdresse(adresse);
        return client;
    }

    public List<Client> findAll() throws SQLException {
        String sql = "SELECT * FROM client";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs == null)
            throw new SQLException("Cannot get roles ResultSet");
        List<Client> clients = new ArrayList<>();
        while (rs.next()) {
            Client client = parseClient(rs);
            clients.add(client);
        }
        return clients;
    }


    public void delete(int clientId) throws SQLException {
        String sql = "DELETE FROM client WHERE id = " + clientId;
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    public void update(int clientId, Client client) throws SQLException {
        String sql = "UPDATE client SET " +
                "nom = '" + client.getNom() + "', " +
                "prenom = '" + client.getPrenom() + "', " +
                "email = '" + client.getEmail() + "', " +
                "num_passport = '" + client.getNumPassport() + "', " +
                "cin = '" + client.getCin() + "', " +
                "adresse = '" + client.getAdresse() + "'  " +
                " WHERE id = " + clientId;
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }
    
    public Client findLast() throws SQLException {
        String sql = "SELECT * FROM client ORDER BY id DESC LIMIT 1";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs == null)
            throw new SQLException("Cannot get ResultSet");
        Client client = null;
        if (rs.next()) {
            client = parseClient(rs);
        }
        return client;
    }

}
