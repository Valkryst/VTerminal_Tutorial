package com.valkryst.VTerminal_Tutorial.gui.view;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.builder.ButtonBuilder;
import com.valkryst.VTerminal.component.Button;
import lombok.Getter;

public class MainMenuView extends View {
    @Getter private Button button_new;
    @Getter private Button button_exit;

    /**
     * Constructs a new MainMenuView.
     *
     * @param screen
     *          The screen on which the view is displayed.
     */
    public MainMenuView(final Screen screen) {
        super(screen);
        initializeComponents();

        this.addComponent(button_new);
        this.addComponent(button_exit);
    }

    /** Initializes the components. */
    private void initializeComponents() {
        final ButtonBuilder buttonBuilder = new ButtonBuilder();
        buttonBuilder.setText("New Game");
        buttonBuilder.setPosition(34, 16);
        button_new = buttonBuilder.build();

        buttonBuilder.setText("Exit");
        buttonBuilder.setPosition(36, 18);
        button_exit = buttonBuilder.build();
    }
}
