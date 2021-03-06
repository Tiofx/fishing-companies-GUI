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

    public static FormFactory<Class> formFactory = new FormFactory<>();
    public static ControllerFactory<String> controllerFactory;

    protected Statement stmt;

    protected final String[] tablesName = {
            "voyage",
            "fish",
            "ship",
            "captain",
            "inventory",
            "fishRegion",
            "quota",
            "fishSeason"
    };
    protected final int tablesNumber = tablesName.length;

    public Connection() {
        this("root", "root");
    }

    public Connection(String userName, String userPassword) {
        this("com.mysql.jdbc.Driver",
                "localhost",
                "3306",
                "fishingCompanies",
                userName,
                userPassword);
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
        formFactory = new FormFactory<>();
        fillFactory();
    }

    private void fillFactory() {
        formFactory.add(CaptainController.class, CaptainForm::new);
        formFactory.add(FishController.class, FishForm::new);
        formFactory.add(FishRegionController.class, FishRegionForm::new);
        formFactory.add(InventoryController.class, InventoryForm::new);
        formFactory.add(ShipController.class, ShipForm::new);
        formFactory.add(QuotaController.class, () -> new QuotaForm(getJRS("fishRegion")));
        formFactory.add(FishSeasonController.class, () -> new FishSeasonForm(getJRS("fishRegion")));
        formFactory.add(FishCatchController.class, () ->
                new FishCatchForm((FishController) controllerFactory.getInstance("fish")));
        formFactory.add(VoyageController.class, () -> new FullVoyageForm(
                (CaptainController) controllerFactory.getInstance("captain"),
                (ShipController) controllerFactory.getInstance("ship"),
                (FishSeasonController) controllerFactory.getInstance("fishSeason"),
                (QuotaController) controllerFactory.getInstance("quota"),
                (FishCatchController) controllerFactory.getInstance("fishCatch"),
                (InventoryController) controllerFactory.getInstance("inventory"),
                (VoyageInventoryController) controllerFactory.getInstance("voyage_inventory")
        ));
        formFactory.add(FishCatchControllerForVoyageForm.class, () ->
                new FishCatchForm((FishController) controllerFactory.getInstance("fish")));
        formFactory.add(InventoryControllerForVoyageForm.class, InventoryForm::new);
        formFactory.add(VoyageInventoryController.class, () ->
                new VoyageInventoryForm((InventoryController) controllerFactory.getInstance("inventory")));
    }

    public void startConnection() {
        connection = null;
        try {
            Class.forName(driverName);
            url = "jdbc:mysql://" + ip + ":" + port + "/" + dataBaseName
                    + "?user=" + userName + "&password=" + userPassword;
            connection = DriverManager.getConnection(url);

            stmt = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

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
