package com.valkryst.VTerminal_Tutorial.gui.model;

import com.valkryst.VTerminal_Tutorial.item.Inventory;
import lombok.Getter;

public class InventoryModel extends Model {
    private final static Inventory EMPTY_INVENTORY = new Inventory(0);

    /** The player's inventory. */
    @Getter private final Inventory playerInventory;
    /** The inventory being looted. */
    @Getter private final Inventory lootInventory;

    /**
     * Constructs a new InventoryModel.
     *
     * @param playerInventory
     *          The player's inventory.
     */
    public InventoryModel(final Inventory playerInventory) {
        if (playerInventory == null) {
            this.playerInventory = EMPTY_INVENTORY;
        } else {
            this.playerInventory = playerInventory;
        }

        lootInventory = EMPTY_INVENTORY;
    }

    /**
     * Constructs a new InventoryModel.
     *
     * @param playerInventory
     *          The player's inventory.
     *
     * @param lootInventory
     *          The inventory being looted.
     */
    public InventoryModel(final Inventory playerInventory, final Inventory lootInventory) {
        if (playerInventory == null) {
            this.playerInventory = EMPTY_INVENTORY;
        } else {
            this.playerInventory = playerInventory;
        }

        if (lootInventory == null) {
            this.lootInventory = EMPTY_INVENTORY;
        } else {
            this.lootInventory = lootInventory;
        }
    }
}
