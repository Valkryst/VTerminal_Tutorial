package com.valkryst.VTerminal_Tutorial.gui.view;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.builder.ButtonBuilder;
import com.valkryst.VTerminal.builder.LabelBuilder;
import com.valkryst.VTerminal.builder.RadioButtonBuilder;
import com.valkryst.VTerminal.builder.TextAreaBuilder;
import com.valkryst.VTerminal.component.Layer;
import com.valkryst.VTerminal.component.RadioButtonGroup;
import com.valkryst.VTerminal.component.TextArea;
import com.valkryst.VTerminal.printer.RectanglePrinter;
import com.valkryst.VTerminal_Tutorial.gui.model.InventoryModel;
import com.valkryst.VTerminal_Tutorial.item.Equipment;
import com.valkryst.VTerminal_Tutorial.item.EquipmentSlot;
import com.valkryst.VTerminal_Tutorial.item.Inventory;
import com.valkryst.VTerminal_Tutorial.item.Item;

import java.awt.*;

public class InventoryView extends View {
    /** The equipment slot of the item currently selected in the equipment layer. */
    private EquipmentSlot selectedEquipmentSlot;
    /** The currently selected item in the inventory layer. */
    private Item selectedInventoryItem;
    /** The currently selected item in the loot layer. */
    private Item selectedLootItem;

    /** The layer displaying the equipped items. */
    private Layer equipmentLayer;
    /** The layer displaying the inventory contents. */
    private Layer inventoryLayer;
    /** The layer displaying any lootable items. */
    private Layer lootLayer;

    /** The message box to display item information. */
    private TextArea messageBox;

    /**
     * Constructs a new InventoryView.
     *
     * @param screen
     *          The screen on which the view is displayed.
     */
    public InventoryView(final Screen screen) {
        super(screen);
        initializeComponents();
    }

    /** Initializes the components. */
    private void initializeComponents() {
        // Section Borders
        final RectanglePrinter printer = new RectanglePrinter();
        printer.setTitle("Equipment");
        printer.setWidth(80);
        printer.setHeight(11);
        printer.print(this.getTiles(), new Point(0, 0));

        printer.setTitle("Inventory");
        printer.setWidth(41);
        printer.setHeight(30);
        printer.print(this.getTiles(), new Point(0, 10));

        printer.setTitle("Loot");
        printer.setWidth(40);
        printer.setHeight(30);
        printer.print(this.getTiles(), new Point(40, 10));

        // Message Box
        final TextAreaBuilder builder = new TextAreaBuilder();
        builder.setPosition(0, 40);
        builder.setWidth(80);
        builder.setHeight(5);

        builder.setEditable(false);

        messageBox = builder.build();
        this.addComponent(messageBox);
    }

    /**
     * Adds components, that require data from the model, to the view.
     *
     * @param model
     *          The model.
     */
    public void addModelComponents(final InventoryModel model) {
        equipmentLayer = createEquipmentLayer(model);
        inventoryLayer = createInventoryLayer(model);
        lootLayer = createLootLayer(model);

        this.addComponent(equipmentLayer);
        this.addComponent(inventoryLayer);
        this.addComponent(lootLayer);
    }

