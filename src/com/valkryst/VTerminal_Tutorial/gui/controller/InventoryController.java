package com.valkryst.VTerminal_Tutorial.gui.controller;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal_Tutorial.entity.Container;
import com.valkryst.VTerminal_Tutorial.gui.model.GameModel;
import com.valkryst.VTerminal_Tutorial.gui.model.InventoryModel;
import com.valkryst.VTerminal_Tutorial.gui.view.InventoryView;
import com.valkryst.VTerminal_Tutorial.item.Inventory;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InventoryController extends Controller<InventoryView, InventoryModel> {
    /** The controller for the game. */
    private GameController gameController;

    /**
     * Constructs a new InventoryController.
     *
     * @param screen
     *          The screen on which the view is displayed.
     *
     * @param gameController
     *          The controller for the game.
     */
    public InventoryController(final Screen screen, final GameController gameController) {
        super(new InventoryView(screen), new InventoryModel());
        this.gameController = gameController;

        initializeEventHandlers(screen);

        super.view.addModelComponents(model);
    }

    @Override
    public void addToScreen(final Screen screen) {
        if (screen == null) {
            return;
        }

        super.addToScreen(screen);

        view.refreshEquipmentLayer(model);
        view.refreshInventoryLayer(model);
        view.refreshLootLayer(model);
    }

    /**
     * Creates any event handlers required by the view.
     *
     * @param screen
     *          The screen on which the view is displayed.
     */
    private void initializeEventHandlers(final Screen screen) {
        final KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(final KeyEvent e) {
            }

            @Override
            public void keyPressed(final KeyEvent e) {
            }

            @Override
            public void keyReleased(final KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE:
                    case KeyEvent.VK_I: {
                        // If there is anything in the loot inventory, then create a container with the loot and
                        // place it on the map.
                        final Inventory lootInventory = model.getLootInventory();

                        if (lootInventory.getTotalItems() > 0) {
                            final GameModel model = gameController.getModel();

                            final Container container = new Container(model.getPlayer().getPosition(), lootInventory);
                            gameController.addEntityToMap(container);
                        }

                        // Switch to game view.
                        InventoryController.super.removeFromScreen(screen);
                        gameController.addToScreen(screen);
                        break;
                    }
                }
            }
        };

        super.getModel().getEventListeners().add(keyListener);
    }
}
