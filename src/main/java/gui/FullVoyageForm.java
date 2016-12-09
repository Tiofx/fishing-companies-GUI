package gui;

import controllers.*;
import models.sql.Voyage;

import javax.swing.*;
import java.awt.*;

public class FullVoyageForm extends VoyageForm {
    protected int voyageId;

    protected FishCatchController controller;
    protected FishCatchController fishCatchController;

    protected InventoryController controller2;
    protected VoyageInventoryController controller3;
    protected InventoryController inventoryController;

    public FullVoyageForm() {
    }

    public FullVoyageForm(CaptainController captainController, ShipController shipController,
                          FishSeasonController fishSeasonController, QuotaController quotaController,
                          FishCatchController controller, InventoryController controller2,
                          VoyageInventoryController controller3) {

        super(captainController, shipController, fishSeasonController, quotaController);
        this.controller = controller;
        this.inventoryController = new InventoryControllerForVoyageForm(controller2, controller3, voyageId);

        this.controller2 = controller2;
        this.controller3 = controller3;

        inventorySP.setViewportView(inventoryController.getView());
        inventorySP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        inventorySP.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        l1.setVisible(false);
        l2.setVisible(false);
        inventorySP.setVisible(false);
        fishCatchSP.setVisible(false);
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
        inventoryController = new InventoryControllerForVoyageForm(controller2, controller3, voyageId);

        fishCatchController.getView().setMaximumSize(new Dimension(400, 200));
        fishCatchSP.setViewportView(fishCatchController.getView());
        fishCatchSP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        fishCatchSP.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        inventorySP.setViewportView(inventoryController.getView());
        inventorySP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        inventorySP.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        l1.setVisible(true);
        l2.setVisible(true);
        inventorySP.setVisible(true);
        fishCatchSP.setVisible(true);
    }
}
