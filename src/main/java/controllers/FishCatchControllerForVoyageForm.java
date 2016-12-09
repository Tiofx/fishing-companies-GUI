package controllers;

import models.sql.FishCatch;
import unit.Connection;

import javax.sql.rowset.JdbcRowSet;

public class FishCatchControllerForVoyageForm extends FishCatchController {
    protected final int voyageId;

    public FishCatchControllerForVoyageForm() {
        voyageId = -1;
    }

    public FishCatchControllerForVoyageForm(Connection connection, String tableName, int voyageId) {
        super(connection, tableName);
        this.voyageId = voyageId;
    }

    public FishCatchControllerForVoyageForm(FishCatchController controller, int voyageId) {
        this(controller.getJrs(), voyageId);
    }

    public FishCatchControllerForVoyageForm(JdbcRowSet jrs, int voyageId) {
        super(jrs);
        this.voyageId = voyageId;
    }

    @Override
    public boolean reset() {
        return find(new Boolean[]{true, false, false}, new FishCatch(voyageId, -1, -1));
    }
}
