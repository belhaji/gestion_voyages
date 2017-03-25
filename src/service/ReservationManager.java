package service;


import models.Client;
import models.Ligne;
import models.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationManager {
    private Connection connection;
    private ClientManager cm;
    private LigneManager lm;

    public ReservationManager(Connection connection, ClientManager cm, LigneManager lm) {
        this.connection = connection;
        this.cm = cm;
        this.lm = lm;
    }

    public void add(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservation (client_id, type, ligne_id, class, date) VALUES " +
                "(" + reservation.getClient().getId() + ", " +
                "'" + reservation.getType() + "', " +
                "" + reservation.getLigne().getId() + ", " +
                "'" + reservation.getClasse() + "', " +
                "NOW()) ";
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    public Reservation find(int reservationId) throws SQLException {
        String sql = "SELECT * FROM reservation WHERE id = " + reservationId;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs == null)
            throw new SQLException("Cannot get reservation ResultSet");
        Reservation reservation = null;
        if (rs.next()) {
            reservation = parseReservation(rs);
        }
        return reservation;
    }

    private Reservation parseReservation(ResultSet rs) throws SQLException {

        int id = rs.getInt("id");
        int clientId = rs.getInt("client_id");
        String type = rs.getString("type");
        int ligneId = rs.getInt("ligne_id");
        String classe = rs.getString("class");
        Date date = rs.getDate("date");
        Ligne ligne = lm.find(ligneId);
        Client client = cm.find(clientId);
        Reservation reservation = new Reservation(client, ligne);
        reservation.setId(id);
        reservation.setType(type);
        reservation.setClasse(classe);
        reservation.setDate(date);
        return reservation;
    }

    public List<Reservation> findAll() throws SQLException {
        String sql = "SELECT * FROM reservation";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs == null)
            throw new SQLException("Cannot get Reservations ResultSet");
        List<Reservation> reservations = new ArrayList<>();
        while (rs.next()) {
            Reservation reservation = parseReservation(rs);
            reservations.add(reservation);
        }
        return reservations;
    }

    public void delete(int reservationId) throws SQLException {
        String sql = "DELETE FROM reservation WHERE id = " + reservationId;
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    public void update(int reservationId, Reservation reservation) throws SQLException {
        String sql = "UPDATE reservation SET client_id = " + reservation.getClient().getId() + " ," +
                "type = '" + reservation.getType() + "', " +
                "ligne_id = " + reservation.getLigne().getId() + ", " +
                "date = '" + reservation.getDate() + "', " +
                "class = '" + reservation.getClasse() + "' " +
                " WHERE id = " + reservationId;
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

}
