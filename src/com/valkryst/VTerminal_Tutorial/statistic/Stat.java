package com.valkryst.VTerminal_Tutorial.statistic;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Stat {
    /** The name. */
    @Getter private final String name;

    /** The value. */
    @Getter private short value;

    /** The runnable functions to run whenever the value is changed. */
    @Getter private final List<Runnable> runnables = new ArrayList<>();

    /**
     * Constructs a new Stat.
     *
     * @param name
     *        The name.
     *
     * @param value
     *        The value.
     */
    public Stat(String name, final short value) {
        if (name.isEmpty()) {
            name = "Undefined";
        }

        this.name = name;
        this.value = value;
    }

    /**
     * Sets a new value.
     *
     * @param value
     *        The new value.
     */
    public void setValue(final short value) {
        this.value = value;

        for (final Runnable runnable : runnables) {
            runnable.run();
        }
    }
}
