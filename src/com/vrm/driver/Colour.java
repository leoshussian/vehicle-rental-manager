package com.vrm.driver;

/**
 * List of colours used with Strings.
 */
public enum Colour {
    //Colour end string, color reset
    RESET("\033[0m"),

    // High Contrast
    W_B("\033[4;30m" + "\033[47m"),

    // Regular Colors
    BLACK("\033[0;30m"),    // BLACK
    RED("\033[0;31m"),      // RED
    GREEN("\033[0;32m"),    // GREEN

    WHITE_BACKGROUND("\033[47m"),
    RED_BACKGROUND("\033[41m"),     // RED
    GREEN_BACKGROUND("\033[42m");   // GREEN

    private final String code;

    Colour(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
