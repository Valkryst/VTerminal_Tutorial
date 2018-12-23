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
import com.valkryst.VTerminal_Tutorial.enums.Stat;
import com.valkryst.VTerminal_Tutorial.gui.controller.GameController;
import com.valkryst.VTerminal_Tutorial.item.Inventory;
import com.valkryst.VTerminal_Tutorial.statistic.BoundStatistic;
import com.valkryst.VTerminal_Tutorial.statistic.Statistic;
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
    private final HashMap<Stat, Statistic> stats = new HashMap<>();

    /** The inventory. */
    @Getter private Inventory inventory = new Inventory(26);

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
        setSprite((sprite == null ? Sprite.UNKNOWN : sprite));

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

        final BoundStatistic health = new BoundStatistic(Stat.HEALTH, 0, 100);
        final BoundStatistic level = new BoundStatistic(Stat.LEVEL, 1, 1, 60);
        final BoundStatistic experience = new BoundStatistic(Stat.EXPERIENCE, 0, 0, 100);

        addStat(health);
        addStat(level);
        addStat(experience);
    }

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
     *
     * @param inventory
     *          The inventory.
     *
     *          Defaults to an empty inventory if null.
     */
    public Entity(final Sprite sprite, final Point position, final String name, final Inventory inventory) {
        this(sprite, position, name);
        this.inventory = (inventory == null ? new Inventory(26) : inventory);
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
    public void addStat(final Statistic stat) {
        if (stat == null) {
            return;
        }

        stats.putIfAbsent(stat.getType(), stat);
    }

    /**
     * Removes a stat, by type, from the entity.
     *
     * @param type
     *          The type of the stat.
     */
    public void removeStat(final Stat type) {
        if (type == null) {
            return;
        }

        stats.remove(type);
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
     * Retrieves a stat, by type, from the entity.
     *
     * @param type
     *          The type of the stat.
     *
     * @return
     *          The stat.
     *          If the type is null, then null is returned.
     *          If the entity has no stat that uses the specified type, then null is returned.
     */
    public Statistic getStat(final Stat type) {
        if (type == null) {
            return null;
        }

        return stats.get(type);
    }

    /**
     * Constructs an information panel, containing a number of important statistics, for the entity.
     *
     * @return
     *          The layer containing all of the information.
     */
    public Layer getInformationPanel() {
        final Layer layer = new Layer(new Dimension(40, 8));

        // Print border
        final RectanglePrinter rectanglePrinter = new RectanglePrinter();
        rectanglePrinter.setWidth(40);
        rectanglePrinter.setHeight(8);
        rectanglePrinter.setTitle(this.getName());
        rectanglePrinter.print(layer.getTiles(), new Point(0, 0));

        // Color name on the border
        final Color color = sprite.getForegroundColor();
        final Tile[] nameTiles = layer.getTiles().getRowSubset(0, 2, name.length());

        for (final Tile tile : nameTiles) {
            tile.setForegroundColor(color);
        }

        // Retrieve Stat
        final BoundStatistic health = (BoundStatistic) this.getStat(Stat.HEALTH);
        final BoundStatistic level = (BoundStatistic) this.getStat(Stat.LEVEL);
        final BoundStatistic experience = (BoundStatistic) this.getStat(Stat.EXPERIENCE);

        // Create runnable functions, used to add/update labels.
        final Runnable add_level = () -> {
            layer.getComponentsByID(name + "-" + Stat.LEVEL.getName()).forEach(layer::removeComponent);

            final Label label = level.getLabel();
            label.setId(name + "-" + label.getId());
            label.getTiles().setPosition(1, 1);

            layer.addComponent(label);
        };

        final Runnable add_xp = () -> {
            layer.getComponentsByID(name + "-" + Stat.EXPERIENCE.getName()).forEach(layer::removeComponent);

            final Label label = experience.getBoundLabel();
            label.setId(name + "-" + label.getId());
            label.getTiles().setPosition(1, 2);

            layer.addComponent(label);
        };

        final Runnable add_health = () -> {
            layer.getComponentsByID(name + "-" + Stat.HEALTH.getName()).forEach(layer::removeComponent);

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

            label.setId(name + "-" + label.getId());

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