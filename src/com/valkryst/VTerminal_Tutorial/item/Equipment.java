package com.valkryst.VTerminal_Tutorial.item;

import com.valkryst.VTerminal_Tutorial.Sprite;
import com.valkryst.VTerminal_Tutorial.statistic.Stat;
import lombok.Getter;

import java.util.HashMap;

public class Equipment extends Item {
    /** The slot in which this item can be equipped. */
    @Getter private final EquipmentSlot slot;

    /**
     * Constructs a new piece of Equipment.
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
     *
     * @param slot
     *          The slot in which this item can be equipped.
     */
    public Equipment(final Sprite sprite, final String name, final String description, final HashMap<String, Stat> stats, final EquipmentSlot slot) {
        super(sprite, name, description, stats);
        this.slot = (slot == null ? EquipmentSlot.UNKNOWN : slot);
    }
}
