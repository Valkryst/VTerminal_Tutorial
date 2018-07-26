package com.valkryst.VTerminal_Tutorial.gui.view;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.component.Layer;

import java.awt.*;

public class View extends Layer {
    /**
     * Constructs a new View that fills the entire screen.
     *
     * @param screen
     *          The screen on which the view is displayed.
     */
    public View(final Screen screen) {
        super(new Dimension(screen.getWidth(), screen.getHeight()));
    }
}
