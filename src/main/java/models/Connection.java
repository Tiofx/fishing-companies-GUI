package models;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection {
    protected final String driverName;

    protected final String id;
    protected final String port;
    protected final String dataBaseName;
    protected final String userName;
    protected final String userPassword;


    protected java.sql.Connection connection;
    protected Statement stmt;
    protected ResultSet fishRS;

    public Connection() {
        this("com.mysql.jdbc.Driver",
                "localhost",
                "3306",
                "fishingCompanies",
                "root",
                "root");
    }

    public Connection(String driverName, String id, String port,
                      String dataBaseName, String userName, String userPassword) {
        this.driverName = driverName;
        this.id = id;
        this.port = port;
        this.dataBaseName = dataBaseName;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public void startConnection() {
        try {
            connection = null;
            Class.forName(driverName);
            String url = "jdbc:mysql://localhost:3306/fishingCompanies";
            connection = DriverManager.getConnection(url, userName, userPassword);

            stmt = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            fishRS = stmt.executeQuery("SELECT * FROM fish");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Statement getStmt() {
        return stmt;
    }

    public ResultSet getFishRS() {
        return fishRS;
    }

    public void close() {
        try {
            fishRS.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
