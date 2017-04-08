package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// singleton
public class ConnectionManager {
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    private static final String DB_NAME = "gestion_voyages";
    private static Connection connection;


    private ConnectionManager() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost/" +
                        DB_NAME +
                        "?user=" + DB_USER + "&password=" + DB_PASS);
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur de connection");
            }
        }
        return connection;
    }

}
