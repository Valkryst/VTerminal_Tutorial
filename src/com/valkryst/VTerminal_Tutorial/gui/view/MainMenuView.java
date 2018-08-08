package com.valkryst.VTerminal_Tutorial.gui.view;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.TileGrid;
import com.valkryst.VTerminal.builder.ButtonBuilder;
import com.valkryst.VTerminal.component.Button;
import com.valkryst.VTerminal.component.Layer;
import com.valkryst.VTerminal.printer.ImagePrinter;
import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class MainMenuView extends View {
    @Getter private Button button_new;
    @Getter private Button button_exit;

    /**
     * Constructs a new MainMenuView.
     *
     * @param screen
     *          The screen on which the view is displayed.
     */
    public MainMenuView(final Screen screen) {
        super(screen);
        initializeComponents();

        this.addComponent(loadLogo());
        this.addComponent(button_new);
        this.addComponent(button_exit);
    }

    /** Initializes the components. */
    private void initializeComponents() {
        final ButtonBuilder buttonBuilder = new ButtonBuilder();
        buttonBuilder.setText("New Game");
        buttonBuilder.setPosition(34, 16);
        button_new = buttonBuilder.build();

        buttonBuilder.setText("Exit");
        buttonBuilder.setPosition(36, 18);
        button_exit = buttonBuilder.build();
    }

    /**
     * Loads the logo from within the Jar file, then prints it to a Layer.
     *
     * @return
     *          The layer containing the logo.
     */
    private Layer loadLogo() {
        final Layer layer = new Layer(new Dimension(120, 16), new Point(0, 0));
        this.addComponent(layer);


        final Thread thread = Thread.currentThread();
        final ClassLoader classLoader = thread.getContextClassLoader();

        try (final InputStream is = classLoader.getResourceAsStream("Logo.png")) {
            final BufferedImage image = ImageIO.read(is);

            final ImagePrinter printer = new ImagePrinter(image);
            printer.printDetailed(layer.getTiles(), new Point(0, 0));
        } catch (final IOException | IllegalArgumentException e) {
            // todo Log the error message.

            // If we're unable to load the logo, then we'll set the layer's colors to something that stands out.
            final TileGrid layerGrid = layer.getTiles();

            for (int y = 0 ; y < layerGrid.getHeight() ; y++) {
                for (int x = 0 ; x < layerGrid.getWidth() ; x++) {
                    final Tile tile = layerGrid.getTileAt(x, y);
                    tile.setCharacter('?');
                    tile.setForegroundColor(Color.GREEN);
                    tile.setBackgroundColor(Color.MAGENTA);
                }
            }
        }

        return layer;
    }
}
