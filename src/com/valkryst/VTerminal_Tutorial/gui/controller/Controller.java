package com.valkryst.VTerminal_Tutorial.gui.controller;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal_Tutorial.gui.model.Model;
import com.valkryst.VTerminal_Tutorial.gui.view.View;
import lombok.Getter;
import lombok.NonNull;

import java.util.EventListener;

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
     * Adds the view to a screen after removing all other components from the screen.
     *
     * @param screen
     *          The screen on which the view is displayed.
     */
    public void addToScreen(final Screen screen) {
        if (screen == null) {
            return;
        }

        screen.addComponent(view);

        for (final EventListener listener : model.getEventListeners()) {
            screen.addListener(listener);
        }
    }

    /**
     * Removes the view from a screen.
     *
     * @param screen
     *          The screen on which the view is displayed.
     */
    public void removeFromScreen(final Screen screen) {
        if (screen == null) {
            return;
        }

        screen.removeComponent(view);

        for (final EventListener listener : model.getEventListeners()) {
            screen.removeListener(listener);
        }
    }
}
