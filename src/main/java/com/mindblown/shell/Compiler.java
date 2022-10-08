/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.shell;

import java.awt.event.KeyEvent;

/**
 * Objects of this interface process commands that the user types into the
 * ConsoleWindow
 *
 * @author beamj
 * @see ConsoleWindow
 * @see ConsoleWindowInputInterpreter
 */
public interface Compiler {

    /**
     * This function reads the command a user gives (through the ConsoleWindow)
     * and enacts the functionality corresponding to that command
     *
     * @param command the command
     * @see ConsoleWindow
     * @see Command
     */
    public void processCommand(Command command);

    /**
     * This function reads a key stroke that the user types into the
     * ConsoleWindow and does the corresponding action associated with that
     * keystroke. This function generally won't handle things like adding the
     * letter A to the ConsoleWindow text when the user types "A" -- that's
     * generally reserved for the ConsoleWindowInputInterpreter -- but will
     * instead handle key strokes containing modifiers such as "Ctrl"; for
     * example, it can take a Ctrl-Shift-X key and decide that when the user
     * uses the Ctrl-Shift-X command, that the program will exit.
     *
     * @param evt the keystroke the user typed
     * @see ConsoleWindow
     * @see ConsoleWindowInputInterpreter
     * @see KeyEvent
     */
    public void processKeyStroke(KeyEvent evt);
}
