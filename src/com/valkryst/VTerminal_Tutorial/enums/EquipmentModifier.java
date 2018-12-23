package com.valkryst.VTerminal_Tutorial.enums;

import lombok.Getter;

public enum EquipmentModifier {
    NONE("", new Stat[0]),
    LEOPARD(" of the Leopard", new Stat[]{Stat.AGILITY, Stat.STAMINA}),
    TIGER(" of the Tiger", new Stat[]{Stat.AGILITY, Stat.STRENGTH}),
    RHINO(" of the Rhino", new Stat[]{Stat.STAMINA, Stat.STRENGTH});

    /** The text appended after an item's name, if it uses the modifier. */
    @Getter private final String suffix;
    /** The stats associated with the modifier. */
    @Getter private final Stat[] stats;

    /**
     *
     * @param suffix
     *          The text appended after an item's name, if it uses the modifier.
     *
     * @param stats
     *          The stats associated with the modifier.
     */
    EquipmentModifier(final String suffix, final Stat[] stats) {
        this.suffix = suffix;
        this.stats = stats;
    }
}
