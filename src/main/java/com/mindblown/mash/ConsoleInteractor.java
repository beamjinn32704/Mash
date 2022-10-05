/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mindblown.mash;

import com.mindblown.util.ArrayListUtil;
import com.mindblown.util.ArrayUtil;
import com.mindblown.util.FileUtil;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JTextPane;

/** Objects of this class are basically the connection between the user's "interactions" with the Console Window (so 
 * typing text, moving mouse, etc.)
 *
 * @author beamj
 */
public class ConsoleInteractor {
    private ConsoleTextEditor consoleEditor;
    private ConsoleWindow console;
    private JTextPane consolePane;
    private Compiler compiler;
    private int indexOfSafety = 0;
    private ColorText[] colorPrompt;
    private ArrayList<String> previousCommands;
    public static File commandDataFile = new File("cdfile.consoledata");
    private final static int maxCommandsHold = 50;
    private int currentCommandInd;
    private boolean runningProgram = false;
    
    private ControllerSettings origControllerSettings = null;
    
    public ConsoleInteractor(ConsoleTextEditor consoleEditor, Compiler compiler, ColorText[] colorPrompt) {
        this.console = ConsoleWindow.staticConsole;
        this.consoleEditor = consoleEditor;
        this.consolePane = console.getConsolePane();
        this.compiler = compiler;
        this.colorPrompt = colorPrompt;
        
        String commandDataFileText = FileUtil.getText(commandDataFile);
        commandDataFileText = commandDataFileText.trim();
        if(!commandDataFileText.isBlank()){
            previousCommands = ArrayUtil.toList(commandDataFileText.split("\n"));
        } else {
            previousCommands = new ArrayList<>();
        }
        currentCommandInd = previousCommands.size();
    }
    
    public void printColorPrompt(){
        consoleEditor.addTextToEnd(colorPrompt);
    }
    
    public void setIndexOfSafetyToEnd(){
        indexOfSafety = consoleEditor.getDocumentLength();
    }
    
    public ConsoleTextEditor getConsoleEditor(){
        return consoleEditor;
    }
    
