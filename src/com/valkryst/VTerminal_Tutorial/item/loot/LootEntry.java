package com.valkryst.VTerminal_Tutorial.item.loot;

import com.valkryst.VDice.DiceRoller;
import com.valkryst.VTerminal_Tutorial.item.Item;
import lombok.Getter;

public class LootEntry {
    /** The item to drop. */
    @Getter private final Item item;

    /** The drop chance. */
    @Getter private final int dropChance;

    /**
     * Constructs a new LootEntry.
     *
     * @param item
     *          The item to drop.
     *
     * @param dropChance
     *          The drop chance.
     */
    public LootEntry(final Item item, final int dropChance) {
        this.item = item;

        if (dropChance > 100) {
            this.dropChance = 100;
        } else if (dropChance < 0) {
            this.dropChance = 0;
        } else {
            this.dropChance = dropChance;
        }
    }

    /**
     * Determines whether or not the loot is dropped.
     *
     * @return
     *          Whether or not the loot is dropped.
     */
    public boolean drop() {
        if (dropChance == 0) {
            return false;
        } else {
            final DiceRoller diceRoller = new DiceRoller();
            diceRoller.addDice(100, 1);

            return diceRoller.roll() <= dropChance;
        }
    }
}
