package com.valkryst.VTerminal_Tutorial;

import com.valkryst.VTerminal.misc.ColorFunctions;
import lombok.Getter;

import java.awt.Color;

public enum Sprite {
    UNKNOWN('?', Color.BLACK, Color.RED),

    DARKNESS('█', Color.BLACK, Color.BLACK),
    DIRT('▒', new Color(0x452F09), new Color(0x372507)),
    GRASS('▒', new Color(0x3D4509), new Color(0x303707)),
    WALL('#', new Color(0x494949), new Color(0x3C3C3C)),

    PLAYER('@', new Color(0, 0, 0 ,0), Color.GREEN),
    ENEMY('E', new Color(0, 0, 0, 0), Color.RED),

    CONTAINER('C', new Color(0, 0, 0 ,0), Color.GREEN);

    /** The character. */
    @Getter private final char character;
    /** The background color. */
    @Getter private final Color backgroundColor;
    /** The foreground color. */
    @Getter private final Color foregroundColor;
    /** The dark background color. */
    @Getter private final Color darkBackgroundColor;
    /** The dark foreground color. */
    @Getter private final Color darkForegroundColor;

    /**
     * Constructs a new Sprite.
     *
     * @param character
     *        The character.
     *
     * @param backgroundColor
     *        The background color.
     *
     * @param foregroundColor
     *        The foreground color.
     */
    Sprite(final char character, final Color backgroundColor, final Color foregroundColor) {
        this.character = character;

        if (backgroundColor == null) {
            this.backgroundColor = Color.MAGENTA;
        } else {
            this.backgroundColor = backgroundColor;
        }

        if (foregroundColor == null) {
            this.foregroundColor = Color.MAGENTA;
        } else {
            this.foregroundColor = foregroundColor;
        }

        darkBackgroundColor = ColorFunctions.shade(this.backgroundColor, 0.5);
        darkForegroundColor = ColorFunctions.shade(this.foregroundColor, 0.5);
    }
}
