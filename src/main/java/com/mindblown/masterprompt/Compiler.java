/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.masterprompt;
import java.awt.event.KeyEvent;

/** This is an interface whose objects interpret the actions the user makes in the Console Window
 *
 * @author beamj
 */
public interface Compiler {
    void compile(String command);
    void routeKeyEvent(KeyEvent evt);
}
