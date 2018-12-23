package com.valkryst.VTerminal_Tutorial.enums;

import lombok.Getter;

public enum Material {
    COPPER("Copper", 1),
    BRONZE("Bronze", 2),
    IRON("Iron", 3),
    STEEL("Steel", 4),
    MITHRIL("Mithril", 8),
    ADAMANTINE("Adamantine", 12);

    /** The name. */
    @Getter private final String name;

    /** The multiplier value associated with the material. */
    @Getter private final double multiplier;

    /**
     * Constructs a new Material.
     *
     * @param name
     *          The name.
     *
     * @param multiplier
     *          The multiplier value associated with the material.
     */
    Material(final String name, final double multiplier) {
        this.name = name;
        this.multiplier = multiplier;
    }
}
