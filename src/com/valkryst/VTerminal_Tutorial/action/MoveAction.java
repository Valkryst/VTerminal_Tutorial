package com.valkryst.VTerminal_Tutorial.action;

import com.valkryst.VTerminal_Tutorial.Map;
import com.valkryst.VTerminal_Tutorial.entity.Entity;

import java.awt.*;

public class MoveAction extends Action {
    /** The original position of the entity being moved. */
    private final Point originalPosition;

    /** The position being moved to. */
    private final Point newPosition;

    /**
     * Constructs a new MoveAction.
     *
     * @param position
     *        The current position of the entity to move.
     *
     * @param dx
     *        The change to apply to the x-axis position.
     *
     * @param dy
     *        The change to apply to the y-axis position.
     */
    public MoveAction(final Point position, final int dx, final int dy) {
        originalPosition = position;
        newPosition = new Point(dx + position.x, dy + position.y);
    }

    @Override
    public void perform(final Map map, final Entity entity) {
        if (map == null || entity == null) {
            return;
        }

        if (map.isPositionFree(newPosition)) {
            super.perform(map, entity);
            entity.setPosition(newPosition);
        }
    }
}
