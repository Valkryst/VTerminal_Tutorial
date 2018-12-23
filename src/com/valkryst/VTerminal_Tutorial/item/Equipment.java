package com.valkryst.VTerminal_Tutorial.item;

import com.valkryst.VDice.DiceRoller;
import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.TileGrid;
import com.valkryst.VTerminal.builder.TextAreaBuilder;
import com.valkryst.VTerminal.component.Label;
import com.valkryst.VTerminal.component.Layer;
import com.valkryst.VTerminal.component.TextArea;
import com.valkryst.VTerminal.printer.ImagePrinter;
import com.valkryst.VTerminal_Tutorial.enums.EquipmentSlot;
import com.valkryst.VTerminal_Tutorial.enums.Material;
import com.valkryst.VTerminal_Tutorial.enums.Rarity;
import com.valkryst.VTerminal_Tutorial.enums.Stat;
import com.valkryst.VTerminal_Tutorial.enums.EquipmentModifier;
import com.valkryst.VTerminal_Tutorial.gui.palette.ItemDescriptionColorPalette;
import com.valkryst.VTerminal_Tutorial.statistic.BoundStatistic;
import com.valkryst.VTerminal_Tutorial.statistic.Statistic;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class Equipment extends Item {
    /** The rarity. */
    @Getter private final Rarity rarity;

    /** The material. */
    @Getter private final Material material;

    /** The slot in which this item can be equipped. */
    @Getter private final EquipmentSlot slot;

    /** The statistic modifiers. */
    @Getter private final EquipmentModifier modifier;

    /**
     * Constructs a new piece of Equipment.
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
     * @param rarity
     *          The rarity.
     *
     * @param material
     *          The material.
     *
     * @param modifier
     *          The modifier.
     *
     * @param slot
     *          The slot in which this item can be equipped.
     */
    public Equipment(final String name, final String description, final HashMap<Stat, Statistic> stats, final Rarity rarity, final Material material, final EquipmentModifier modifier, final EquipmentSlot slot) {
        super(name + (modifier == null ? "" : modifier.getSuffix()), description, stats);
        this.rarity = (rarity == null ? Rarity.COMMON : rarity);
        this.material = (material == null ? Material.COPPER : material);
        this.modifier = (modifier == null ? EquipmentModifier.NONE : modifier);
        this.slot = slot;
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
        final BoundStatistic damageStat = (BoundStatistic) super.getStat(Stat.DAMAGE);

        if (damageStat == null) {
            return 0;
        }

        final int minDamage = damageStat.getMinValue();
        final int maxDamage = damageStat.getMaxValue();

        final DiceRoller diceRoller = new DiceRoller();
        diceRoller.addDice(maxDamage - minDamage, 1);

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
        final Statistic armor = super.getStat(Stat.ARMOR);

        if (armor == null) {
            return 0;
        }

        return armor.getValue();
    }

    @Override
    public Layer getInformationPanel() {
        final Layer layer = super.getInformationPanel();

        // Add Icon
        final Layer iconLayer = loadIcon();
        iconLayer.getTiles().setPosition(11, 1);
        layer.addComponent(iconLayer);

        // Display Stats
        int row = 0;

        Label label = (this.getStat(Stat.DAMAGE) == null ? this.getStat(Stat.ARMOR).getLabel() : this.getStat(Stat.DAMAGE).getLabel());
        label.getTiles().setPosition(1, 10);
        layer.addComponent(label);

        for (; row < modifier.getStats().length ; row++) {
            final Statistic stat = this.getStat(modifier.getStats()[row]);

            if (stat != null) {
                label = stat.getLabel();

                final TileGrid tiles = label.getTiles();
                tiles.setPosition(1, 11 + row);
                layer.addComponent(label);
            }
        }

        // Display Description.
        row += 11;

        final TextAreaBuilder textAreaBuilder = new TextAreaBuilder();
        textAreaBuilder.setWidth(layer.getTiles().getWidth() - 2);
        textAreaBuilder.setEditable(false);

        final TextArea descriptionArea = textAreaBuilder.build();
        descriptionArea.getTiles().setPosition(1, row + 1);
        descriptionArea.appendText(super.getDescription());
        descriptionArea.setColorPalette(new ItemDescriptionColorPalette(), true);

        layer.addComponent(descriptionArea);

        return layer;
    }

    /**
     * Loads the icon from within the Jar file, then prints it to a Layer.
     *
     * @return
     *          The layer containing the logo.
     */
    private Layer loadIcon() {
        final Layer layer = new Layer(new Dimension(16, 8), new Point(0, 0));

        final Thread thread = Thread.currentThread();
        final ClassLoader classLoader = thread.getContextClassLoader();

        try (final InputStream is = classLoader.getResourceAsStream("Icons/" + slot.name() + "/" + material.name() + ".png")) {
            final BufferedImage image = ImageIO.read(is);

            final ImagePrinter printer = new ImagePrinter(image);
            printer.printDetailed(layer.getTiles(), new Point(0, 0));
        } catch (final IOException | IllegalArgumentException e) {
            // todo Log the error message.

            // If we're unable to load the logo, then we'll set the layer's colors to something that stands out.
            final TileGrid layerGrid = layer.getTiles();

            for (int y = 0 ; y < layerGrid.getHeight() ; y++) {
                for (int x = 0 ; x < layerGrid.getWidth() ; x++) {
                    final Tile tile = layerGrid.getTileAt(x, y);
                    tile.setCharacter('?');
                    tile.setForegroundColor(Color.GREEN);
                    tile.setBackgroundColor(Color.MAGENTA);
                }
            }
        }

        return layer;
    }
}
