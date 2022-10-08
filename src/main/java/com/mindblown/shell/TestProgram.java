/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.shell;

import com.mindblown.debuggerutil.DebugPrinter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;

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
        
        textEditor.addText("What text do you want to tell me? ");
        int endOfPromptIndex = consoleWindow.getNumOfCharacters();
        
        DebugPrinter.printlnCustomNorm("EndOfPromptIndex:", endOfPromptIndex);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                DebugPrinter.println("Iteration");
                String response;
                try {
                    response = consoleTextStyledDocument.getText(endOfPromptIndex, consoleWindow.getNumOfCharacters() - endOfPromptIndex);
                    DebugPrinter.printlnCustomNorm("Response:", response);
                    if (!response.isEmpty()) {
                        DebugPrinter.println("User Response: " + response);
                        textEditor.addText("\n\nResponse Received! You typed \'" + response + "\'!\n\nProgram Starts Now :)");
                        textEditor.bringCaretToEnd();
                        timer.cancel();
                    }
                } catch (BadLocationException ex) {
                    Logger.getLogger(ConsoleWindowInputInterpreter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }, 0, 500);
    }
}