package com.valkryst.VTerminal_Tutorial.entity;

import com.valkryst.VTerminal_Tutorial.Sprite;

import java.awt.*;

public class Player extends Entity {
    /**
     * Constructs a new Player.
     *
     * @param position
     *          The position of the player within a map.
     *
     *          Defaults to (0, 0) if the position is null or if either part of the coordinate is negative.
     *
     * @param name
     *          The name.
     *
     *          Defaults to NoNameSet if the name is null or empty.
     */
    public Player(final Point position, final String name) {
        super(Sprite.PLAYER, position, name);
    }
}
