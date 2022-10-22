/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.shell;

import com.mindblown.shell.consolewindow.ConsoleWindow;

/**
 * This is the compiler used by the ConsoleWindow when the ConsoleWindow starts.
 * It contains the normal, "default" commands like cd, exit, open, etc.
 *
 * @author beamj
 * @see ConsoleWindow
 */
public class MainCompiler extends OverlayingCompiler {

    private ConsoleWindow consoleWindow;
    

    /**
     * Instantiates a MainCompiler object that interacts with a ConsoleWindow
     *
     * @param consoleWind the ConsoleWindow object this MainCompiler interacts
     * with
     */
    public MainCompiler(ConsoleWindow consoleWind) {
        super();
        consoleWindow = consoleWind;
    }

    @Override
    public void processCommand(Command command) {
        
    }
}