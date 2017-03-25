package service;


import models.Ligne;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LigneManager {
    private Connection connection;

    public LigneManager(Connection connection) {
        this.connection = connection;
    }

    public void add(Ligne ligne) throws SQLException {
        String sql = "INSERT INTO ligne (airoport_aller, airoport_arriver, prix_class, prix_normale) VALUES " +
                "('" + ligne.getAiroportAller() + "'," +
                "'" + ligne.getAiroportArriver() + "'," +
                ligne.getPrixClass() + "," +
                ligne.getPrixNormale() + ")";

        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    public Ligne find(int ligneId) throws SQLException {
        String sql = "SELECT * FROM ligne WHERE id = " + ligneId;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs == null)
            throw new SQLException("Cannot get role ResultSet");
        Ligne ligne = null;
        if (rs.next()) {
            ligne = parseLigne(rs);
        }
        return ligne;
    }

    private Ligne parseLigne(ResultSet rs) throws SQLException {
        Ligne ligne = new Ligne();
        int id = rs.getInt("id");
        String aeroAller = rs.getString("airoport_aller");
        String aeroArriver = rs.getString("airoport_arriver");
        double pn = rs.getDouble("prix_normale");
        double pc = rs.getDouble("prix_class");

        ligne.setId(id);
        ligne.setAiroportAller(aeroAller);
        ligne.setAiroportArriver(aeroArriver);
        ligne.setPrixClass(pc);
        ligne.setPrixNormale(pn);
        return ligne;
    }

    public List<Ligne> findAll() throws SQLException {
        String sql = "SELECT * FROM ligne";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs == null)
            throw new SQLException("Cannot get roles ResultSet");
        List<Ligne> lignes = new ArrayList<>();
        while (rs.next()) {
            Ligne ligne = parseLigne(rs);
            lignes.add(ligne);
        }
        return lignes;
    }


    public void delete(int ligneId) throws SQLException {
        String sql = "DELETE FROM ligne WHERE id = " + ligneId;

        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    public void update(int ligneId, Ligne ligne) throws SQLException {
        String sql = "UPDATE ligne SET airoport_aller = '" + ligne.getAiroportAller() + "'," +
                "airoport_arriver = '" + ligne.getAiroportArriver() + "'," +
                "prix_class = " + ligne.getPrixClass() + "," +
                "prix_normale = " + ligne.getPrixNormale() +
                " WHERE id = " + ligneId;
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

	public Ligne findLast() throws SQLException {
		String sql = "SELECT * FROM ligne ORDER BY id DESC LIMIT 1";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs == null)
            throw new SQLException("Cannot get role ResultSet");
        Ligne ligne = null;
        if (rs.next()) {
            ligne = parseLigne(rs);
        }
        return ligne;
	}
    

}
