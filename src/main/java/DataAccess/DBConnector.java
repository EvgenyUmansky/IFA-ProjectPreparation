package DataAccess;

import java.sql.Connection;

public class DBConnector {

    private static final DBConnector instance = new DBConnector();

    public static DBConnector getInstance(){
        return instance;
    }

    private DBConnector(){
    }

    public static Connection getConnection() {
        return null;
    }

}
