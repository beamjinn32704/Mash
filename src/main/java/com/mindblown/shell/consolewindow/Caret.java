/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.shell.consolewindow;

import javax.swing.text.DefaultCaret;

/**
 * This is the caret (text blinker) used in the Console Window. Any special changes to it so that it fits well with 
 * the style/format of the Console Window are automatically applied here.
 * @author beamj
 */
public class Caret extends DefaultCaret {

    public Caret() {
        setBlinkRate(500);
    }
}
