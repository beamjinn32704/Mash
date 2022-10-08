/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.shell;

import java.awt.event.KeyEvent;

/**
 * This Compiler class contains functions that should be available for any
 * Compiler object used in the this Console library. Notably, it creates in the
 * processShiftCtrlC() function which force closes the current Program running.
 * It also supplies functionality that turns any command a user enters into a
 * Command object which is easier for Java objects to read and go through
 *
 * @author beamj
 * @see #processShiftCtrlC(java.awt.event.KeyEvent)
 * @see Command
 */
public class OverlayingCompiler implements Compiler {
    
    /**
     * Creates an OverlayingCompiler object.
     */
    public OverlayingCompiler() {
    
    }
    
    @Override
    public void processCommand(Command command) {
        
    }

    @Override
    public void processKeyStroke(KeyEvent evt) {
        
    }
    
    /**
     * This function handles what should happen when the user presses the letter C on their keyboard while holding down Ctrl.
     * @param evt the KeyEvent generated when the user types the letter C
     */
    public void processCtrlC(KeyEvent evt) {
        
    }
    
    /**
     * This function handles what should happen when the user presses the letter C on their keyboard while holding down Shift and Ctrl.
     * @param evt the KeyEvent generated when the user types the letter C
     */
    final void processShiftCtrlC(KeyEvent evt) {
        //Note to self on using "final" in functions: https://www.geeksforgeeks.org/different-ways-to-prevent-method-overriding-in-java/
    }

}
