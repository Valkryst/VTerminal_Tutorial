package com.valkryst.VTerminal_Tutorial.item.loot;

import com.valkryst.VTerminal_Tutorial.item.Item;

import java.util.ArrayList;
import java.util.List;

public class LootTable {
    /** The set of items that can be dropped. */
    private final List<LootEntry> entries;

    /** Constructs a new empty LootTable. */
    public LootTable() {
        entries = new ArrayList<>();
    }

    /**
     * Constructs a new LootTable.
     *
     * @param lootEntries
     *          The set of items that can be dropped.
     */
    public LootTable(final List<LootEntry> lootEntries) {
        if (lootEntries == null) {
            this.entries = new ArrayList<>();
        } else {
            this.entries = lootEntries;
        }
    }

    /**
     * Retrieves a set of loot from the loot table.
     *
     * @return
     *        The loot.
     */
    public List<Item> loot() {
        final List<Item> loot = new ArrayList<>();

        entries.forEach(entry -> {
            if (entry.drop() && (entry.getItem() != null)) {
                loot.add(entry.getItem());
            }
        });

        return loot;
    }

    /**
     * Adds a loot entry to the table.
     *
     * @param entry
     *          The entry.
     */
    public void addEntry(final LootEntry entry) {
        if (entry != null && (entry.getItem() != null)) {
            entries.add(entry);
        }
    }

    /**
     * Removes a loot entry from the table.
     *
     * @param entry
     *          The entry.
     */
    public void removeEntry(final LootEntry entry) {
        if (entry != null) {
            entries.remove(entry);
        }
    }
}
