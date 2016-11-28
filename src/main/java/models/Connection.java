package models;

import com.sun.rowset.JdbcRowSetImpl;

import javax.sql.rowset.JdbcRowSet;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection {
    protected final String driverName;

    protected final String ip;
    protected final String port;
    protected final String dataBaseName;
    protected final String userName;
    protected final String userPassword;
    protected String url;


    protected java.sql.Connection connection;
    protected Statement stmt;
    protected JdbcRowSet fishJRS;

    public Connection() {
        this("com.mysql.jdbc.Driver",
                "localhost",
                "3306",
                "fishingCompanies",
                "root",
                "root");
    }

    public Connection(String driverName, String ip, String port,
                      String dataBaseName, String userName, String userPassword) {
        this.driverName = driverName;
        this.ip = ip;
        this.port = port;
        this.dataBaseName = dataBaseName;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public void startConnection() {
        connection = null;
        try {
            Class.forName(driverName);
            url = "jdbc:mysql://" + ip + ":" + port + "/" + dataBaseName
                    + "?user=" + userName + "&password=" + userPassword;
//            connection = DriverManager.getConnection(url, userName, userPassword);
            connection = DriverManager.getConnection(url);

            stmt = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

//            ResultSet fishRS = stmt.executeQuery("SELECT * FROM fish");
            fishJRS = new JdbcRowSetImpl(connection);
            fishJRS.setCommand("SELECT * FROM fish");
            fishJRS.execute();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public java.sql.Connection getConnection() {
        return connection;
    }

    public Statement getStmt() {
        return stmt;
    }

    public JdbcRowSet getFishJRS() {
        return fishJRS;
    }

    public void close() {
        try {
            fishJRS.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
