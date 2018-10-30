package com.valkryst.VTerminal_Tutorial.gui.controller;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal_Tutorial.Map;
import com.valkryst.VTerminal_Tutorial.Message;
import com.valkryst.VTerminal_Tutorial.entity.Container;
import com.valkryst.VTerminal_Tutorial.entity.Entity;
import com.valkryst.VTerminal_Tutorial.entity.Player;
import com.valkryst.VTerminal_Tutorial.gui.model.GameModel;
import com.valkryst.VTerminal_Tutorial.gui.view.GameView;
import com.valkryst.VTerminal_Tutorial.item.Inventory;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameController extends Controller<GameView, GameModel> {
    /** The timer which runs the game loop. */
    @Getter private final Timer timer;

    /** The controller for the inventory view. */
    private final InventoryController inventoryController;

    /**
     * Constructs a new GameController.
     *
     * @param screen
     *          The screen on which the view is displayed.
     */
    public GameController(final Screen screen) {
        super(new GameView(screen), new GameModel(screen));
        initializeEventHandlers(screen);

        super.view.addModelComponents(model);

        // Create inventory controller
        inventoryController = new InventoryController(screen, this);

        // Create and start the game-loop timer.
        final Map map = model.getMap();

        timer = new Timer(16, e -> {
            for (final Entity entity : map.getEntities()) {
                entity.performActions(this);
            }

            map.updateLayerTiles();

            screen.draw();
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    /**
     * Creates any event handlers required by the view.
     *
     * @param screen
     *          The screen on which the view is displayed.
     */
    private void initializeEventHandlers(final Screen screen) {
        final Player player = super.model.getPlayer();

        final MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {}

            @Override
            public void mousePressed(final MouseEvent e) {}

            @Override
            public void mouseReleased(final MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    final Point mousePos = screen.getMousePosition();
                    final Map map = model.getMap();

                    // Check if the click was outside the map area.
                    if (mousePos.x < 0 || mousePos.x >= map.getViewWidth()) {
                        return;
                    }

                    if (mousePos.y < 0 || mousePos.y >= map.getViewHeight()) {
                        return;
                    }

                    // Check if an entity was clicked.
                    for (final Entity entity : map.getEntities()) {
                        if (entity.intersects(mousePos)) {
                            view.displayTargetInformation(entity);
                            return;
                        }
                    }

                    view.displayTargetInformation(null);
                }
            }

            @Override
            public void mouseEntered(final MouseEvent e) {}

            @Override
            public void mouseExited(final MouseEvent e) {}
        };

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
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP: {
                        player.move(0, -1);
                        break;
                    }

                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN: {
                        player.move(0, 1);
                        break;
                    }

                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT: {
                        player.move(-1, 0);
                        break;
                    }

                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT: {
                        player.move(1, 0);
                        break;
                    }

                    case KeyEvent.VK_I: {
                        // Remove this view from the screen.
                        GameController.super.removeFromScreen(screen);

                        // If there's a container below the player, open it for looting.
                        Inventory lootInventory = null;

                        for (final Entity entity : model.getMap().getEntities()) {
                            if (entity instanceof Container == false) {
                                continue;
                            }

                            final Point containerPosition = entity.getPosition();
                            final Point playerPosition = model.getPlayer().getPosition();

                            if (containerPosition.equals(playerPosition)) {
                                lootInventory = entity.getInventory();
                                removeEntityFromMap(entity);
                                break;
                            }
                        }

                        // Add the new view to the screen.
                        inventoryController.getModel().setPlayerInventory(model.getPlayer().getInventory());
                        inventoryController.getModel().setLootInventory(lootInventory);

                        inventoryController.addToScreen(screen);
                        break;
                    }
                }
            }
        };

        super.getModel().getEventListeners().add(mouseListener);
        super.getModel().getEventListeners().add(keyListener);
    }

    /**
     * Adds an entity to the map.
     *
     * @param entity
     *          The entity to add.
     */
    public void addEntityToMap(final Entity entity) {
        if (entity == null) {
            return;
        }

        final Map map = model.getMap();

        if (map.getEntities().contains(entity) == false) {
            map.getEntities().add(entity);
            view.addComponent(entity);
        }
    }

    /**
     * Removes an entity from the map.
     *
     * @param entity
     *          The entity to remove.
     */
    public void removeEntityFromMap(final Entity entity) {
        if (entity == null) {
            return;
        }

        final Map map = model.getMap();
        map.getEntities().remove(entity);
        view.removeComponent(entity);
    }

    /**
     * Adds a message to the message box.
     *
     * @param message
     *          The message.
     */
    public void displayMessage(final Message message) {
        if (message != null) {
            view.getMessageBox().appendText(message.getMessage());
        }
    }
}
