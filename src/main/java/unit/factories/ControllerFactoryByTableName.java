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
        add("fishSeason", () -> new FishSeasonController(connection, "fishSeason"));
        add("fishCatch", () -> new FishCatchController(connection, "fishCatch"));
        add("voyage", () -> new VoyageController(connection, "voyage"));
        add("voyage_inventory", () -> new VoyageInventoryController(connection, "voyage_inventory"));
    }

    public AbstractController getInstance(JdbcRowSet id) {
        try {
            return super.getInstance(id.getMetaData().getTableName(1));
        } catch (SQLException e) {
            return null;
        }
    }
}
