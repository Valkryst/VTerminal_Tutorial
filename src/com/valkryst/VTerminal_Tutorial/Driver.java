package com.valkryst.VTerminal_Tutorial;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal_Tutorial.entity.Player;

import java.awt.*;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws IOException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);
        final Screen screen = new Screen(80, 40, font);
        screen.addCanvasToFrame();

        final Map map = new Map();
        screen.addComponent(map);

        final Point position = new Point(10, 10);
        final Dimension dimensions = new Dimension(10, 5);
        final Room room = new Room(position, dimensions);
        room.carve(map);

        final Player player = new Player(new Point(12, 12), "Gygax");
        map.addComponent(player);

        screen.draw();
    }
}