package unit.factories;

import controllers.*;
import unit.Connection;

import javax.sql.rowset.JdbcRowSet;
import java.sql.SQLException;

public class ControllerFactoryByTableName extends ControllerFactory<String> {
    protected final Connection connection;

    public ControllerFactoryByTableName(Connection connection) {
        this.connection = connection;
        add("captain", () -> new CaptainController(connection, "captain"));
        add("fishCatch", () -> new FishCatchController(connection, "fishCatch"));
        add("fish", () -> new FishController(connection, "fish"));
        add("fishRegion", () -> new FishRegionController(connection, "fishRegion"));
        add("inventory", () -> new InventoryController(connection, "inventory"));
        add("quota", () -> new QuotaController(connection, "quota", "fishRegion"));
        add("ship", () -> new ShipController(connection, "ship"));
    }
//
//    public ControllerFactoryByTableName() {
//        add("captain", CaptainController::new);
//        add("fishCatch", FishCatchController::new);
//        add("fish", FishController::new);
//        add("fishRegion", FishRegionController::new);
//        add("inventory", InventoryController::new);
//        add("quota", QuotaController::new);
//        add("ship", ShipController::new);
//    }

    public AbstractController getInstance(JdbcRowSet id) {
        try {
            return super.getInstance(id.getMetaData().getTableName(1));
        } catch (SQLException e) {
            return null;
        }
    }
}
