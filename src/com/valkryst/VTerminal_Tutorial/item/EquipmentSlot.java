package com.valkryst.VTerminal_Tutorial.item;

public enum EquipmentSlot {
    HEAD,
    NECK,
    BACK,
    SHOULDERS,
    CHEST,
    WRISTS,
    HANDS,
    WAIST,
    LEGS,
    FEET,
    MAIN_HAND,
    OFF_HAND,
    UNKNOWN;

    /**
     * Retrieves the name of the equipment slot.
     *
     * The first letter is capitalized and all underscores are converted to spaces.
     *
     * @return
     *         The name of the slot.
     */
    public String getName() {
        String name = this.name();
        name = name.toLowerCase();
        name = name.replace('_', ' ');
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }
}
