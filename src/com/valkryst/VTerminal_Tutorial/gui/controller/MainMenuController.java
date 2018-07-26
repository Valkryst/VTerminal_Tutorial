package com.valkryst.VTerminal_Tutorial.gui.controller;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal_Tutorial.gui.model.MainMenuModel;
import com.valkryst.VTerminal_Tutorial.gui.view.MainMenuView;
import lombok.NonNull;

public class MainMenuController extends Controller<MainMenuView, MainMenuModel> {
    /**
     * Constructs a new MainMenuController.
     *
     * @param screen
     *          The screen on which the view is displayed.
     *
     * @throws NullPointerException
     *          If the screen is null.
     */
    public MainMenuController(final @NonNull Screen screen) {
        super(new MainMenuView(screen), new MainMenuModel());
        initializeEventHandlers(screen);
        screen.addComponent(super.getView());
        screen.draw();
    }

    /**
     * Creates any event handlers required by the view.
     *
     * @param screen
     *          The screen on which the view is displayed.
     */
    public void initializeEventHandlers(final Screen screen) {
        super.view.getButton_new().setOnClickFunction(() -> {
            // Remove this view from the screen.
            super.removeFromScreen(screen);

            // Add the new view to the screen.
            final GameController controller = new GameController(screen);
            controller.addToScreen(screen);
        });

        super.view.getButton_exit().setOnClickFunction(() -> {
            System.exit(0);
        });
    }
}
