package com.valkryst.VTerminal_Tutorial.enums;

import com.valkryst.VTerminal.palette.ColorPalette;
import com.valkryst.VTerminal_Tutorial.gui.palette.rarity.*;
import lombok.Getter;

public enum Rarity {
    COMMON(new CommonColorPalette(), 1),
    UNCOMMON(new UncommonColorPalette(), 2),
    RARE(new RareColorPalette(), 3),
    EPIC(new EpicColorPalette(), 4),
    LEGENDARY(new LegendaryColorPalette(), 8),
    ARTIFACT(new ArtifactColorPalette(), 12);

    /** The color palette associated with the rarity. */
    @Getter private final ColorPalette colorPalette;
    /** The multiplier value associated with the rarity. */
    @Getter private final double multiplier;

    /**
     * Constructs a new Rarity.
     *
     * @param colorPalette
     *          The color palette associated with the rarity.
     *
     * @param multiplier
     *          The multiplier value associated with the rarity.
     */
    Rarity(final ColorPalette colorPalette, final double multiplier) {
        this.colorPalette = colorPalette;
        this.multiplier = multiplier;
    }
}
