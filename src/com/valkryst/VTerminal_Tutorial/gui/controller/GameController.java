package com.valkryst.VTerminal_Tutorial.gui.controller;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal_Tutorial.Room;
import com.valkryst.VTerminal_Tutorial.entity.Player;
import com.valkryst.VTerminal_Tutorial.gui.model.GameModel;
import com.valkryst.VTerminal_Tutorial.gui.view.GameView;
import lombok.NonNull;

import java.awt.*;

public class GameController extends Controller<GameView, GameModel> {
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
        super(new GameView(screen), new GameModel());
    }

    /**
     * Creates any event handlers required by the view.
     *
     * @param screen
     *          The screen on which the view is displayed.
     */
    public void initializeEventHandlers(final Screen screen) {
    }

    // Test Code
    public void test(final Screen screen) {
        screen.addComponent(super.model.getMap());

        final Point position = new Point(10, 10);
        final Dimension dimensions = new Dimension(10, 5);
        final Room room = new Room(position, dimensions);
        room.carve(super.model.getMap());

        screen.draw();

        final Player player = new Player(new Point(12, 12), "Gygax");
        super.model.getMap().addComponent(player);

        screen.draw();
    }
}
