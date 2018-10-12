package com.valkryst.VTerminal_Tutorial;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.component.Layer;
import com.valkryst.VTerminal_Tutorial.entity.Container;
import com.valkryst.VTerminal_Tutorial.entity.Entity;
import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Map extends Layer {
    /** The map tiles. */
    @Getter private MapTile[][] mapTiles;

    /** The entities. */
    @Getter private List<Entity> entities = new ArrayList<>();

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

        this.updateLayerTiles();
    }

    /** Updates the Map's Layer, so that any changes made to the Map's tiles are displayed on the Layer. */
    public void updateLayerTiles() {
        for (int y = 0 ; y < getViewHeight() ; y++) {
            for (int x = 0 ; x < getViewWidth() ; x++) {
                final MapTile mapTile = mapTiles[y][x];
                final Sprite mapTileSprite = mapTile.getSprite();

                final Tile layerTile = super.tiles.getTileAt(x, y);
                layerTile.setCharacter(mapTileSprite.getCharacter());

                if (mapTile.isVisible()) {
                    layerTile.setBackgroundColor(mapTileSprite.getBackgroundColor());
                    layerTile.setForegroundColor(mapTileSprite.getForegroundColor());
                } else if (mapTile.isVisited()) {
                    layerTile.setBackgroundColor(mapTileSprite.getDarkBackgroundColor());
                    layerTile.setForegroundColor(mapTileSprite.getDarkForegroundColor());
                } else {
                    layerTile.setBackgroundColor(Sprite.DARKNESS.getBackgroundColor());
                    layerTile.setForegroundColor(Sprite.DARKNESS.getForegroundColor());
                }
            }
        }
    }

    /**
     * Determines if a position on the map is free.
     *
     * A position is free if there is no entity at the position and if the tile at the position is not solid.
     *
     * @param position
     *        The position.
     *
     * @return
     *        Whether the position is free.
     */
    public boolean isPositionFree(final Point position) {
        if (position == null) {
            return false;
        }

        // Ensure position isn't outside the bounds of the map.
        if (position.x < 0 || position.y < 0) {
            return false;
        }

        if (position.x >= getMapWidth() || position.y >= getMapHeight()) {
            return false;
        }

        // Check for entities at the position.
        for (final Entity entity : entities) {
            if (entity instanceof Container) {
                continue;
            }

            if (entity.getPosition().equals(position)) {
                return false;
            }
        }

        // Check if the tile at the position is solid.
        if (mapTiles[position.y][position.x].isSolid()) {
            return false;
        }

        return true;
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

