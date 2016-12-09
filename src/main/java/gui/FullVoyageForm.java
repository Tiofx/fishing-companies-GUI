package gui;

import controllers.*;
import models.sql.Voyage;

import javax.swing.*;

public class FullVoyageForm extends VoyageForm {
    protected int voyageId;

    protected FishCatchController controller;
    protected FishCatchController fishCatchController;

    public FullVoyageForm() {
    }

    public FullVoyageForm(CaptainController captainController, ShipController shipController,
                          FishSeasonController fishSeasonController, QuotaController quotaController,
                          FishCatchController controller) {
        super(captainController, shipController, fishSeasonController, quotaController);
        this.controller = controller;
        this.fishCatchController = new FishCatchControllerForVoyageForm(controller, voyageId);

        fishCatchSP.setViewportView(fishCatchController.getView());
        fishCatchSP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        fishCatchSP.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    public void setVoyageId(int voyageId) {
        this.voyageId = voyageId;
    }

    public void setController(FishCatchController controller) {
        this.controller = controller;
        fishCatchController = new FishCatchControllerForVoyageForm(controller, voyageId);
    }

    @Override
    public void setRecord(Voyage record) {
        super.setRecord(record);
        voyageId = record.getId();
        fishCatchController = new FishCatchControllerForVoyageForm(controller, voyageId);

        fishCatchSP.setViewportView(fishCatchController.getView());
        fishCatchSP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        fishCatchSP.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }
}
