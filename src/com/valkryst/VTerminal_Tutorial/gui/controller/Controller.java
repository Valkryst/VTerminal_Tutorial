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
     * Removes all components from a screen, then adds the view to the screen.
     *
     * @param screen
     *          The screen.
     */
    public void addViewToScreen(final Screen screen) {
        if (screen == null) {
            return;
        }

        screen.removeAllComponents();
        screen.addComponent(view);
        addEventListenersTo(screen);
    }

    /**
     * Adds all of the event listeners to a screen.
     *
     * @param screen
     *          The screen.
     */
    public void addEventListenersTo(final Screen screen) {
        if (screen == null) {
            return;
        }

        for (final EventListener listener : model.getEventListeners()) {
            screen.addListener(listener);
        }
    }

    /**
     * Removes all of the event listeners from a screen.
     *
     * @param screen
     *          The screen.
     */
    public void removeEventListenersFrom(final Screen screen) {
        if (screen == null) {
            return;
        }

        for (final EventListener listener : model.getEventListeners()) {
            screen.removeListener(listener);
        }
    }
}
