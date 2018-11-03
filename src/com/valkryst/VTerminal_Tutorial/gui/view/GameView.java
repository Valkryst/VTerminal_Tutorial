package com.valkryst.VTerminal_Tutorial.gui.view;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.builder.TextAreaBuilder;
import com.valkryst.VTerminal.component.Layer;
import com.valkryst.VTerminal.component.TextArea;
import com.valkryst.VTerminal.printer.RectanglePrinter;
import com.valkryst.VTerminal_Tutorial.Map;
import com.valkryst.VTerminal_Tutorial.Sprite;
import com.valkryst.VTerminal_Tutorial.entity.Entity;
import com.valkryst.VTerminal_Tutorial.entity.Player;
import com.valkryst.VTerminal_Tutorial.gui.model.GameModel;
import com.valkryst.VTerminal_Tutorial.item.Equipment;
import com.valkryst.VTerminal_Tutorial.item.EquipmentSlot;
import com.valkryst.VTerminal_Tutorial.statistic.BoundStat;
import com.valkryst.VTerminal_Tutorial.statistic.Stat;
import lombok.Getter;

import java.awt.*;

public class GameView extends View {
    /** The area in which messages are displayed. */
    @Getter private TextArea messageBox;

    /** The currently displayed player information. */
    private Layer playerInfoView;

    /** The currently displayed target information. */
    private Layer targetInfoView;

    /**
     * Constructs a new GameView.
     *
     * @param screen
     *          The screen on which the view is displayed.
     */
    public GameView(final Screen screen) {
        super(screen);
    }

    /**
     * Adds any components, defined in the model, to the view.
     *
     * @param model
     *          The model.
     */
    public void addModelComponents(final GameModel model) {
        initializeComponents();

        final Map map = model.getMap();
        final Player player = model.getPlayer();
        final Entity enemy = new Entity(Sprite.ENEMY, new Point(15, 12), "Gary");

        displayPlayerInformation(player);
        displayTargetInformation(null);

        map.getEntities().add(enemy);
        this.addComponent(map);

        this.addComponent(player);
        player.getLineOfSight().showLOSOnMap(map);

        this.addComponent(enemy);

        this.addComponent(messageBox);

        // Add Equipment to Player
        final Equipment sword = new Equipment("Sword", "A Sword", null, EquipmentSlot.MAIN_HAND);
        sword.addStat(new BoundStat("Damage", 1, 10));
        player.getInventory().equip(sword);

        // Add Armor to Target
        final Equipment shield = new Equipment("Shield", "A Shield", null, EquipmentSlot.OFF_HAND);
        shield.addStat(new Stat("Armor", 3));
        enemy.getInventory().equip(shield);
    }

    /** Initializes the components. */
    private void initializeComponents() {
        // Message Box
        final TextAreaBuilder builder = new TextAreaBuilder();
        builder.setPosition(0, 40);
        builder.setWidth(80);
        builder.setHeight(5);

        builder.setEditable(false);

        messageBox = builder.build();
    }

    /**
     * Displays the information of a player entity.
     *
     * @param player
     *          The player.
     */
    public void displayPlayerInformation(final Player player) {
        // Set the information panel.
        Layer layer = player.getInformationPanel();
        layer.getTiles().setPosition(80, 0);

        if (playerInfoView != null) {
            super.removeComponent(playerInfoView);
        }

        playerInfoView = layer;
        super.addComponent(playerInfoView);
    }

    /**
     * Displays the information of a targeted entity.
     *
     * @param entity
     *          The target.
     */
    public void displayTargetInformation(final Entity entity) {
        final Layer layer;

        if (entity == null) {
            layer = new Layer(new Dimension(40, 8));

            // Print border
            final RectanglePrinter rectanglePrinter = new RectanglePrinter();
            rectanglePrinter.setWidth(40);
            rectanglePrinter.setHeight(8);
            rectanglePrinter.setTitle("No Target");
            rectanglePrinter.print(layer.getTiles(), new Point(0, 0));
        } else {
            layer = entity.getInformationPanel();
        }

        layer.getTiles().setPosition(80, 8);

        if (targetInfoView != null) {
            super.removeComponent(targetInfoView);
        }

        targetInfoView = layer;
        super.addComponent(targetInfoView);
    }
}
