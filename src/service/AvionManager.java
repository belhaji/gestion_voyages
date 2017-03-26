package service;


import models.Avion;
import models.Pilote;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AvionManager {
    private PiloteManager pm;
    private Connection connection;

    public AvionManager(PiloteManager pm, Connection connection) {
        this.pm = pm;
        this.connection = connection;
    }

    public void add(Avion avion) throws SQLException {
        String sql = "INSERT INTO avion (nom, nb_place, pilote_id) VALUES " +
                "('" + avion.getNom() + "'," +
                avion.getNbPlace() + ", " +
                avion.getPilote().getId() + ")";

        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    public Avion find(int avionId) throws SQLException {
        String sql = "SELECT * FROM avion WHERE id = " + avionId;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs == null)
            throw new SQLException("Cannot get pilote ResultSet");
        Avion avion = null;
        if (rs.next()) {
            avion = parseAvion(rs);
        }
        return avion;
    }

    private Avion parseAvion(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nom = rs.getString("nom");
        int nb_place = rs.getInt("nb_place");
        int piloteId = rs.getInt("pilote_id");

        Pilote pilote = pm.find(piloteId);

        Avion avion = new Avion(pilote);
        avion.setId(id);
        avion.setNom(nom);
        avion.setNbPlace(nb_place);
        return avion;
    }

    public List<Avion> findAll() throws SQLException {
        String sql = "SELECT * FROM avion";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs == null)
            throw new SQLException("Cannot get avions ResultSet");
        List<Avion> avions = new ArrayList<>();
        while (rs.next()) {
            avions.add(parseAvion(rs));
        }
        return avions;
    }

    public void delete(int avionId) throws SQLException {
        String sql = "DELETE FROM avion WHERE id = " + avionId;
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    public void update(int avionId, Avion avion) throws SQLException {
        String sql = "UPDATE avion SET nom = '" + avion.getNom() + "', " +
                "nb_place = " + avion.getNbPlace() + "," +
                "pilote_id = " + avion.getPilote().getId() +
                " WHERE id = " + avionId;
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }
    public Avion findLast() throws SQLException {
		String sql = "SELECT * FROM avion ORDER BY id DESC LIMIT 1";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		if (rs == null)
			throw new SQLException("Cannot get pilote ResultSet");
		Avion avion = null;
		if (rs.next()) {
			avion = parseAvion(rs);
		}
		return avion;
	}
}
