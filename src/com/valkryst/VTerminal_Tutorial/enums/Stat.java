package com.valkryst.VTerminal_Tutorial.enums;

import lombok.Getter;

public enum Stat {
    LEVEL("Level"),
    EXPERIENCE("Experience"),
    HEALTH("Health"),
    DAMAGE("Damage"),
    ARMOR("Armor"),
    AGILITY("Agility"),
    STAMINA("Stamina"),
    STRENGTH("Strength");

    /** The name. */
    @Getter private final String name;

    /**
     * Constructs a new Stat.
     *
     * @param name
     *          The name.
     */
    Stat(final String name) {
        this.name = name;
    }
}
