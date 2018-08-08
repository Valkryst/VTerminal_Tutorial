package com.valkryst.VTerminal_Tutorial;

import com.valkryst.VTerminal.Tile;
import lombok.Getter;
import lombok.NonNull;

import java.awt.*;

public class Message {
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
     *           The message.
     */
    public Message(final @NonNull String message) {
        this.message = prepareString(message);
    }


    /**
     * Prepares an array of tiles with the default message box colors.
     *
     * @param length
     *           The length of the array.
     *
     * @return
     *          The array.
     */
    public static Tile[] prepareString(final int length) {
        final Tile[] tiles = new Tile[length];
        final Color backgroundColor = new Color(0xFF8E999E, true);
        final Color foregroundColor = new Color(0xFF68D0FF, true);

        for (int i = 0 ; i < tiles.length ; i++) {
            tiles[i] = new Tile(' ');
            tiles[i].setBackgroundColor(backgroundColor);
            tiles[i].setForegroundColor(foregroundColor);
        }

        return tiles;
    }

    /**
     * Prepares a string with the default message box colors.
     *
     * @param text
     *           The text of the AsciiString,
     *
     * @return
     *          The tiles.
     */
    public static Tile[] prepareString(final @NonNull String text) {
        final Tile[] tiles = new Tile[text.length()];
        final Color backgroundColor = new Color(0xFF8E999E, true);
        final Color foregroundColor = new Color(0xFF68D0FF, true);

        for (int x = 0 ; x < text.length() ; x++) {
            tiles[x] = new Tile(text.charAt(x));
            tiles[x].setCharacter(text.charAt(x));
            tiles[x].setBackgroundColor(backgroundColor);
            tiles[x].setForegroundColor(foregroundColor);
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
    public Message append(final @NonNull String text) {
        append(prepareString(text));
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
    public Message append(final @NonNull Tile[] text) {
        final Tile[] newMessage = new Tile[message.length + text.length];

        for (int x = 0 ; x < message.length ; x++) {
            newMessage[x] = new Tile(' ');
            newMessage[x].copy(message[x]);
        }

        for (int x = message.length ; x < newMessage.length ; x++) {
            newMessage[x] = new Tile(' ');
            newMessage[x].copy(text[x - message.length]);
        }

        message = newMessage;

        return this;
    }
}
