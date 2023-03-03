/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.shell;

import com.mindblown.shell.consolewindow.ConsoleWindow;

/**
 *
 * @author beamj
 */
public class TestProgram extends Program {

    public TestProgram(ConsoleWindow cw) {
        super(cw);
    }

    @Override
    public void run() {
        textEditor.appendText("Test Is a Go!\n\nPrompt>");
        consoleWindow.getUiHandler().moveStartingIndexToEnd();
    }

}
