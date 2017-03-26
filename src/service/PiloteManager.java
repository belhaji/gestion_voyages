package service;

import models.Ligne;
import models.Pilote;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PiloteManager {
	private Connection connection;

	public PiloteManager(Connection connection) {
		this.connection = connection;
	}

	public void add(Pilote pilote) throws SQLException {
		String sql = "INSERT INTO pilote (nom, prenom, matricule) " + " VALUES ('" + pilote.getNom() + "'," + "'"
				+ pilote.getPrenom() + "', " + "'" + pilote.getMatricule() + "')";

		Statement statement = connection.createStatement();
		statement.execute(sql);
	}

	public Pilote find(int piloteId) throws SQLException {
		String sql = "SELECT * FROM pilote WHERE id = " + piloteId;

		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		if (rs == null)
			throw new SQLException("Cannot get pilote ResultSet");
		Pilote pilote = null;
		if (rs.next()) {
			pilote = parsePilote(rs);
		}
		return pilote;
	}

	private Pilote parsePilote(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String nom = rs.getString("nom");
		String prenom = rs.getString("prenom");
		String matricule = rs.getString("matricule");
		Pilote p = new Pilote();
		p.setId(id);
		p.setNom(nom);
		p.setPrenom(prenom);
		p.setMatricule(matricule);
		return p;
	}

	public List<Pilote> findAll() throws SQLException {
		String sql = "SELECT * FROM pilote";

		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		if (rs == null)
			throw new SQLException("Cannot get pilotes ResultSet");
		List<Pilote> pilotes = new ArrayList<>();
		while (rs.next()) {
			pilotes.add(parsePilote(rs));
		}
		return pilotes;
	}

	public void delete(int piloteId) throws SQLException {
		String sql = "DELETE FROM pilote WHERE id = " + piloteId;
		Statement statement = connection.createStatement();
		statement.execute(sql);
	}

	public void update(int piloteId, Pilote pilote) throws SQLException {
		String sql = "UPDATE pilote SET nom = '" + pilote.getNom() + "', " + "prenom = '" + pilote.getPrenom() + "',"
				+ "matricule = '" + pilote.getMatricule() + "'" + " WHERE id = " + piloteId;
		Statement statement = connection.createStatement();
		statement.execute(sql);
	}

	public Pilote findLast() throws SQLException {
		String sql = "SELECT * FROM pilote ORDER BY id DESC LIMIT 1";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		if (rs == null)
			throw new SQLException("Cannot get pilote ResultSet");
		Pilote pilote = null;
		if (rs.next()) {
			pilote = parsePilote(rs);
		}
		return pilote;
	}

	public Pilote findByMatricule(String mat) throws SQLException {
		String sql = "SELECT * FROM pilote WHERE matricule = '" + mat + "'";
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		if (rs == null)
			throw new SQLException("Cannot get pilote ResultSet");
		Pilote pilote = null;
		if (rs.next()) {
			pilote = parsePilote(rs);
		}
		return pilote;
	}
}
