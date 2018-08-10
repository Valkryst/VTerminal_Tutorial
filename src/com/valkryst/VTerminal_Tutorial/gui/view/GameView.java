package com.valkryst.VTerminal_Tutorial.gui.view;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.builder.TextAreaBuilder;
import com.valkryst.VTerminal.component.TextArea;
import com.valkryst.VTerminal_Tutorial.Map;
import com.valkryst.VTerminal_Tutorial.Sprite;
import com.valkryst.VTerminal_Tutorial.entity.Entity;
import com.valkryst.VTerminal_Tutorial.entity.Player;
import com.valkryst.VTerminal_Tutorial.gui.model.GameModel;
import lombok.Getter;

import java.awt.*;

public class GameView extends View {
    /** The area in which messages are displayed. */
    @Getter private TextArea messageBox;

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

        map.getEntities().add(enemy);
        this.addComponent(map);

        this.addComponent(player);
        player.getLineOfSight().showLOSOnMap(map);

        this.addComponent(enemy);

        this.addComponent(messageBox);
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
}
