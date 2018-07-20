package com.valkryst.VTerminal_Tutorial;

import com.valkryst.VTerminal.Screen;

import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws IOException {
        final Screen screen = new Screen(80, 40);
        screen.addCanvasToFrame();

        final Map map = new Map();
        screen.addComponent(map);

        screen.draw();
    }
}