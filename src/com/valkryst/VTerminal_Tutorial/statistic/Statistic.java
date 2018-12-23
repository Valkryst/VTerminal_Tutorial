package com.valkryst.VTerminal_Tutorial.statistic;

import com.valkryst.VTerminal.builder.LabelBuilder;
import com.valkryst.VTerminal.component.Label;
import com.valkryst.VTerminal_Tutorial.enums.Stat;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Statistic {
    /** The type. */
    @Getter private final Stat type;

    /** The value. */
    @Getter private int value;

    /** The runnable functions to run whenever the value is changed. */
    @Getter private final List<Runnable> runnables = new ArrayList<>();

    /**
     * Constructs a new Stat.
     *
     * @param type
     *          The type.
     *
     * @param value
     *          The value.
     */
    public Statistic(final Stat type, final int value) {
        this.type = type;
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
        labelBuilder.setText(type.getName() + ": " + value);

        final Label label = labelBuilder.build();
        label.setId(type.getName());

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
