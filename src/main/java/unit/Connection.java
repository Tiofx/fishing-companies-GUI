package unit;

import com.sun.rowset.JdbcRowSetImpl;
import controllers.*;
import gui.*;
import unit.factories.ControllerFactory;
import unit.factories.ControllerFactoryByTableName;
import unit.factories.FormFactory;

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

    public java.sql.Connection connection;

    // TODO: 30/11/2016 change this
    public static final FormFactory<Class> formFactory = new FormFactory<>();
    public static ControllerFactory<String> controllerFactory;

    protected Statement stmt;

    protected final String[] tablesName = {"fish", "ship", "captain", "inventory", "fishRegion", "quota"};
    protected final int tablesNumber = tablesName.length;

    public Connection() {
        this("com.mysql.jdbc.Driver",
                "localhost",
                "3306",
                "fishingCompanies2",
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

        controllerFactory = new ControllerFactoryByTableName(this);
        fillFactory();
    }

    private void fillFactory() {
        formFactory.add(CaptainController.class, CaptainForm::new);
        formFactory.add(FishController.class, FishForm::new);
        formFactory.add(FishRegionController.class, FishRegionForm::new);
        formFactory.add(InventoryController.class, InventoryForm::new);
        formFactory.add(ShipController.class, ShipForm::new);
        formFactory.add(QuotaController.class, () -> new QuotaForm(getJRS("fishRegion")));
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

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String[] getTablesName() {
        return tablesName;
    }

    public int getTablesNumber() {
        return tablesNumber;
    }

    public java.sql.Connection getConnection() {
        return connection;
    }

    public JdbcRowSet getJRS(String tableName) {
        try {
            JdbcRowSet result = new JdbcRowSetImpl(connection);
            result.setCommand("SELECT * FROM " + tableName);
            result.execute();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
