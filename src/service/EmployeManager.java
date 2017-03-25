package service;

import models.Employe;
import models.Role;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeManager {

    private Connection connection;

    public EmployeManager(Connection connection) {
        this.connection = connection;
    }

    public void add(Employe employe) throws SQLException {
        String sql = "INSERT INTO employe (nom, prenom, email, login, password, active, role_id)" +
                " VALUES ('" + employe.getNom() +
                "','" + employe.getPrenom() +
                "','" + employe.getEmail() +
                "','" + employe.getLogin() +
                "','" + employe.getPassword() +
                "'," + employe.getActive() +
                ", " + employe.getRole().getId() + " )";

        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    public Employe find(int employeId) throws SQLException {
        String sql = "SELECT * FROM employe WHERE id = " + employeId;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs == null)
            throw new SQLException("Cannot get employe ResultSet");
        Employe employe = null;
        if (rs.next()) {
            employe = parseEmploye(rs);
        }
        return employe;
    }

    public Employe findByLogin(String login) throws SQLException {
        String sql = "SELECT * FROM employe WHERE login = '" + login + "'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs == null)
            throw new SQLException("Cannot get employe ResultSet");
        Employe employe = null;
        if (rs.next()) {
            employe = parseEmploye(rs);
        }
        return employe;
    }

    private Employe parseEmploye(ResultSet rs) throws SQLException {
        Employe employe;
        int id = rs.getInt("id");
        String nom = rs.getString("nom");
        String prenom = rs.getString("prenom");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String password = rs.getString("password");
        int active = rs.getInt("active");
        int role_id = rs.getInt("role_id");
        Role role = new RoleManager(connection).find(role_id);
        employe = new Employe(role);
        employe.setId(id);
        employe.setNom(nom);
        employe.setPrenom(prenom);
        employe.setEmail(email);
        employe.setLogin(login);
        employe.setPassword(password);
        employe.setActive(active);
        return employe;
    }

    public List<Employe> findAll() throws SQLException {
        String sql = "SELECT * FROM employe";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs == null)
            throw new SQLException("Cannot get employees ResultSet");
        List<Employe> employes = new ArrayList<>();
        while (rs.next()) {
            Employe employe = parseEmploye(rs);
            employes.add(employe);
        }
        return employes;
    }

    public void disable(int employeId) throws SQLException {
        String sql = "UPDATE employe SET active = 0 WHERE id = " + employeId;
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }
    public void update(int employeId, Employe employe) throws SQLException {
        String sql = "UPDATE employe SET nom = '" + employe.getNom() + "'," +
                "prenom = '" + employe.getPrenom() + "'," +
                "email = '" + employe.getEmail() + "'," +
                "login = '" + employe.getLogin() + "'," +
                "password = '" + employe.getPassword()+ "'," +
                "role_id = " + employe.getRole().getId() +
                " WHERE id = " + employeId;
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

}
