package com.valkryst.VTerminal_Tutorial.item;

import com.valkryst.VDice.DiceRoller;
import com.valkryst.VTerminal_Tutorial.Sprite;
import com.valkryst.VTerminal_Tutorial.statistic.BoundStat;
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

    /**
     * Uses the damage stat to perform a damage roll. The result is a value between the min and max damage
     * values of the stat.
     *
     * If this piece of equipment doesn't have a damage stat, then 0 is returned.
     *
     * @return
     *          The rolled damage value.
     */
    public int rollDamage() {
        final BoundStat damageStat = (BoundStat) super.getStat("Damage");

        if (damageStat == null) {
            return 0;
        }

        final int minDamage = damageStat.getMinValue();
        final int maxDamage = damageStat.getMaxValue();

        final DiceRoller diceRoller = new DiceRoller();
        diceRoller.addDice(1, maxDamage - minDamage);

        return diceRoller.roll() + minDamage;
    }

    /**
     * Retrieves the armor value from the armor stat.
     *
     * If this piece of equipment doesn't have an armor stat, then 0 is returned.
     *
     * @return
     *          The armor value of the armor stat.
     */
    public int getArmor() {
        final Stat armor = super.getStat("Armor");

        if (armor == null) {
            return 0;
        }

        return armor.getValue();
    }
}
