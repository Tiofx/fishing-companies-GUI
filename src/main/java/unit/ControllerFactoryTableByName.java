package unit;

import controllers.*;

import javax.sql.rowset.JdbcRowSet;
import java.sql.SQLException;

public class ControllerFactoryTableByName extends ControllerFactory<String> {

    public ControllerFactoryTableByName() {
        add("captain", CaptainController::new);
        add("fishCatch", FishCatchController::new);
        add("fish", FishController::new);
        add("fishRegion", FishRegionController::new);
        add("inventory", InventoryController::new);
        add("quota", QuotaController::new);
        add("ship", ShipController::new);
    }

    public AbstractController getInstance(JdbcRowSet id) {
        try {
            return super.getInstance(id.getMetaData().getTableName(1));
        } catch (SQLException e) {
            return null;
        }
    }
}
