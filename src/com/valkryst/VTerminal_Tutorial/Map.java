package com.valkryst.VTerminal_Tutorial;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.component.Layer;
import lombok.Getter;

import java.awt.*;

public class Map extends Layer {
    /** The mapTiles. */
    @Getter private MapTile[][] mapTiles;

    /** Constructs a new Map. */
    public Map() {
        super(new Dimension(80, 40));

        final int viewWidth = getViewWidth();
        final int viewHeight = getViewHeight();

        // Set the Layer to display all tiles as empty and black.
        for (int y = 0 ; y < viewHeight ; y++) {
            for (int x = 0 ; x < viewWidth ; x++) {
                final Tile tile = super.tiles.getTileAt(x, y);
                tile.setCharacter(' ');
                tile.setBackgroundColor(Color.BLACK);
            }
        }

        // Initialize the MapTiles array.
        mapTiles = new MapTile[viewHeight][viewWidth];

        for (int y = 0 ; y < viewHeight ; y++) {
            for (int x = 0 ; x < viewWidth ; x++) {
                mapTiles[y][x] = new MapTile();
            }
        }
    }

    /**
     * Retrieves the width of the map.
     *
     * @return
     *          The width of the map.
     */
    public int getMapWidth() {
        return mapTiles.length;
    }

    /**
     * Retrieves the height of the map.
     *
     * @return
     *          The height of the map.
     */
    public int getMapHeight() {
        return mapTiles[0].length;
    }

    /**
     * Retrieves the width of the view.
     *
     * @return
     *          The width of the view.
     */
    public int getViewWidth() {
        return super.tiles.getWidth();
    }

    /**
     * Retrieves the height of the view.
     *
     * @return
     *          The height of the view.
     */
    public int getViewHeight() {
        return super.tiles.getHeight();
    }
}