    /**
     * Creates the equipment layer.
     *
     * @param model
     *          The inventory model containing the player's equipment.
     *
     * @return
     *          The layer.
     */
    private Layer createEquipmentLayer(final InventoryModel model) {
        // Create the Layer
        final int width = 78;
        final int height = 9;
        Layer layer = equipmentLayer;

        if (layer == null) {
            layer = new Layer(new Dimension(width, height));
            layer.getTiles().setPosition(1, 1);
        } else {
            layer.removeAllComponents();
        }

        // Add Equipment Radio Buttons
        final Inventory inventory = model.getPlayerInventory();

        if (inventory == null) {
            final LabelBuilder builder = new LabelBuilder();
            builder.setText("The inventory is empty.");
            builder.setPosition(2, 2);

            layer.addComponent(builder.build());

            return layer;
        }

        final RadioButtonGroup radioButtonGroup = new RadioButtonGroup();
        final RadioButtonBuilder radioButtonBuilder = new RadioButtonBuilder();

        int row = 0;
        int column = 0;

        for (final EquipmentSlot slot : EquipmentSlot.values()) {
            final Item item = inventory.getEquipment(slot);

            /*
             * RadioButton components don't run their onClickFunction if they're already selected.
             *
             * This is because the first button is always selected after we create the layer, we have to manually
             * set the slot here.
             */
            if (row == 0 && column == 0) {
                selectedEquipmentSlot = slot;
            }

            radioButtonBuilder.reset();
            radioButtonBuilder.setText(item == null ? "Empty" : item.getName());
            radioButtonBuilder.setPosition(column, row);
            radioButtonBuilder.setGroup(radioButtonGroup);
            radioButtonBuilder.setOnClickFunction(() -> selectedEquipmentSlot = slot);

            layer.addComponent(radioButtonBuilder.build());

            // We want each column of items to include 6 rows and have space for 40 tiles in-between columns.
            if (row == 6) {
                row = 0;
                column += 40;
            } else {
                row++;
            }
        }

        // Add Inspect Button
        final ButtonBuilder buttonBuilder = new ButtonBuilder();

        buttonBuilder.setText("[Inspect]");
        buttonBuilder.setPosition(0, height - 1);
        buttonBuilder.setOnClickFunction(() -> {
            final Item item = inventory.getEquipment(selectedEquipmentSlot);

            if (item != null) {
                messageBox.appendText(item.getDescription());
            }
        });
        layer.addComponent(buttonBuilder.build());

        // Add Unequip Button
        buttonBuilder.setText("[Unequip]");
        buttonBuilder.setPosition(buttonBuilder.getXPosition() + 10, buttonBuilder.getYPosition());
        buttonBuilder.setOnClickFunction(() -> {
            final Item item = inventory.getEquipment(selectedEquipmentSlot);

            if (item != null) {
                inventory.unequip(selectedEquipmentSlot);

                selectedEquipmentSlot = null;

                refreshEquipmentLayer(model);
                refreshInventoryLayer(model);
            }
        });
        layer.addComponent(buttonBuilder.build());

        return layer;
    }

    /**
     * Creates the inventory layer.
     *
     * @param model
     *          The inventory model containing the player's inventory.
     *
     * @return
     *          The layer.
     */
    private Layer createInventoryLayer(final InventoryModel model) {
        // Create the Layer
        final int width = 38;
        final int height = 28;
        Layer layer = inventoryLayer;

        if (layer == null) {
            layer = new Layer(new Dimension(width, height));
            layer.getTiles().setPosition(1, 11);
        } else {
            layer.removeAllComponents();
        }

        // Check For Empty Inventory
        final Inventory inventory = model.getPlayerInventory();

        if (inventory == null) {
            final LabelBuilder builder = new LabelBuilder();
            builder.setText("The inventory is empty.");
            builder.setPosition(2, 2);

            layer.addComponent(builder.build());

            return layer;
        }

        // Add Item Radio Buttons
        final RadioButtonGroup radioButtonGroup = new RadioButtonGroup();
        final RadioButtonBuilder radioButtonBuilder = new RadioButtonBuilder();

        for (int i = 0 ; i < inventory.getSize() ; i++) {
            final Item item = inventory.getItem(i);

            /*
             * RadioButton components don't run their onClickFunction if they're already selected.
             *
             * This is because the first button is always selected after we create the layer, we have to manually
             * set the item here.
             */
            if (i == 0) {
                selectedInventoryItem = item;
            }

            radioButtonBuilder.reset();
            radioButtonBuilder.setText(item == null ? "Empty" : item.getName());
            radioButtonBuilder.setPosition(0, i);
            radioButtonBuilder.setGroup(radioButtonGroup);
            radioButtonBuilder.setOnClickFunction(() -> selectedInventoryItem = item);

            layer.addComponent(radioButtonBuilder.build());
        }

        // Add Inspect Button
        final ButtonBuilder buttonBuilder = new ButtonBuilder();

        buttonBuilder.setText("[Inspect]");
        buttonBuilder.setPosition(0, height - 1);
        buttonBuilder.setOnClickFunction(() -> {
            if (selectedInventoryItem != null) {
                messageBox.appendText(selectedInventoryItem.getDescription());
            }
        });
        layer.addComponent(buttonBuilder.build());

        // Add Equip Button
        buttonBuilder.setText("[Equip]");
        buttonBuilder.setPosition(buttonBuilder.getXPosition() + 10, buttonBuilder.getYPosition());
        buttonBuilder.setOnClickFunction(() -> {
            if (selectedInventoryItem == null || selectedInventoryItem instanceof Equipment == false) {
                return;
            }

            inventory.remove(selectedInventoryItem);
            inventory.equip((Equipment) selectedInventoryItem);

            selectedInventoryItem = null;

            refreshEquipmentLayer(model);
            refreshInventoryLayer(model);
        });
        layer.addComponent(buttonBuilder.build());

        // Add Drop Button
        buttonBuilder.setText("[Drop]");
        buttonBuilder.setPosition(buttonBuilder.getXPosition() + 8, buttonBuilder.getYPosition());
        buttonBuilder.setOnClickFunction(() -> {
            final Inventory lootInventory = model.getLootInventory();

            // If the loot inventory is full, then don't allow the player to drop.
            if (selectedInventoryItem == null || lootInventory.getTotalItems() == lootInventory.getSize()) {
                return;
            }

            inventory.remove(selectedInventoryItem);
            lootInventory.put(selectedInventoryItem);

            selectedInventoryItem = null;

            refreshInventoryLayer(model);
            refreshLootLayer(model);
        });
        layer.addComponent(buttonBuilder.build());

        return layer;
    }

