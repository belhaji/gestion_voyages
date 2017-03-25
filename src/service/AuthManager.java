package service;

import models.Employe;

import java.sql.SQLException;

/**
 * Created by adil on 3/12/17.
 */
public class AuthManager {
    private EmployeManager em;

    public AuthManager(EmployeManager em) {
        this.em = em;
    }

    public boolean login(String login, String password) {
        try {
            Employe employe = em.findByLogin(login);
            if (employe == null)
                return false;
            if (employe.getPassword().equals(password))
                return true;
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
