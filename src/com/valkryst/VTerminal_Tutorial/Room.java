package com.valkryst.VTerminal_Tutorial;

import java.awt.Dimension;
import java.awt.Point;

public class Room {
    /** The coordinates of the top-left tile. */
    private final Point position;
    /** The width & height. */
    private final Dimension dimensions;

    /** Constructs a new Room. */
    public Room() {
        this.position = new Point(0, 0);
        this.dimensions = new Dimension(0, 0);
    }

    /**
     * Constructs a new Room.
     *
     * @param position
     *          The coordinates of the top-left tile.
     *
     * @param dimensions
     *          The width & height.
     */
    public Room(final Point position, final Dimension dimensions) {
        this.position = position;
        this.dimensions = dimensions;
    }

    /**
     * Carves the room into a Map.
     *
     * @param map
     *          The map.
     */
    public void carve(final Map map) {
        final MapTile[][] tiles = map.getMapTiles();

        for (int y = position.y ; y < position.y + dimensions.height ; y++) {
            for (int x = position.x ; x < position.x + dimensions.width ; x++) {
                tiles[y][x].setSprite(Sprite.DIRT);
                tiles[y][x].setSolid(false);
            }
        }

        map.updateLayerTiles();
    }
}

