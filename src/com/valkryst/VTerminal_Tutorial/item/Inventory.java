package com.valkryst.VTerminal_Tutorial.item;

import java.util.Arrays;
import java.util.HashMap;

public class Inventory {
    /** The equipped items. */
    private final HashMap<EquipmentSlot, Equipment> equipment = new HashMap<>();

    /** The non-equipped items. */
    private final Item[] items;

    /**
     * Constructs a new Inventory.
     *
     * @param size
     *          The number of items that can be held in the inventory, not including equipped items.
     */
    public Inventory(final int size) {
        items = new Item[size];
    }

    /**
     * Equips an item.
     *
     * If another item is equipped in the same slot that the new item is being equipped to, then the currently
     * equipped item is removed and placed in the inventory before the new item is equipped.
     *
     * If there is no room in the inventory, then nothing happens.
     *
     * @param item
     *          The item to equip.
     */
    public void equip(final Equipment item) {
        if (item == null) {
            return;
        }

        final EquipmentSlot slot = item.getSlot();

        if (equipment.get(slot) != null) {
            put(equipment.remove(slot));
        }

        equipment.put(slot, item);
    }

    /**
     * Unequips an item and places it in the inventory.
     *
     * If there is no room in the inventory, then nothing happens.
     *
     * @param slot
     *          The slot of the item to unequip.
     */
    public void unequip(final EquipmentSlot slot) {
        if (put(equipment.get(slot))) {
            equipment.remove(slot);
        }
    }

    /**
     * Places an item in the first available inventory slot.
     *
     * If there is no room in the inventory, then nothing happens.
     *
     * @param item
     *          The item.
     *
     * @return
     *          Whether the item was placed in the inventory.
     */
    public boolean put(final Item item) {
        if (item == null) {
            return true;
        }

        for (int i = 0 ; i < items.length ; i++) {
            if (items[i] == null) {
                items[i] = item;
                return true;
            }
        }

        return false;
    }

    /**
     * Removes an item from the inventory.
     *
     * If multiple matching items exist in the inventory, then only the first is removed.
     *
     * @param item
     *          The item.
     */
    public void remove(final Item item) {
        if (item == null) {
            return;
        }

        for (int i = 0 ; i < items.length ; i++) {
            if (items[i] != null && items[i].equals(item)) {
                items[i] = null;
            }
        }
    }

    /**
     * Removes an item at a specific index.
     *
     * @param index
     *          The index.
     */
    public void remove(final int index) {
        if (index < 0 || index >= items.length) {
            return;
        }

        items[index] = null;
    }

    /** Removes all equipment and items from the inventory. */
    public void clear() {
        equipment.clear();
        Arrays.fill(items, null);
    }

    /**
     * Retrieves the item equipped in a specific slot.
     *
     * @param slot
     *          The slot.
     *
     * @return
     *          The item.
     *          Null if no item is equipped to the slot.
     */
    public Equipment getEquipment(final EquipmentSlot slot) {
        return equipment.get(slot);
    }

    /**
     * Retrieves the item at a specific index.
     *
     * @param index
     *          The index.
     *
     * @return
     *          The item.
     *          Null if the index is outside of the inventory's bounds.
     *          Null if there is no item at the index.
     */
    public Item getItem(final int index) {
        if (index < 0 || index >= items.length) {
            return null;
        }

        return items[index];
    }

    /**
     * Retrieves the first occurrence of an item with a specific name.
     *
     * @param name
     *          The name.
     *
     * @return
     *          Either the item, or null if no item uses the name.
     */
    public Item getItem(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        name = name.toLowerCase();

        for (final Item item : items) {
            if (item != null && item.getName().toLowerCase().equals(name)) {
                return item;
            }
        }

        return null;
    }

    /**
     * Retrieves the size of the inventory, excluding equipment slots.
     *
     * @return
     *          The number of items that can be held in the inventory, not including equipped items.
     */
    public int getSize() {
        return items.length;
    }
}
