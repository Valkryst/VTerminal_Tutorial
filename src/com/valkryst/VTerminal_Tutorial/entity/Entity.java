package com.valkryst.VTerminal_Tutorial.entity;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.builder.LabelBuilder;
import com.valkryst.VTerminal.component.Label;
import com.valkryst.VTerminal.component.Layer;
import com.valkryst.VTerminal.printer.RectanglePrinter;
import com.valkryst.VTerminal_Tutorial.LineOfSight;
import com.valkryst.VTerminal_Tutorial.Sprite;
import com.valkryst.VTerminal_Tutorial.action.Action;
import com.valkryst.VTerminal_Tutorial.action.MoveAction;
import com.valkryst.VTerminal_Tutorial.gui.controller.GameController;
import com.valkryst.VTerminal_Tutorial.item.Inventory;
import com.valkryst.VTerminal_Tutorial.statistic.BoundStat;
import com.valkryst.VTerminal_Tutorial.statistic.Stat;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Entity extends Layer {
    /** The sprite. */
    @Getter private Sprite sprite;

    /** The name. */
    @Getter @Setter private String name;

    /** The stats. */
    private final HashMap<String, Stat> stats = new HashMap<>();

    /** The inventory. */
    @Getter private final Inventory inventory = new Inventory(26);

    /** The actions to perform. */
    private final Queue<Action> actions = new ConcurrentLinkedQueue<>();

    /** The line of sight. */
    @Getter @Setter private LineOfSight lineOfSight;

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

        lineOfSight = new LineOfSight(4, position);

        // Set Core Stats
        final BoundStat health = new BoundStat("Health", 0, 100);
        final BoundStat level = new BoundStat("Level", 1, 1, 60);
        final BoundStat experience = new BoundStat("Experience", 0, 0, 100);

        addStat(health);
        addStat(level);
        addStat(experience);
    }

    /**
     * Performs all of the entity's actions.
     *
     * @param controller
     *          The game controller.
     */
    public void performActions(final GameController controller) {
        for (final Action action : actions) {
            action.perform(controller, this);
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
     * Adds a stat to the entity.
     *
     * @param stat
     *          The stat.
     */
    public void addStat(final Stat stat) {
        if (stat == null) {
            return;
        }

        stats.putIfAbsent(stat.getName().toLowerCase(), stat);
    }

    /**
     * Removes a stat, by name, from the entity.
     *
     * @param name
     *          The name of the stat.
     */
    public void removeStat(final String name) {
        if (name == null) {
            return;
        }

        stats.remove(name.toLowerCase());
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

        final Tile tile = super.getTileAt(0, 0);
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

    /**
     * Retrieves a stat, by name, from the entity.
     *
     * @param name
     *          The name of the stat.
     *
     * @return
     *          The stat.
     *          If the name is null, then null is returned.
     *          If the entity has no stat that uses the specified name, then null is returned.
     */
    public Stat getStat(final String name) {
        if (name == null) {
            return null;
        }

        return stats.get(name.toLowerCase());
    }

    /**
     * Constructs an information panel, containing a number of important statistics, for an entity.
     *
     * @param entity
     *          The entity.
     *
     * @return
     *          The layer containing all of the information.
     */
    public static Layer getInformationPanel(final Entity entity) {
        final Layer layer = new Layer(new Dimension(40, 8));

        // Print border
        final RectanglePrinter rectanglePrinter = new RectanglePrinter();
        rectanglePrinter.setWidth(40);
        rectanglePrinter.setHeight(8);
        rectanglePrinter.setTitle(entity.getName());
        rectanglePrinter.print(layer.getTiles(), new Point(0, 0));

        // Color name on the border
        final Color color = entity.getSprite().getForegroundColor();
        final Tile[] nameTiles = layer.getTiles().getRowSubset(0, 2, entity.getName().length());

        for (final Tile tile : nameTiles) {
            tile.setForegroundColor(color);
        }

        // Retrieve Stats
        final BoundStat health = (BoundStat) entity.getStat("Health");
        final BoundStat level = (BoundStat) entity.getStat("Level");
        final BoundStat experience = (BoundStat) entity.getStat("Experience");

        // Create runnable functions, used to add/update labels.
        final Runnable add_level = () -> {
            layer.getComponentsByID(entity.getName() + "-" + level.getName()).forEach(layer::removeComponent);

            final Label label = level.getLabel();
            label.setId(entity.getName() + "-" + label.getId());
            label.getTiles().setPosition(1, 1);

            layer.addComponent(label);
        };

        final Runnable add_xp = () -> {
            layer.getComponentsByID(entity.getName() + "-" + experience.getName()).forEach(layer::removeComponent);

            final Label label = experience.getBoundLabel();
            label.setId(entity.getName() + "-" + label.getId());
            label.getTiles().setPosition(1, 2);

            layer.addComponent(label);
        };

        final Runnable add_health = () -> {
            layer.getComponentsByID(entity.getName() + "-" + health.getName()).forEach(layer::removeComponent);

            final Label label;

            if (health.getValue() > 0) {
                label = health.getBoundLabel();
                label.getTiles().setPosition(1, 3);
            } else {
                final LabelBuilder builder = new LabelBuilder();
                builder.setText("Health: Deceased");
                builder.setPosition(1, 3);
                label = builder.build();
            }

            label.setId(entity.getName() + "-" + label.getId());

            layer.addComponent(label);
        };

        // Add runnable functions to their associated stat.
        level.getRunnables().add(add_level);
        experience.getRunnables().add(add_xp);
        health.getRunnables().add(add_health);

        // Run the runnable functions in order to add the labels to the layer.
        add_level.run();
        add_xp.run();
        add_health.run();

        return layer;
    }
}