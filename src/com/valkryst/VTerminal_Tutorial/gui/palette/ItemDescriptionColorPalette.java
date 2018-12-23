package com.valkryst.VTerminal_Tutorial.gui.palette;

import com.valkryst.VTerminal.palette.ColorPalette;
import lombok.Getter;

import java.awt.*;

public class ItemDescriptionColorPalette extends ColorPalette {
    @Getter private final Color textArea_defaultBackground = super.getLabel_defaultBackground();
    @Getter private final Color textArea_defaultForeground = super.getLabel_defaultForeground();
    @Getter private final Color textArea_caretBackground = textArea_defaultBackground;
    @Getter private final Color textArea_caretForeground = textArea_defaultForeground;
}
