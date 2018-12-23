package com.valkryst.VTerminal_Tutorial.statistic;

import com.valkryst.VTerminal.builder.LabelBuilder;
import com.valkryst.VTerminal.component.Label;
import com.valkryst.VTerminal_Tutorial.enums.Stat;
import lombok.Getter;

public class BoundStatistic extends Statistic {
    /** The minimum value. */
    @Getter private int minValue;

    /** The maximum value. */
    @Getter private int maxValue;

    /**
     * Constructs a new BoundStatistic.
     *
     * If the max value is less than the min value, then it is set to the min value.
     *
     * @param type
     *          The type.
     *
     * @param value
     *          The value.
     *
     * @param minValue
     *          The minimum value.
     *
     * @param maxValue
     *          The maximum value.
     */
    public BoundStatistic(final Stat type, final int value, final int minValue, int maxValue) {
        super(type, value);

        if (maxValue < minValue) {
            maxValue = minValue;
        }

        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    /**
     * Constructs a new BoundStatistic.
     *
     * If the max value is less than the min value, then it is set to the min value.
     *
     * The value is automatically set to the maximum value.
     *
     * @param type
     *          The type.
     *
     * @param minValue
     *          The minimum value.
     *
     * @param maxValue
     *          The maximum value.
     */
    public BoundStatistic(final Stat type, final int minValue, final int maxValue) {
        this(type, maxValue, minValue, maxValue);
    }

    /**
     * Constructs and returns a label with the stat name, value, and max value.
     *
     * The label is formatted as follows.
     *
     * StatName: Value/MaxValue
     *
     * @return
     *          The label.
     */
    public Label getBoundLabel() {
        final LabelBuilder labelBuilder = new LabelBuilder();
        labelBuilder.setText(super.getType().getName() + ": " + super.getValue() + "/" + maxValue);

        final Label label = labelBuilder.build();
        label.setId(super.getType().getName());

        return label;
    }

    @Override
    public void setValue(final int value) {
        if (super.getValue() > maxValue) {
            super.setValue(maxValue);
            return;
        }

        if (super.getValue() < minValue) {
            super.setValue(minValue);
            return;
        }

        super.setValue(value);
    }

    /**
     * Sets a new minimum value.
     *
     * If the new min value is greater than the max value, then no change occurs.
     *
     * If the value is less than the new min value, then the value is set to the min value.
     *
     * @param minValue
     *          The new min value.
     */
    public void setMinValue(final int minValue) {
       if (minValue < maxValue) {
           this.minValue = minValue;

           if (super.getValue() < minValue) {
               super.setValue(minValue);
           }
       }
    }

    /**
     * Sets a new maximum value.
     *
     * If the new max value is less than the min value, then no change occurs.
     *
     * If the value is greater than the new max value, then the value is set to the max value.
     *
     * @param maxValue
     *          The new max value.
     */
    public void setMaxValue(final int maxValue) {
        if (maxValue > minValue) {
            this.maxValue = maxValue;

            if (super.getValue() > maxValue) {
                super.setValue(maxValue);
            }
        }
    }
}
