package com.valkryst.VTerminal_Tutorial.action;

import com.valkryst.VTerminal_Tutorial.LineOfSight;
import com.valkryst.VTerminal_Tutorial.Map;
import com.valkryst.VTerminal_Tutorial.entity.Entity;
import com.valkryst.VTerminal_Tutorial.entity.Player;
import com.valkryst.VTerminal_Tutorial.gui.controller.GameController;

import java.awt.*;

public class MoveAction extends Action {
    /** The original position of the entity being moved. */
    private final Point originalPosition;
    /** The position being moved to. */
    private final Point newPosition;

    /** The change applied to the original x-axis position. */
    private final int dx;
    /** The change applied to the original y-axis position. */
    private final int dy;

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
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void perform(final GameController controller, final Entity entity) {
        final Map map = controller.getModel().getMap();

        if (map == null || entity == null) {
            return;
        }

        if (map.isPositionFree(newPosition)) {
            super.perform(controller, entity);
            entity.getTiles().setPosition(newPosition);

            if (entity instanceof Player) {
                final LineOfSight los = entity.getLineOfSight();
                los.hideLOSOnMap(map);
                los.move(dx, dy);
                los.showLOSOnMap(map);
            } else {
                entity.getLineOfSight().move(dx, dy);
            }
        }
    }
}
