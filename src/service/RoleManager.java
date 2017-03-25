package service;


import models.Role;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoleManager {
    private Connection connection;

    public RoleManager(Connection connection) {
        this.connection = connection;
    }

    public void add(Role role) throws SQLException {
        String sql = "INSERT INTO role (nom)" +
                " VALUES ('" + role.getNom() + "')";

        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    public Role find(int roleId) throws SQLException {
        String sql = "SELECT * FROM role WHERE id = " + roleId;

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs == null)
            throw new SQLException("Cannot get role ResultSet");
        Role role = null;
        if (rs.next()) {
            role = parseRole(rs);
        }
        return role;
    }

    private Role parseRole(ResultSet rs) throws SQLException {
        Role role = new Role();
        int id = rs.getInt("id");
        String nom = rs.getString("nom");
        role.setId(id);
        role.setNom(nom);
        return role;
    }

    public List<Role> findAll() throws SQLException {
        String sql = "SELECT * FROM role";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs == null)
            throw new SQLException("Cannot get roles ResultSet");
        List<Role> roles = new ArrayList<>();
        while (rs.next()) {
            Role role = parseRole(rs);
            roles.add(role);
        }
        return roles;
    }

    public void delete(int roleId) throws SQLException {
        String sql = "DELETE FROM role WHERE id = " + roleId;

        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    public void update(int roleId, Role role) throws SQLException {
        String sql = "UPDATE role SET nom = '" + role.getNom() + "' WHERE id = " + roleId;
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }
    
    public Role findByName(String roleName) throws SQLException {
        String sql = "SELECT * FROM role WHERE nom = '" + roleName + "'";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        if (rs == null)
            throw new SQLException("Cannot get role ResultSet");
        Role role = null;
        if (rs.next()) {
            role = parseRole(rs);
        }
        return role;
    }
    
}
