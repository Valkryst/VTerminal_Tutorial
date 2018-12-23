package com.valkryst.VTerminal_Tutorial.enums;

import lombok.Getter;

public enum WeaponType {
    SWORD("Sword"),
    SHIELD("Shield");

    /** The name. */
    @Getter private final String name;

    /**
     * Constructs a new Stat.
     *
     * @param name
     *          The name.
     */
    WeaponType(final String name) {
        this.name = name;
    }
}
