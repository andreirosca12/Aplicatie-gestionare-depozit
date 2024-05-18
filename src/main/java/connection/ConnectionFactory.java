package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 * @Source: http://theopentutorials.com/tutorials/java/jdbc/jdbc-mysql-create-database-example/
 */
public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/depozit";
    private static final String USER = "root";
    private static final String PASS = "andrei";

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * Constructor privat pentru a incarca driverul JDBC.
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creeaza o noua conexiune la baza de date.
     *
     * @return un obiect Connection sau null dacă nu se poate stabili o conexiune.
     */
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "A apărut o eroare în timp ce se încerca conectarea la baza de date", e);
        }
        return connection;
    }

    /**
     * Obtine o conexiune la baza de date.
     *
     * @return un obiect Connection
     */
    public static Connection getConnection() {
        return singleInstance.createConnection();
    }

    /**
     * Inchide conexiunea furnizata.
     *
     * @param connection conexiunea de inchis
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "A apărut o eroare în timp ce se încerca închiderea conexiunii", e);
            }
        }
    }

    /**
     * Inchide statement-ul furnizat.
     *
     * @param statement statement-ul de inchis
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "A apărut o eroare în timp ce se încerca închiderea statement-ului", e);
            }
        }
    }

    /**
     * Inchide setul de rezultate furnizat.
     *
     * @param resultSet setul de rezultate de inchis
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                LOGGER.log(Level.WARNING, "A apărut o eroare în timp ce se încerca închiderea setului de rezultate", e);
            }
        }
    }
}