package com.valkryst.VTerminal_Tutorial.entity;

import com.valkryst.VTerminal_Tutorial.Sprite;

import java.awt.*;

public class Player extends Entity {
    /**
     * Constructs a new Player.
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
     */
    public Player(final Sprite sprite, final Point position, final String name) {
        super(sprite, position, name);
    }
}
