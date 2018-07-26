package com.valkryst.VTerminal_Tutorial.entity;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.component.Layer;
import com.valkryst.VTerminal_Tutorial.Map;
import com.valkryst.VTerminal_Tutorial.Sprite;
import com.valkryst.VTerminal_Tutorial.action.Action;
import com.valkryst.VTerminal_Tutorial.action.MoveAction;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Entity extends Layer {
    /** The sprite. */
    @Getter private Sprite sprite;

    /** The name of the entity. */
    @Getter @Setter private String name;

    /** The actions to perform. */
    private final Queue<Action> actions = new ConcurrentLinkedQueue<>();

    /**
     * Constructs a new Entity.
     *
     * @param sprite
     *          The sprite.
     *
     *          Defaults to UNKNOWN if the sprite is null.
     *
     * @param position
     *          The position of the entity within a map.
     *
     *          Defaults to (0, 0) if the position is null or if either part of the coordinate is negative.
     *
     * @param name
     *          The name.
     *
     *          Defaults to NoNameSet if the name is null or empty.
     */
    public Entity(final Sprite sprite, final Point position, final String name) {
        super(new Dimension(1, 1));

        if (sprite == null) {
            setSprite(Sprite.UNKNOWN);
        } else {
            setSprite(sprite);
        }

        if (position == null || position.x < 0 || position.y < 0) {
            super.getTiles().setPosition(new Point(0, 0));
        } else {
            super.getTiles().setPosition(position);
        }

        if (name == null || name.isEmpty()) {
            this.name = "NoNameSet";
        } else {
            this.name = name;
        }
    }

    /**
     * Performs all of the entity's actions.
     *
     * @param map
     *          The map on which the entity exists.
     */
    public void performActions(final Map map) {
        for (final Action action : actions) {
            action.perform(map, this);
        }

        actions.clear();
    }

    /**
     * Adds an action to the entity.
     *
     * @param action
     *        The action.
     */
    public void addAction(final Action action) {
        if (action == null) {
            return;
        }

        actions.add(action);
    }

    /**
     * Adds a move action to the entity, to move it to a new position relative to it's current position.
     *
     * @param dx
     *          The change in x-axis position.
     *
     * @param dy
     *          The change in y-axis position.
     */
    public void move(final int dx, final int dy) {
        actions.add(new MoveAction(this.getPosition(), dx, dy));
    }

    /**
     * Sets a new sprite for the entity.
     *
     * @param sprite
     *          The sprite.
     */
    public void setSprite(Sprite sprite) {
        if (sprite == null) {
            sprite = Sprite.UNKNOWN;
        }

        final Tile tile = super.getTileAt(new Point(0, 0));
        tile.setCharacter(sprite.getCharacter());
        tile.setForegroundColor(sprite.getForegroundColor());
        tile.setBackgroundColor(sprite.getBackgroundColor());

        this.sprite = sprite;
    }

    /**
     * Retrieves the entity's position.
     *
     * @return
     *          The entity's position.
     */
    public Point getPosition() {
        return new Point(super.getTiles().getXPosition(), super.getTiles().getYPosition());
    }

    /**
     * Adds a move action to the entity, to move it to a new position.
     *
     * Ignores null and negative positions.
     *
     * @param position
     *          The new position.
     */
    public void setPosition(final Point position) {
        if (position == null || position.x < 0 || position.y < 0) {
            return;
        }

        final Point currentPosition = this.getPosition();
        final int xDifference = position.x - currentPosition.x;
        final int yDifference = position.y - currentPosition.y;

        actions.add(new MoveAction(currentPosition, xDifference, yDifference));
    }
}