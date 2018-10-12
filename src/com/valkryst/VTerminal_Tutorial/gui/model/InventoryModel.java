package com.valkryst.VTerminal_Tutorial.gui.model;

import com.valkryst.VTerminal_Tutorial.item.Inventory;
import lombok.Getter;

public class InventoryModel extends Model {
    private final static int MAX_INVENTORY_SIZE = 26;

    /** The player's inventory. */
    @Getter private Inventory playerInventory;
    /** The inventory being looted. */
    @Getter private Inventory lootInventory;

    /**
     * Sets the player inventory.
     *
     * @param playerInventory
     *          The new player inventory.
     */
    public void setPlayerInventory(final Inventory playerInventory) {
        if (playerInventory == null) {
            this.playerInventory = new Inventory(MAX_INVENTORY_SIZE);
        } else {
            this.playerInventory = playerInventory;
        }
    }

    /**
     * Sets the loot inventory.
     *
     * @param lootInventory
     *          The new loot inventory.
     */
    public void setLootInventory(final Inventory lootInventory) {
        if (lootInventory == null) {
            this.lootInventory = new Inventory(MAX_INVENTORY_SIZE);
        } else {
            this.lootInventory = lootInventory;
        }
    }
}
