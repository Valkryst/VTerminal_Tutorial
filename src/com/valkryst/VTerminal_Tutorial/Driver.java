package com.valkryst.VTerminal_Tutorial;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal_Tutorial.gui.controller.MainMenuController;

import java.io.IOException;

public class Driver {
    public static void main(String[] args) throws IOException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);
        final Screen screen = new Screen(80, 40, font);
        screen.addCanvasToFrame();

        final MainMenuController controller = new MainMenuController(screen);
        controller.addToScreen(screen);
    }
}