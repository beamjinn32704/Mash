/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.shell;

/**
 * This is the result of a command the user types into the ConsoleWindow. It is
 * a series of actions that run as a result of the command.
 *
 * @author beamj
 * @see ConsoleWindow
 * @see Compiler
 */
public class Program implements Runnable {

    protected final ConsoleWindow consoleWindow;
    protected final ConsoleWindowTextEditor textEditor;
    protected final ConsoleStyledDocument consoleTextStyledDocument;

    public Program(ConsoleWindow cw) {
        consoleWindow = cw;
        textEditor = cw.getTextEditor();
        consoleTextStyledDocument = cw.getConsoleWindowTextStyledDocument();
    }

    @Override
    public void run() {

    }

}
