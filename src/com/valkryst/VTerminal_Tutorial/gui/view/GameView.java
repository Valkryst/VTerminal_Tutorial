package com.valkryst.VTerminal_Tutorial.gui.view;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal_Tutorial.Map;
import com.valkryst.VTerminal_Tutorial.entity.Player;
import com.valkryst.VTerminal_Tutorial.gui.model.GameModel;

public class GameView extends View {
    /**
     * Constructs a new GameView.
     *
     * @param screen
     *          The screen on which the view is displayed.
     */
    public GameView(final Screen screen) {
        super(screen);
    }

    public void addModelComponents(final GameModel model) {
        final Map map = model.getMap();
        final Player player = model.getPlayer();

        this.addComponent(map);

        this.addComponent(player);
        player.getLineOfSight().showLOSOnMap(map);
    }
}
