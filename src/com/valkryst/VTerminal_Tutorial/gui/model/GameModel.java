package com.valkryst.VTerminal_Tutorial.gui.model;

import com.valkryst.VTerminal_Tutorial.Map;
import lombok.Getter;

public class GameModel extends Model {
    @Getter private final Map map = new Map();
}