    /**
     * Creates the loot layer.
     *
     * @param model
     *          The inventory model containing the loot.
     *
     * @return
     *          The layer.
     */
    private Layer createLootLayer(final InventoryModel model) {
        // Create the Layer
        final int width = 38;
        final int height = 28;
        Layer layer = lootLayer;

        if (layer == null) {
            layer = new Layer(new Dimension(width, height));
            layer.getTiles().setPosition(41, 11);
        } else {
            layer.removeAllComponents();
        }

        // Check For Empty Loot Inventory
        final Inventory lootInventory = model.getLootInventory();

        if (lootInventory == null) {
            final LabelBuilder builder = new LabelBuilder();
            builder.setText("There is nothing to loot.");
            builder.setPosition(2, 2);

            layer.addComponent(builder.build());

            return layer;
        }

        // Add Item Radio Buttons
        final RadioButtonGroup radioButtonGroup = new RadioButtonGroup();
        final RadioButtonBuilder radioButtonBuilder = new RadioButtonBuilder();

        for (int i = 0 ; i < lootInventory.getSize() ; i++) {
            final Item item = lootInventory.getItem(i);

            /*
             * RadioButton components don't run their onClickFunction if they're already selected.
             *
             * This is because the first button is always selected after we create the layer, we have to manually
             * set the item here.
             */
            if (i == 0) {
                selectedLootItem = item;
            }

            radioButtonBuilder.reset();
            radioButtonBuilder.setText(item == null ? "Empty" : item.getName());
            radioButtonBuilder.setPosition(0, i);
            radioButtonBuilder.setGroup(radioButtonGroup);
            radioButtonBuilder.setOnClickFunction(() -> selectedLootItem = item);

            layer.addComponent(radioButtonBuilder.build());
        }

        // Add Inspect Button
        final ButtonBuilder buttonBuilder = new ButtonBuilder();

        buttonBuilder.setText("[Inspect]");
        buttonBuilder.setPosition(0, height - 1);
        buttonBuilder.setOnClickFunction(() -> {
            if (selectedLootItem != null) {
                messageBox.appendText(selectedLootItem.getDescription());
            }
        });
        layer.addComponent(buttonBuilder.build());

        // Add Loot Button
        buttonBuilder.setText("[Loot]");
        buttonBuilder.setPosition(buttonBuilder.getXPosition() + 10, buttonBuilder.getYPosition());
        buttonBuilder.setOnClickFunction(() -> {
            final Inventory playerInventory = model.getPlayerInventory();

            // If the player inventory is full, then don't allow the player to loot.
            if (selectedLootItem == null || playerInventory.getTotalItems() == playerInventory.getSize()) {
                return;
            }

            lootInventory.remove(selectedLootItem);
            playerInventory.put(selectedLootItem);

            selectedLootItem = null;

            refreshInventoryLayer(model);
            refreshLootLayer(model);
        });
        layer.addComponent(buttonBuilder.build());

        return layer;
    }

    /**
     * Refreshes the components of the equipment layer, so that it reflects the current state of the equipment
     * portion of the inventory.
     *
     * @param model
     *          The inventory model containing the inventory which contains the equipment.
     */
    public void refreshEquipmentLayer(final InventoryModel model) {
        this.removeComponent(equipmentLayer);
        equipmentLayer = createEquipmentLayer(model);
        this.addComponent(equipmentLayer);
    }

    /**
     * Refreshes the components of the inventory layer, so that it reflects the current state of the non-equipment
     * portion of the inventory.
     *
     * @param model
     *          The inventory model containing the inventory
     */
    public void refreshInventoryLayer(final InventoryModel model) {
        this.removeComponent(inventoryLayer);
        inventoryLayer = createInventoryLayer(model);
        this.addComponent(inventoryLayer);
    }

    /**
     * Refreshes the components of the loot inventory layer, so that it reflects the current state of the loot
     * inventory.
     *
     * @param model
     *          The inventory model containing the loot.
     */
    public void refreshLootLayer(final InventoryModel model) {
        this.removeComponent(lootLayer);
        lootLayer = createLootLayer(model);
        this.addComponent(lootLayer);
    }
}
