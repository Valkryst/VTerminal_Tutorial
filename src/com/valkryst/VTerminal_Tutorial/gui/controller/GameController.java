package com.valkryst.VTerminal_Tutorial.gui.controller;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal_Tutorial.Map;
import com.valkryst.VTerminal_Tutorial.entity.Entity;
import com.valkryst.VTerminal_Tutorial.entity.Player;
import com.valkryst.VTerminal_Tutorial.gui.model.GameModel;
import com.valkryst.VTerminal_Tutorial.gui.view.GameView;
import lombok.Getter;
import lombok.NonNull;

import javax.swing.*;
import java.awt.*;
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
     *
     * @throws NullPointerException
     *          If the screen is null.
     */
    public GameController(final @NonNull Screen screen) {
        super(new GameView(screen), new GameModel(screen));
        initializeEventHandlers();

        /*
         * Add the Map & Player to the View.
         *
         * Because of a quirk in VTerminal's rendering, we need to add the player after the map is first
         * rendered, so that the map appears below the player. We use 1ms as the delay, because the first
         * render (see the game-loop timer below) occurs right away.
         */
        view.addComponent(super.model.getMap());

        final Timer tempTimer = new Timer(1, e-> {
            final Player player = super.model.getPlayer();
            final Map map = super.model.getMap();

            /*
             * Again, due to the initial rendering quirk, we need to perform the following steps to correctly
             * render the player with it's LOS on the map when the game first starts.
             */
            player.getLineOfSight().showLOSOnMap(map);
            map.updateLayerTiles();
            screen.draw();

            view.addComponent(player);
        });
        tempTimer.setRepeats(false);
        tempTimer.start();

        // Create and start the game-loop timer.
        timer = new Timer(16, e -> {
            final Map map = super.model.getMap();

            for (final Entity entity : map.getEntities()) {
                entity.performActions(map);
            }

            map.updateLayerTiles();

            screen.draw();
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    /** Creates any event handlers required by the view. */
    private void initializeEventHandlers() {
        final Player player = super.model.getPlayer();

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
                }
            }
        };

        super.getModel().getEventListeners().add(keyListener);
    }
}
