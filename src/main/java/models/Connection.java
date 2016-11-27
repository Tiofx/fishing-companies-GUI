package models;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection {
    protected Statement stmt;
    protected ResultSet fishRS;

    public Connection() {
        try {
            java.sql.Connection connection = null;
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/fishingCompanies";
            String user = "root";
            String password = "root";
            connection = DriverManager.getConnection(url, user, password);

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
}
