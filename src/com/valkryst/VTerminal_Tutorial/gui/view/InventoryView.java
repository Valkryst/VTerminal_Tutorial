package com.valkryst.VTerminal_Tutorial.gui.view;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.builder.ButtonBuilder;
import com.valkryst.VTerminal.builder.LabelBuilder;
import com.valkryst.VTerminal.builder.RadioButtonBuilder;
import com.valkryst.VTerminal.component.Layer;
import com.valkryst.VTerminal.component.RadioButtonGroup;
import com.valkryst.VTerminal.printer.RectanglePrinter;
import com.valkryst.VTerminal_Tutorial.entity.Player;
import com.valkryst.VTerminal_Tutorial.enums.Rarity;
import com.valkryst.VTerminal_Tutorial.enums.Stat;
import com.valkryst.VTerminal_Tutorial.gui.model.InventoryModel;
import com.valkryst.VTerminal_Tutorial.gui.palette.rarity.UnknownColorPalette;
import com.valkryst.VTerminal_Tutorial.item.Equipment;
import com.valkryst.VTerminal_Tutorial.enums.EquipmentSlot;
import com.valkryst.VTerminal_Tutorial.item.Inventory;
import com.valkryst.VTerminal_Tutorial.item.Item;
import com.valkryst.VTerminal_Tutorial.statistic.BoundStatistic;
import com.valkryst.VTerminal_Tutorial.statistic.Statistic;

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
    /** The layer displaying the inspected item. */
    private Layer inspectionLayer;
    /** The layer displaying the player's stats. */
    private Layer statsLayer;

    /**
     * The border dimensions of each layer.
     *
     * 0 - Equipment Layer
     * 1 - Inventory Layer
     * 2 - Loot Layer
     * 3 - Inspection Layer
     * 4 - Stats Layer
     */
    private final static Dimension[] LAYER_BORDER_DIMENSIONS = new Dimension[] {
            new Dimension(80, 10),
            new Dimension(41, 30),
            new Dimension(40, 30),
            new Dimension(41, 39),
            new Dimension(80, 7)
    };

    /**
     * The inner dimensions of each layer.
     *
     * 0 - Equipment Layer
     * 1 - Inventory Layer
     * 2 - Loot Layer
     * 3 - Inspection Layer
     * 4 - Stats Layer
     */
    private final static Dimension[] LAYER_INNER_DIMENSIONS = new Dimension[] {
            new Dimension(LAYER_BORDER_DIMENSIONS[0].width - 2, LAYER_BORDER_DIMENSIONS[0].height - 2),
            new Dimension(LAYER_BORDER_DIMENSIONS[1].width - 2, LAYER_BORDER_DIMENSIONS[1].height - 2),
            new Dimension(LAYER_BORDER_DIMENSIONS[2].width - 2, LAYER_BORDER_DIMENSIONS[2].height - 2),
            new Dimension(LAYER_BORDER_DIMENSIONS[3].width - 2, LAYER_BORDER_DIMENSIONS[3].height - 2),
            new Dimension(LAYER_BORDER_DIMENSIONS[4].width - 2, LAYER_BORDER_DIMENSIONS[4].height - 2),
    };

    /**
     * The origin points of each layer.
     *
     * 0 - Equipment Layer
     * 1 - Inventory Layer
     * 2 - Loot Layer
     * 3 - Inspection Layer
     * 4 - Stats Layer
     */
    private final static Point[] LAYER_POSITIONS = new Point[] {
        new Point(0, 0),
        new Point(0, LAYER_BORDER_DIMENSIONS[0].height - 1),
        new Point(LAYER_BORDER_DIMENSIONS[1].width - 1, LAYER_BORDER_DIMENSIONS[0].height - 1),
        new Point(LAYER_BORDER_DIMENSIONS[0].width - 1, 0),
        new Point(0, LAYER_BORDER_DIMENSIONS[3].height - 1)
    };

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
        final RectanglePrinter printer = new RectanglePrinter();
        printer.setTitle("Equipment");
        printer.setWidth(LAYER_BORDER_DIMENSIONS[0].width);
        printer.setHeight(LAYER_BORDER_DIMENSIONS[0].height);
        printer.print(this.getTiles(), LAYER_POSITIONS[0]);

        printer.setTitle("Inventory");
        printer.setWidth(LAYER_BORDER_DIMENSIONS[1].width);
        printer.setHeight(LAYER_BORDER_DIMENSIONS[1].height);
        printer.print(this.getTiles(), LAYER_POSITIONS[1]);

        printer.setTitle("Loot");
        printer.setWidth(LAYER_BORDER_DIMENSIONS[2].width);
        printer.setHeight(LAYER_BORDER_DIMENSIONS[2].height);
        printer.print(this.getTiles(), LAYER_POSITIONS[2]);

        printer.setTitle(""); // Inspection Layer Border
        printer.setWidth(LAYER_BORDER_DIMENSIONS[3].width);
        printer.setHeight(LAYER_BORDER_DIMENSIONS[3].height);
        printer.print(this.getTiles(), LAYER_POSITIONS[3]);

        printer.setTitle("Stat");
        printer.setWidth(LAYER_BORDER_DIMENSIONS[4].width);
        printer.setHeight(LAYER_BORDER_DIMENSIONS[4].height);
        printer.print(this.getTiles(), LAYER_POSITIONS[4]);
    }

    /**
     * Adds components, that require data from the model, to the view.
     *
     * @param player
     *          The player entity.
     *
     * @param model
     *          The model.
     */
    public void addModelComponents(final Player player, final InventoryModel model) {
        equipmentLayer = createEquipmentLayer(player, model);
        inventoryLayer = createInventoryLayer(player, model);
        lootLayer = createLootLayer(player, model);
        inspectionLayer = createInspectionLayer(null);
        statsLayer = createStatsLayer(player, model);

        this.addComponent(equipmentLayer);
        this.addComponent(inventoryLayer);
        this.addComponent(lootLayer);
        this.addComponent(inspectionLayer);
        this.addComponent(statsLayer);
    }

    /**
     * Creates the equipment layer.
     *
     * @param player
     *          The player entity.
     *
     * @param model
     *          The inventory model containing the player's equipment.
     *
     * @return
     *          The layer.
     */
    private Layer createEquipmentLayer(final Player player, final InventoryModel model) {
        // Create the Layer
        Layer layer = equipmentLayer;

        if (layer == null) {
            layer = new Layer(LAYER_INNER_DIMENSIONS[0]);
            layer.getTiles().setPosition(LAYER_POSITIONS[0].x + 1, LAYER_POSITIONS[0].y + 1);
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
            final Equipment item = inventory.getEquipment(slot);

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
            radioButtonBuilder.setColorPalette((item == null ? new UnknownColorPalette() : item.getRarity().getColorPalette()));
            layer.addComponent(radioButtonBuilder.build());

            // We want each column of items to include 5 rows and have space for 40 tiles in-between columns.
            if (row == 5) {
                row = 0;
                column += 40;
            } else {
                row++;
            }
        }

        // Add Inspect Button
        final ButtonBuilder buttonBuilder = new ButtonBuilder();

        buttonBuilder.setText("[Inspect]");
        buttonBuilder.setPosition(0, layer.getTiles().getHeight() - 1);
        buttonBuilder.setOnClickFunction(() -> {
            final Item item = inventory.getEquipment(selectedEquipmentSlot);

            if (item != null) {
                refreshInspectionLayer(item);
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

                refreshEquipmentLayer(player, model);
                refreshInventoryLayer(player, model);
                refreshStatsLayer(player, model);
            }
        });
        layer.addComponent(buttonBuilder.build());

        return layer;
    }

    /**
     * Creates the inventory layer.
     *
     * @param player
     *          The player entity.
     *
     * @param model
     *          The inventory model containing the player's inventory.
     *
     * @return
     *          The layer.
     */
    private Layer createInventoryLayer(final Player player, final InventoryModel model) {
        // Create the Layer
        Layer layer = inventoryLayer;

        if (layer == null) {
            layer = new Layer(LAYER_INNER_DIMENSIONS[1]);
            layer.getTiles().setPosition(LAYER_POSITIONS[1].x + 1, LAYER_POSITIONS[1].y + 1);
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

            if (item == null || item instanceof Equipment == false) {
                radioButtonBuilder.setColorPalette(new UnknownColorPalette());
            } else {
                radioButtonBuilder.setColorPalette(((Equipment) item).getRarity().getColorPalette());
            }

            layer.addComponent(radioButtonBuilder.build());
        }

        // Add Inspect Button
        final ButtonBuilder buttonBuilder = new ButtonBuilder();

        buttonBuilder.setText("[Inspect]");
        buttonBuilder.setPosition(0, layer.getTiles().getHeight() - 1);
        buttonBuilder.setOnClickFunction(() -> {
            if (selectedInventoryItem != null) {
                refreshInspectionLayer(selectedInventoryItem);
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

            refreshEquipmentLayer(player, model);
            refreshInventoryLayer(player, model);
            refreshStatsLayer(player, model);
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

            refreshInventoryLayer(player, model);
            refreshLootLayer(player, model);
        });
        layer.addComponent(buttonBuilder.build());

        return layer;
    }

    /**
     * Creates the loot layer.
     *
     * @param player
     *          The player entity.
     *
     * @param model
     *          The inventory model containing the loot.
     *
     * @return
     *          The layer.
     */
    private Layer createLootLayer(final Player player, final InventoryModel model) {
        // Create the Layer
        Layer layer = lootLayer;

        if (layer == null) {
            layer = new Layer(LAYER_INNER_DIMENSIONS[2]);
            layer.getTiles().setPosition(LAYER_POSITIONS[2].x + 1, LAYER_POSITIONS[2].y + 1);
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

            if (item == null || item instanceof Equipment == false) {
                radioButtonBuilder.setColorPalette(new UnknownColorPalette());
            } else {
                radioButtonBuilder.setColorPalette(((Equipment) item).getRarity().getColorPalette());
            }

            layer.addComponent(radioButtonBuilder.build());
        }

        // Add Inspect Button
        final ButtonBuilder buttonBuilder = new ButtonBuilder();

        buttonBuilder.setText("[Inspect]");
        buttonBuilder.setPosition(0, layer.getTiles().getHeight() - 1);
        buttonBuilder.setOnClickFunction(() -> {
            if (selectedLootItem != null) {
                refreshInspectionLayer(selectedLootItem);
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

            refreshInventoryLayer(player, model);
            refreshLootLayer(player, model);
        });
        layer.addComponent(buttonBuilder.build());

        return layer;
    }

    /**
     * Creates the inspection layer.
     *
     * @param item
     *          The item being inspected.
     *
     * @return
     *          The layer.
     */
    private Layer createInspectionLayer(final Item item) {
        // Create the Layer
        Layer layer = inspectionLayer;

        if (layer == null) {
            layer = new Layer(LAYER_INNER_DIMENSIONS[3]);
            layer.getTiles().setPosition(LAYER_POSITIONS[3].x + 1, LAYER_POSITIONS[3].y + 1);
        } else {
            layer.removeAllComponents();
        }

        // Reset the foreground color of the tiles where the border's title will be.
        for (int x = 79 ; x < this.getTiles().getWidth() ; x++) {
            this.getTileAt(x, 0).setForegroundColor(Color.WHITE);
        }

        // Check for null item and re-print the border.
        final RectanglePrinter printer = new RectanglePrinter();
        printer.setWidth(LAYER_BORDER_DIMENSIONS[3].width);
        printer.setHeight(LAYER_BORDER_DIMENSIONS[3].height);

        if (item == null || item instanceof Equipment == false) {
            printer.setTitle("");
            printer.print(this.getTiles(), new Point(79, 0));

            return layer;
        }

        printer.setTitle(item.getName());
        printer.print(this.getTiles(), new Point(79, 0));

        // Color the border's title to reflect the rarity of the item.
        final Rarity rarity = ((Equipment) item).getRarity();
        final Color foregroundColor = rarity.getColorPalette().getRadioButton_defaultForeground();

        for (int x = 81; x < 81 + item.getName().length() ; x++) {
            this.getTileAt(x, 0).setForegroundColor(foregroundColor);
        }

        // Add the item's information panel.
        final Layer informationPanel = item.getInformationPanel();
        informationPanel.getTiles().setPosition(0, 0);
        layer.addComponent(informationPanel);

        return layer;
    }

    /**
     * Creates the stats layer.
     *
     * @param player
     *          The player entity.
     *
     * @param model
     *          The inventory model containing the player's equipment.
     *
     * @return
     *          The layer.
     */
    private Layer createStatsLayer(final Player player, final InventoryModel model) {
        // Create the Layer
        Layer layer = statsLayer;

        if (layer == null) {
            layer = new Layer(LAYER_INNER_DIMENSIONS[4]);
            layer.getTiles().setPosition(LAYER_POSITIONS[4].x + 1, LAYER_POSITIONS[4].y + 1);

            layer.addComponent();
        } else {
            layer.removeAllComponents();
        }

        // Calculate Equipment Stat Totals
        final Inventory inventory = model.getPlayerInventory();
        final int[] statsMin = new int[Stat.values().length];
        final int[] statsMax = new int[Stat.values().length];

        if (inventory != null) {
            for (final EquipmentSlot slot : EquipmentSlot.values()) {
                final Equipment item = inventory.getEquipment(slot);

                if (item == null) {
                    continue;
                }

                for (final Stat stat : Stat.values()) {
                    final Statistic temp = item.getStat(stat);

                    if (temp != null) {
                        if (temp instanceof BoundStatistic) {
                            statsMin[stat.ordinal()] += ((BoundStatistic) temp).getMinValue();
                            statsMax[stat.ordinal()] += ((BoundStatistic) temp).getMaxValue();
                        } else {
                            statsMin[stat.ordinal()] += temp.getValue();
                            statsMax[stat.ordinal()] += temp.getValue();
                        }
                    }
                }
            }
        }

        // Calculate Player Stat Totals
        statsMin[Stat.LEVEL.ordinal()] += player.getStat(Stat.LEVEL).getValue();
        statsMax[Stat.LEVEL.ordinal()] += player.getStat(Stat.LEVEL).getValue();

        statsMin[Stat.EXPERIENCE.ordinal()] += player.getStat(Stat.EXPERIENCE).getValue();
        statsMax[Stat.EXPERIENCE.ordinal()] += player.getStat(Stat.EXPERIENCE).getValue();

        statsMin[Stat.HEALTH.ordinal()] += player.getStat(Stat.HEALTH).getValue();
        statsMax[Stat.HEALTH.ordinal()] += player.getStat(Stat.HEALTH).getValue();

        // Display Stat Totals
        final LabelBuilder labelBuilder = new LabelBuilder();

        int row = 0;
        int column = 0;

        for (final Stat stat : Stat.values()) {
            labelBuilder.reset();
            labelBuilder.setPosition(column, row);

            if (statsMin[stat.ordinal()] == statsMax[stat.ordinal()]) {
                labelBuilder.setText(stat.getName() + ": " + statsMin[stat.ordinal()]);
            } else {
                labelBuilder.setText(stat.getName() + ": " + statsMin[stat.ordinal()] + "-" + statsMax[stat.ordinal()]);
            }

            layer.addComponent(labelBuilder.build());

            // We want each column of items to include 5 rows and have space for 20 tiles in-between columns.
            if (row == 5) {
                row = 0;
                column += 20;
            } else {
                row++;
            }
        }

        return layer;
    }

    /**
     * Refreshes the components of the equipment layer, so that it reflects the current state of the equipment
     * portion of the inventory.
     *
     * @param player
     *          The player entity.
     *
     * @param model
     *          The inventory model containing the inventory which contains the equipment.
     */
    public void refreshEquipmentLayer(final Player player, final InventoryModel model) {
        this.removeComponent(equipmentLayer);
        equipmentLayer = createEquipmentLayer(player, model);
        this.addComponent(equipmentLayer);
    }

    /**
     * Refreshes the components of the inventory layer, so that it reflects the current state of the non-equipment
     * portion of the inventory.
     *
     * @param player
     *          The player entity.
     *
     * @param model
     *          The inventory model containing the inventory
     */
    public void refreshInventoryLayer(final Player player, final InventoryModel model) {
        this.removeComponent(inventoryLayer);
        inventoryLayer = createInventoryLayer(player, model);
        this.addComponent(inventoryLayer);
    }

    /**
     * Refreshes the components of the loot inventory layer, so that it reflects the current state of the loot
     * inventory.
     *
     * @param player
     *          The player entity.
     *
     * @param model
     *          The inventory model containing the loot.
     */
    public void refreshLootLayer(final Player player, final InventoryModel model) {
        this.removeComponent(lootLayer);
        lootLayer = createLootLayer(player, model);
        this.addComponent(lootLayer);
    }

    /**
     * Refreshes the components of the inspection layer, so it displays the specified item.
     *
     * @param item
     *          The item to display.
     */
    public void refreshInspectionLayer(final Item item) {
        this.removeComponent(inspectionLayer);
        inspectionLayer = createInspectionLayer(item);
        this.addComponent(inspectionLayer);
    }

    /**
     * Refreshes the components of the stats layer, so that it reflects the current stats of the equipped items.
     *
     * @param player
     *          The player entity.
     *
     * @param model
     *          The inventory model containing the inventory which contains the equipment.
     */
    public void refreshStatsLayer(final Player player, final InventoryModel model) {
        this.removeComponent(statsLayer);
        statsLayer = createStatsLayer(player, model);
        this.addComponent(statsLayer);
    }
}
