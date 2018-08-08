package com.valkryst.VTerminal_Tutorial;

import com.valkryst.VTerminal.Tile;
import lombok.Getter;

import java.awt.*;
import java.util.Arrays;

public class Message {
    /** The default background color of all messages. */
    private final static Color DEFAULT_BACKGROUND_COLOR = new Color(0xFF8E999E);
    /** The default foreground color of all messages. */
    private final static Color DEFAULT_FOREGROUND_COLOR = new Color(0xFF68D0FF);

    /** The message. */
    @Getter private Tile[] message;

    /** Constructs a new message. */
    public Message() {
        this.message = prepareString(0);
    }

    /**
     * Constructs a new message.
     *
     * @param message
     *          The message.
     */
    public Message(final String message) {
        if (message == null) {
            this.message = prepareString(0);
        } else {
            this.message = prepareString(message);
        }
    }


    /**
     * Prepares an array of tiles with the default message box colors.
     *
     * @param length
     *          The length of the array.
     *
     * @return
     *          The array.
     */
    public static Tile[] prepareString(final int length) {
        final Tile[] tiles = new Tile[length];

        for (int i = 0 ; i < tiles.length ; i++) {
            tiles[i] = new Tile(' ');
            tiles[i].setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
            tiles[i].setForegroundColor(DEFAULT_FOREGROUND_COLOR);
        }

        return tiles;
    }

    /**
     * Prepares a string with the default message box colors.
     *
     * @param text
     *          The text of the AsciiString,
     *
     * @return
     *          The tiles.
     */
    public static Tile[] prepareString(final String text) {
        if (text == null) {
            return new Tile[0];
        }

        final Tile[] tiles = new Tile[text.length()];

        for (int i = 0 ; i < text.length() ; i++) {
            tiles[i] = new Tile(text.charAt(i));
            tiles[i].setCharacter(text.charAt(i));
            tiles[i].setBackgroundColor(DEFAULT_BACKGROUND_COLOR);
            tiles[i].setForegroundColor(DEFAULT_FOREGROUND_COLOR);
        }

        return tiles;
    }

    /**
     * Appends a string to the message.
     *
     * @param text
     *          The text.
     *
     * @return
     *          This.
     */
    public Message append(final String text) {
        if (text != null) {
            append(prepareString(text));
        }

        return this;
    }

    /**
     * Appends an array of tiles to the message.
     *
     * @param text
     *           The text.
     *
     * @return
     *          This.
     */
    public Message append(final Tile[] text) {
        if (text == null) {
            return this;
        }

        final Tile[] newMessage = new Tile[message.length + text.length];

        for (int i = 0 ; i < message.length ; i++) {
            newMessage[i] = new Tile(' ');
            newMessage[i].copy(message[i]);
        }

        for (int i = message.length ; i < newMessage.length ; i++) {
            newMessage[i] = new Tile(' ');
            newMessage[i].copy(text[i - message.length]);
        }

        message = newMessage;

        return this;
    }
}
