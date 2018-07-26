package com.valkryst.VTerminal_Tutorial.gui.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class Model {
    /** All listeners that are not a part of the view's components. */
    @Getter protected final List<EventListener> eventListeners = new ArrayList<>(0);
}
