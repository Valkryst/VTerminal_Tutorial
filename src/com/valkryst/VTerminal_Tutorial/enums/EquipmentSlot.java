package com.valkryst.VTerminal_Tutorial.enums;

import lombok.Getter;

public enum EquipmentSlot {
    HEAD("Helm"),
    NECK("Necklace"),
    CHEST("Armor"),
    WRISTS("Wristbands"),
    HANDS("Gloves"),
    FEET("Boots"),
    MAIN_HAND("Main Hand"),
    OFF_HAND("Off Hand");

    /** The name. */
    @Getter private final String name;

    /**
     * Constructs a new EquipmentSlot.
     *
     * @param name
     *          The name.
     */
    EquipmentSlot(final String name) {
        this.name = name;
    }
}
