package com.valkryst.VTerminal_Tutorial.item;

import com.valkryst.VTerminal_Tutorial.Sprite;
import com.valkryst.VTerminal_Tutorial.statistic.Stat;
import lombok.Getter;

import java.util.HashMap;

public class Item {
    /** The sprite. */
    @Getter private final Sprite sprite;

    /** The name. */
    @Getter private final String name;

    /** The description. */
    @Getter private final String description;

    /** The stats. */
    private final HashMap<String, Stat> stats;

    /**
     * Constructs a new Item.
     *
     * @param sprite
     *          The sprite.
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
    public Item(final Sprite sprite, final String name, final String description, final HashMap<String, Stat> stats) {
        this.sprite = (sprite == null ? Sprite.UNKNOWN : sprite);

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
    public void addStat(final Stat stat) {
        if (stat != null) {
            stats.putIfAbsent(stat.getName().toLowerCase(), stat);
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
     * Retrieves a stat, by name, from the item.
     *
     * @param name
     *          The name of the stat.
     *
     * @return
     *          The stat.
     *          If the name is null, then null is returned.
     *          If the item has no stat that uses the specified name, then null is returned.
     */
    public Stat getStat(final String name) {
        if (name == null) {
            return null;
        }

        return stats.get(name.toLowerCase());
    }
}