    public void clearHist(){
        previousCommands = new ArrayList<>();
        try(PrintWriter writer = new PrintWriter(commandDataFile)){
            writer.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        currentCommandInd = 0;
    }
    
    public int getDocumentLength(){
        return consoleEditor.getDocumentLength();
    }
    
    public void keyPressed(KeyEvent evt){
        try {
            //DELETE
            int keyCode = evt.getKeyCode();
            String textSelected = consolePane.getSelectedText();
            boolean textIsSelected = textSelected != null;
            int selectionStart = consolePane.getSelectionStart();
            int selectionEnd = consolePane.getSelectionEnd();
            //This is the only way you can accurately get the text. Do not change the core of this (get all text from style document).
            String origText = consoleEditor.getPromptStyleDoc().getText(0, consoleEditor.getDocumentLength());
            char keyChar = evt.getKeyChar();
            if(runningProgram){
                compiler.routeKeyEvent(evt);
                return;
            }
            if(keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_DELETE){
                backspace(textIsSelected, selectionStart, selectionEnd, evt, origText);
            } else if(keyCode == KeyEvent.VK_ENTER){
                evt.consume();
                String commandText = origText.substring(indexOfSafety).trim();
                indexOfSafety = consoleEditor.getDocumentLength();
                runningProgram = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        compiler.compile(commandText);
                        runningProgram = false;
                    }
                }).start();
                if(!commandText.isEmpty()){
                    if(previousCommands.isEmpty()){
                        if(previousCommands.size() >= maxCommandsHold){
                            previousCommands.remove(0);
                        }
                        previousCommands.add(commandText);
                    } else {
                        String prev = previousCommands.get(previousCommands.size() - 1);
                        if(!prev.equals(commandText)){
                            if(previousCommands.size() >= maxCommandsHold){
                                previousCommands.remove(0);
                            }
                            previousCommands.add(commandText);
                        }
                    }
                }
                try(PrintWriter writer = new PrintWriter(commandDataFile)){
                    for(String command : previousCommands){
                        writer.println(command);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                currentCommandInd = previousCommands.size();
            } else if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN){
                if(evt.isControlDown()){
                    if(keyCode == KeyEvent.VK_UP){
                        if(currentCommandInd > 0){
                            currentCommandInd--;
                            consoleEditor.replaceText(indexOfSafety, consoleEditor.getDocumentLength() - indexOfSafety,
                                    previousCommands.get(currentCommandInd), null);
                        }
                    } else {
                        consoleEditor.deleteFrom(indexOfSafety);
                        if(currentCommandInd >= previousCommands.size() - 1){
                            currentCommandInd = previousCommands.size();
                        } else {
                            currentCommandInd++;
                            consoleEditor.addTextToEnd(previousCommands.get(currentCommandInd), null);
                        }
                    }
                }
            } else if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT){
                
            } else if(keyCode == KeyEvent.VK_C && evt.isControlDown()) {
                
            } else if(keyCode == KeyEvent.VK_V && evt.isControlDown()){
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                if(clipboard != null){
                    Object copied;
                    try {
                        copied = clipboard.getData(DataFlavor.stringFlavor);
                        if(copied != null){
                            String copiedText = ((String)copied);
                            consoleEditor.replaceText(selectionStart, selectionEnd - selectionStart, copiedText, null);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else if(keyCode == KeyEvent.VK_HOME || keyCode == KeyEvent.VK_END){
                
            } else if(keyCode == KeyEvent.VK_A && evt.isControlDown()){
                
            } else {
                evt.consume();
                if(Character.isAlphabetic(keyChar) || Character.isDigit(keyChar)
                        || Character.isWhitespace(keyChar) || Character.isDefined(keyChar)){
                    if(selectionStart >= indexOfSafety){
                        consoleEditor.addText(keyChar + "", selectionStart, null);
                    } else {
                        consolePane.setSelectionStart(consoleEditor.getDocumentLength());
                        consolePane.setSelectionEnd(consoleEditor.getDocumentLength());
                        consoleEditor.addTextToEnd(keyChar + "", null);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void backspace(boolean textIsSelected, int selectionStart, int selectionEnd, KeyEvent evt, String origText){
        if(selectionStart <= indexOfSafety){
            if(selectionEnd > indexOfSafety){
                selectionStart = indexOfSafety;
                consolePane.setSelectionStart(selectionStart);
            } else {
                consolePane.setSelectionStart(consoleEditor.getDocumentLength());
                consolePane.setSelectionEnd(consoleEditor.getDocumentLength());
                evt.consume();
                return;
            }
        }
        
        if(evt.isControlDown() && !textIsSelected){
            ctrlBackSpace(textIsSelected, selectionStart, evt, origText);
        } else {
            if(textIsSelected){
                consoleEditor.removeText(selectionStart, selectionEnd - selectionStart);
            } else {
                consoleEditor.removeText(selectionStart - 1, 1);
            }
        }
    }
    
    private void ctrlBackSpace(boolean textIsSelected, int selectionStart, KeyEvent evt, String origText){
        
        Acceptor alpha = new Acceptor() {
            @Override
            public boolean accept(char c) {
                return Character.isAlphabetic(c);
            }
        };
        Acceptor digit = new Acceptor() {
            @Override
            public boolean accept(char c) {
                return Character.isDigit(c);
            }
        };
        Acceptor whitespace = new Acceptor() {
            @Override
            public boolean accept(char c) {
                return Character.isWhitespace(c);
            }
        };
        Acceptor others = new Acceptor() {
            @Override
            public boolean accept(char c) {
                return !Character.isAlphabetic(c) && !Character.isDigit(c) && !Character.isWhitespace(c);
            }
        };
        
        if(textIsSelected){
            evt.consume();
            return;
        }
        
        if(selectionStart == -1){
            System.out.println("SELECTION START == -1!");
        }
        
        String workingWith = origText.substring(indexOfSafety, selectionStart);
        evt.consume();
        String leftOver;
        if(workingWith.length() > 0){
            char lastChar = workingWith.charAt(workingWith.length() - 1);
            if(Character.isAlphabetic(lastChar)){
                leftOver = removeEndAceppted(workingWith, alpha);
            } else if(Character.isDigit(lastChar)){
                leftOver = removeEndAceppted(workingWith, digit);
            } else if(Character.isWhitespace(lastChar)){
                leftOver = removeEndAceppted(workingWith, whitespace);
            } else {
                leftOver = removeEndAceppted(workingWith, others);
            }
            String toRemove = workingWith.substring(0, (workingWith.length() - leftOver.length()));
            consoleEditor.removeText(selectionStart - toRemove.length(), toRemove.length());
        }
    }
    
    private String removeEndAceppted(String workingWith, Acceptor a){
        int lastInd = -1;
        for(int i = workingWith.length() - 1; i >= 0; i--){
            char c = workingWith.charAt(i);
            if(!a.accept(c)){
                lastInd = i+1;
                i = -1;
            }
        }
        if(lastInd == -1){
            return "";
        } else {
            return workingWith.substring(0, lastInd);
        }
    }
    
    public void formatCaretAndIndexOfSafety(){
        console.bringCaretToFront();
        setIndexOfSafetyToEnd();
    }
    
    public void newPrompt(){
        printColorPrompt();
        formatCaretAndIndexOfSafety();
    }
    
    public void newController(Compiler compilerToUse, ColorText[] prompter){
        if(origControllerSettings != null){
            System.out.println("ConsoleInteractor::newController() OrigControllerSettings is already used!");
            return;
        }
        
        origControllerSettings = new ControllerSettings(compiler, colorPrompt, indexOfSafety);
        
        compiler = compilerToUse;
        colorPrompt = prompter;
        
        setIndexOfSafetyToEnd();
    }
    
    public void restoreOrigController(){
        if(origControllerSettings == null){
            System.out.println("ConsoleInteractor::newController() OrigControllerSettings is null!");
            return;
        }
        
        compiler = origControllerSettings.controllerCompiler;
        colorPrompt = origControllerSettings.controllerPrompter;
        indexOfSafety = origControllerSettings.controllerIndexOfSafety;
        
        origControllerSettings = null;
    }
    
    private class ControllerSettings {
        private Compiler controllerCompiler;
        private ColorText[] controllerPrompter;
        private int controllerIndexOfSafety;

        public ControllerSettings(Compiler controllerCompiler, ColorText[] controllerPrompter, int controllerIndexOfSafety) {
            this.controllerCompiler = controllerCompiler;
            this.controllerPrompter = controllerPrompter;
            this.controllerIndexOfSafety = controllerIndexOfSafety;
        }
    }
    
}