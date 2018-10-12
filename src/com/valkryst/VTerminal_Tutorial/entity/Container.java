package com.valkryst.VTerminal_Tutorial.entity;

import com.valkryst.VTerminal_Tutorial.Sprite;
import com.valkryst.VTerminal_Tutorial.item.Inventory;

import java.awt.*;

public class Container extends Entity {
    /**
     * Constructs a new Container.
     *
     * @param position
     *          The position of the container within a map.
     *
     *          Defaults to (0, 0) if the position is null or if either part of the coordinate is negative.
     *
     * @param inventory
     *          The inventory.
     *
     *          Defaults to an empty inventory if null.
     */
    public Container(final Point position, final Inventory inventory) {
        super(Sprite.CONTAINER, position, ((inventory == null || inventory.getSize() == 0) ? "Empty Container" : "Container"), inventory);
    }
}
