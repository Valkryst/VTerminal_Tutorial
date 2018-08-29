package com.valkryst.VTerminal_Tutorial.gui.controller;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal_Tutorial.Map;
import com.valkryst.VTerminal_Tutorial.Message;
import com.valkryst.VTerminal_Tutorial.entity.Entity;
import com.valkryst.VTerminal_Tutorial.entity.Player;
import com.valkryst.VTerminal_Tutorial.gui.model.GameModel;
import com.valkryst.VTerminal_Tutorial.gui.model.InventoryModel;
import com.valkryst.VTerminal_Tutorial.gui.view.GameView;
import com.valkryst.VTerminal_Tutorial.item.Inventory;
import lombok.Getter;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController extends Controller<GameView, GameModel> {
    /** The timer which runs the game loop. */
    @Getter private final Timer timer;

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
        final GameController gameController = this;

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

                        // Add the new view to the screen.
                        final Inventory playerInventory = GameController.super.model.getPlayer().getInventory();
                        // todo Deal with loot inventory

                        final InventoryModel model = new InventoryModel(playerInventory);
                        final InventoryController controller = new InventoryController(screen, gameController, model);
                        controller.addToScreen(screen);
                        break;
                    }
                }
            }
        };

        super.getModel().getEventListeners().add(keyListener);
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
