package com.valkryst.VTerminal_Tutorial.action;

import com.valkryst.VTerminal_Tutorial.Message;
import com.valkryst.VTerminal_Tutorial.entity.Entity;
import com.valkryst.VTerminal_Tutorial.gui.controller.GameController;

public class AttackMissAction extends Action {
    @Override
    public void perform(final GameController controller, final Entity self) {
        if (controller == null || self == null) {
            return;
        }

        super.perform(controller, self);
        controller.displayMessage(new Message().appendEntityName(self).append(" missed it's target."));
    }
}
