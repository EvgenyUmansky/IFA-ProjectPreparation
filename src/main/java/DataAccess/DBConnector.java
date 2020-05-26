package DataAccess;

import org.apache.log4j.Logger;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    static Logger logger = Logger.getLogger(DBConnector.class.getName());

    public static final String URL = "jdbc:sqlserver://132.72.65.56\\ISE-STR08\\SQLEXPRESS:1433;databaseName=FootBallDB;integratedSecurity=false";
    private static final String className = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String USER = "root";
    private static final String PASS = "root";

    private static final DBConnector instance = new DBConnector();

    //private constructor to avoid client applications to use constructor
    public static DBConnector getInstance(){
        return instance;
    }

    DBConnector() {

    }
    /**
     * Get a connection to database
     *
     * @return Connection object
     */
    public static Connection getConnection() {
        try {
            Class.forName(className);
            return DriverManager.getConnection(URL, USER, PASS);
        }
        catch (SQLException | ClassNotFoundException ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException("Error connecting to the database FootBallDB", ex);
        }
    }
}
