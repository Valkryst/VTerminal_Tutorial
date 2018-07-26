package com.valkryst.VTerminal_Tutorial.gui.model;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal_Tutorial.Map;
import com.valkryst.VTerminal_Tutorial.Room;
import com.valkryst.VTerminal_Tutorial.entity.Player;
import lombok.Data;

import java.awt.*;

@Data
public class GameModel extends Model {
    /** The map. */
    private final Map map = new Map();

    /** The player. */
    private final Player player;

    /**
     * Constructs a new GameModel.
     *
     * @param screen
     *          The screen on which the view is displayed.
     */
    public GameModel(final Screen screen) {
        // Add Room to Map
        final Point position = new Point(10, 10);
        final Dimension dimensions = new Dimension(10, 5);
        final Room room = new Room(position, dimensions);
        room.carve(map);

        // Create Player
        player = new Player(new Point(12, 12), "Gygax");
        map.getEntities().add(player);

        // Add Map & Player to Screen
        screen.addComponent(map);
        screen.addComponent(player);
    }
}
