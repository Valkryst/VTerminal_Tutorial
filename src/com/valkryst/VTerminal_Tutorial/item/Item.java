package com.valkryst.VTerminal_Tutorial.item;

import com.valkryst.VTerminal.component.Layer;
import com.valkryst.VTerminal_Tutorial.enums.Stat;
import com.valkryst.VTerminal_Tutorial.statistic.Statistic;
import lombok.Getter;

import java.awt.*;
import java.util.HashMap;

public class Item {
    /** The name. */
    @Getter private final String name;

    /** The description. */
    @Getter private final String description;

    /** The stats. */
    private final HashMap<Stat, Statistic> stats;

    /**
     * Constructs a new Item.
     *
     * @param name
     *          The name.
     *
     * @param description
     *          The description.
     *
     * @param stats
     *          The stats.
     */
    public Item(final String name, final String description, final HashMap<Stat, Statistic> stats) {
        this.name = (name == null || name.isEmpty() ? "Unknown" : name);
        this.description = (description == null || description.isEmpty() ? "" : description);
        this.stats = (stats == null ? new HashMap<>() : stats);
    }

    /**
     * Adds a stat to the item.
     *
     * @param stat
     *          The stat.
     */
    public void addStat(final Statistic stat) {
        if (stat != null) {
            stats.putIfAbsent(stat.getType(), stat);
        }
    }

    /**
     * Removes a stat, by name, from the item.
     *
     * @param name
     *          The name of the stat.
     */
    public void removeStat(final String name) {
        if (name != null) {
            stats.remove(name.toLowerCase());
        }
    }

    /**
     * Retrieves a stat, by type, from the item.
     *
     * @param type
     *          The type of the stat.
     *
     * @return
     *          The stat.
     *          If the type is null, then null is returned.
     *          If the item has no stat that uses the specified type, then null is returned.
     */
    public Statistic getStat(final Stat type) {
        if (type == null) {
            return null;
        }

        return stats.get(type);
    }

    /**
     * Constructs an information panel, containing a number of important statistics, for the item.
     *
     * @return
     *          The layer containing all of the information.
     */
    public Layer getInformationPanel() {
        return new Layer(new Dimension(39, 38));
    }
}
