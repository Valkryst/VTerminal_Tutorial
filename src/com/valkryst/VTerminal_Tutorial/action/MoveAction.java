package com.valkryst.VTerminal_Tutorial.action;

import com.valkryst.VTerminal_Tutorial.LineOfSight;
import com.valkryst.VTerminal_Tutorial.Map;
import com.valkryst.VTerminal_Tutorial.entity.Container;
import com.valkryst.VTerminal_Tutorial.entity.Entity;
import com.valkryst.VTerminal_Tutorial.entity.Player;
import com.valkryst.VTerminal_Tutorial.gui.controller.GameController;

import java.awt.*;
import java.util.List;

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
    public void perform(final GameController controller, final Entity self) {
        final Map map = controller.getModel().getMap();

        if (map == null || self == null) {
            return;
        }

        // Attack any enemies at new location:
        for (final Entity target : map.getEntities()) {
            if (target.getPosition().equals(newPosition)) {
                if (target instanceof Container) {
                    continue;
                }

                // If the Entity being moved is the player, then we attack non-player entities.
                // Else if the Entity being moved isn't the player, then we attack player entities.
                if (self instanceof Player) {
                    if (target instanceof Player == false) {
                        new AttackAction(target).perform(controller, self);
                        return;
                    }
                } else {
                    if (target instanceof Player) {
                        new AttackAction(target).perform(controller, self);
                        return;
                    }
                }
            }
        }

        // Move to the new location:
        if (map.isPositionFree(newPosition)) {
            super.perform(controller, self);
            self.getTiles().setPosition(newPosition);
            self.setBoundingBoxOrigin(newPosition.x, newPosition.y);

            if (self instanceof Player) {
                final LineOfSight los = self.getLineOfSight();
                los.hideLOSOnMap(map);
                los.move(dx, dy);
                los.showLOSOnMap(map);
            } else {
                self.getLineOfSight().move(dx, dy);
            }
        }
    }
}
