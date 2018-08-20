package com.valkryst.VTerminal_Tutorial.statistic;

import com.valkryst.VTerminal.builder.LabelBuilder;
import com.valkryst.VTerminal.component.Label;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Stat {
    /** The name. */
    @Getter private final String name;

    /** The value. */
    @Getter private int value;

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
    public Stat(String name, final int value) {
        if (name.isEmpty()) {
            name = "Undefined";
        }

        this.name = name;
        this.value = value;
    }

    /**
     * Constructs and returns a label with the stat name and value.
     *
     * The label is formatted as follows.
     *
     * StatName: Value
     *
     * @return
     *          The label.
     */
    public Label getLabel() {
        final LabelBuilder labelBuilder = new LabelBuilder();
        labelBuilder.setText(name + ": " + value);

        final Label label = labelBuilder.build();
        label.setId(name);

        return label;
    }

    /**
     * Sets a new value.
     *
     * @param value
     *        The new value.
     */
    public void setValue(final int value) {
        this.value = value;

        for (final Runnable runnable : runnables) {
            runnable.run();
        }
    }
}
