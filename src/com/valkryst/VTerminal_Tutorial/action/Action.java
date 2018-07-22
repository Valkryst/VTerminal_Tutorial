package com.valkryst.VTerminal_Tutorial.action;

import com.valkryst.VTerminal_Tutorial.Map;
import com.valkryst.VTerminal_Tutorial.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Action {
    /** The runnable functions to run when the action is performed. */
    private final List<Runnable> runnables = new ArrayList<>();

    /**
     * Performs the action and runs all of the runnable functions.
     *
     * Does nothing if the map or entity are null.
     *
     * @param map
     *          The map.
     *
     * @param entity
     *          The entity performing the action.
     */
    public void perform(final Map map, final Entity entity) {
        if (map == null || entity == null) {
            return;
        }

        runnables.forEach(Runnable::run);
    }

    /**
     * Adds a runnable function to the action.
     *
     * @param runnable
     *          The runnable function.
     */
    public void addRunnable(final Runnable runnable) {
        if (runnable == null) {
            return;
        }

        runnables.add(runnable);
    }

    /**
     * Removes a runnable function from the action.
     *
     * @param runnable
     *          The runnable function.
     */
    public void removeRunnable(final Runnable runnable) {
        if (runnable == null) {
            return;
        }

        runnables.remove(runnable);
    }

    /** Removes all runnable functions from the action. */
    public void removeAllRunnables() {
        runnables.clear();
    }
}
