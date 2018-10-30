package com.valkryst.VTerminal_Tutorial.entity;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.builder.LabelBuilder;
import com.valkryst.VTerminal.component.Layer;
import com.valkryst.VTerminal.printer.RectanglePrinter;
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

    @Override
    public Layer getInformationPanel() {
        final Layer layer = new Layer(new Dimension(40, 8));

        // Print border
        final RectanglePrinter rectanglePrinter = new RectanglePrinter();
        rectanglePrinter.setWidth(40);
        rectanglePrinter.setHeight(8);
        rectanglePrinter.setTitle(this.getName());
        rectanglePrinter.print(layer.getTiles(), new Point(0, 0));

        // Color name on the border
        final Color color = super.getSprite().getForegroundColor();
        final Tile[] nameTiles = layer.getTiles().getRowSubset(0, 2, super.getName().length());

        for (final Tile tile : nameTiles) {
            tile.setForegroundColor(color);
        }

        // Display Inventory Information
        final int totalItems = super.getInventory().getTotalItems();

        final LabelBuilder labelBuilder = new LabelBuilder();
        labelBuilder.setPosition(1, 1);

        if (totalItems == 1) {
            labelBuilder.setText("There is " + super.getInventory().getTotalItems() + " item here.");
        } else {
            labelBuilder.setText("There are " + super.getInventory().getTotalItems() + " items here.");
        }

        layer.addComponent(labelBuilder.build());

        return layer;
    }
}
