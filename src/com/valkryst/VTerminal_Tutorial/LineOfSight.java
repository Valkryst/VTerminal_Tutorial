package com.valkryst.VTerminal_Tutorial;

import com.valkryst.VTerminal.misc.ShapeAlgorithms;
import lombok.NonNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LineOfSight {
    /** The visible points within the line of sight. */
    private final List<List<Point>> visibleLines = new ArrayList<>();

    /**
     * Constructs a new LineOfSight.
     *
     * @param radius
     *          The radius of the line of sight's ellipse.
     *
     * @param centerPoint
     *          The center point of the line of sight.
     */
    public LineOfSight(int radius, final @NonNull Point centerPoint) {
        if (radius < 1) {
            radius = 1;
        }

        /*
         * Retrieve the circumference points, then calculate the line of points that leads from the center
         * point to each edge point. These are the visible points of the LOS.
         */
        final List<Point> circumferencePoints = ShapeAlgorithms.getEllipse(centerPoint, new Dimension(radius, radius)) ;

        for (final Point point : circumferencePoints) {
            visibleLines.add(ShapeAlgorithms.getLine(centerPoint.x, centerPoint.y, point.x, point.y));
        }
    }

    /**
     * Moves the line of sight, relative to it's current position.
     *
     * @param dx
     *          The change in x-axis position.
     *
     * @param dy
     *          The change in y-axis position.
     */
    public void move(final int dx, final int dy) {
        visibleLines.forEach(list -> list.forEach(point -> {
            point.x += dx;
            point.y += dy;
        }));
    }

    /**
     * Hides the LOS on the map.
     *
     * @param map
     *          The map.
     */
    public void hideLOSOnMap(final Map map) {
        if (map == null) {
            return;
        }

        final MapTile[][] tiles = map.getMapTiles();

        for (final List<Point> line : visibleLines) {
            for (final Point point : line) {
                tiles[point.y][point.x].setVisible(false);
            }
        }
    }

    /**
     * Shows the LOS on the map.
     *
     * @param map
     *          The map.
     */
    public void showLOSOnMap(final Map map) {
        if (map == null) {
            return;
        }

        final MapTile[][] tiles = map.getMapTiles();

        for (final List<Point> line : visibleLines) {
            for (final Point point : line) {
                final MapTile tile = tiles[point.y][point.x];
                tile.setVisible(true);
                tile.setVisited(true);

                // If we hit a solid tile, then the rest of the line cannot be visible.
                if (tile.isSolid()) {
                    break;
                }
            }
        }
    }

    /**
     * Determines if a point is within the LOS.
     *
     * @param point
     *          The point.
     *
     * @return
     *          Whether the point is within the LOS.
     */
    public boolean isInLOS(final Point point) {
        for (final List<Point> line : visibleLines) {
            if (line.contains(point)) {
                return true;
            }
        }

        return false;
    }
}
