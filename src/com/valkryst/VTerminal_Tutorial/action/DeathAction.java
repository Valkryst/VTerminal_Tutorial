package com.valkryst.VTerminal_Tutorial.action;

import com.valkryst.VTerminal_Tutorial.Map;
import com.valkryst.VTerminal_Tutorial.Message;
import com.valkryst.VTerminal_Tutorial.entity.Entity;
import com.valkryst.VTerminal_Tutorial.gui.controller.GameController;

public class DeathAction extends Action {
    @Override
    public void perform(final GameController controller, final Entity self) {
        super.perform(controller, self);

        if (controller == null || self == null) {
            return;
        }

        final Map map = controller.getModel().getMap();

        if (map != null) {
            map.getEntities().remove(self);
            controller.getView().removeComponent(self);
            controller.displayMessage(new Message().appendEntityName(self).append(" has died."));
        }
    }
}
