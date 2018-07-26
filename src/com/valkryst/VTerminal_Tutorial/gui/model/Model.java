package com.valkryst.VTerminal_Tutorial.gui.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class Model {
    /** The listeners of the view. */
    @Getter protected final List<EventListener> eventListeners = new ArrayList<>(0);
}
