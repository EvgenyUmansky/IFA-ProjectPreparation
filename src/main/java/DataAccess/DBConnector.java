package DataAccess;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
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
            Connection conn = DriverManager.getConnection(URL, USER, PASS);

            return conn;
        } catch (SQLException ex) {
            throw new RuntimeException("Error connecting to the database FootBallDB", ex);
        }
        catch (ClassNotFoundException a){
            throw new RuntimeException("Error connecting to the database FootBallDB", a);
        }
    }
}
