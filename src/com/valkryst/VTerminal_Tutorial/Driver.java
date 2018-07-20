package com.valkryst.VTerminal_Tutorial;

import com.valkryst.VTerminal.Screen;

import java.awt.*;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws IOException {
        final Screen screen = new Screen(80, 40);
        screen.addCanvasToFrame();

        final Map map = new Map();
        screen.addComponent(map);

        final Point position = new Point(10, 10);
        final Dimension dimensions = new Dimension(10, 5);
        final Room room = new Room(position, dimensions);
        room.carve(map);

        screen.draw();
    }
}