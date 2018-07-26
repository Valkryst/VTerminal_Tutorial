package com.valkryst.VTerminal_Tutorial.gui.controller;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal_Tutorial.gui.model.Model;
import com.valkryst.VTerminal_Tutorial.gui.view.View;
import lombok.Getter;
import lombok.NonNull;

public class Controller<V extends View, M extends Model> {
    /** The view. */
    @Getter protected final V view;

    /** The model. */
    @Getter protected final M model;

    /**
     * Constructs a new Controller.
     *
     * @param view
     *          The view.
     *
     * @param model
     *          The model.
     *
     * @throws NullPointerException
     *          If the view or model are null.
     */
    public Controller(final @NonNull V view, final @NonNull M model) {
        this.view = view;
        this.model = model;
    }

    /**
     * Adds a view to a screen after removing any existing views or components from the screen.
     *
     * @param screen
     *          The screen on which the view is displayed.
     *
     * @param controller
     *          The controller of the view to add to the screen.
     */
    public static void swapViews(final Screen screen, final Controller controller) {
        if (screen == null || controller == null) {
            return;
        }

        // Remove existing views and components.
        screen.removeAllComponents();

        // Add the new view and any event listeners it requires.
        screen.addComponent(controller.getView());
        controller.getModel().getEventListeners().forEach(screen::addListener);
    }
}
