/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mindblown.mash;

import com.mindblown.mash.ConsoleProgramRunner.ConsoleProgram;
import java.awt.Color;
import java.awt.event.KeyEvent;

/** An object of this class is used to interact with a ConsoleWindow object, whether through adding/replacing text displayed 
 on the ConsoleWindow, sending key events, etc.
 *
 * @author beamj
 */
public class ProgramRouter {
    private ConsoleWindow console;
    private ConsoleInteractor consoleInteractor;
    private ConsoleTextEditor consoleEditor;
    private boolean empty = true;
//    private ArrayList<KeyEvent> pressedKeys = new ArrayList<>();
    private ConsoleProgram currentRunningProgram = null;
    private int indexOfSafety;
    
    public ProgramRouter() {
        
    }
    
    public void setPrompt(ConsoleWindow c){
        console = c;
        consoleInteractor = c.getInteractor();
        consoleEditor = consoleInteractor.getConsoleEditor();
        indexOfSafety = -1;
    }
    
//    public KeyEvent processKey() {
//        if(pressedKeys.isEmpty()){
//            return null;
//        }
//        return pressedKeys.remove(0);
//    }
    
    public void clear() throws Exception{
        if(!isOpen()){
            throw new Exception("Program Router is not open!");
        }
        consoleEditor.clear();
        empty = false;
    }
    
    public void clearAndClose() throws Exception{
        clear();
        reset();
    }
    
    public void open(int start, ConsoleProgram program) throws Exception{
        if(isOpen()){
            throw new Exception("Program Router is already open!");
        }
        indexOfSafety = start;
        currentRunningProgram = program;
    }
    
    public void open(ConsoleProgram program) throws Exception{
        open(consoleEditor.getDocumentLength(), program);
    }
    
    /**
     * This will add text to the end of the of the console.
     * @param text The text that will be added.
     * @param textCol The color of the text that will be added.
     * @param programCalling
     * @see ConsoleTextEditor
     * @throws Exception
     */
    public void addProgramTextToEnd(ConsoleProgram programCalling, String text, Color textCol) throws Exception{
        addProgramText(programCalling, text, consoleEditor.getDocumentLength() - indexOfSafety, textCol);
    }
    
    /**
     *
     * @param text The text that will be added.
     * @param startInd This is where the text will be added.
     * The minimum value, 0, will represent the first index where program output can be outputted.
     * @param textCol The color of the text that will be added.
     * @param programCalling
     * @see ConsoleTextEditor
     * @throws Exception
     */
    public void addProgramText(ConsoleProgram programCalling, String text, int startInd, Color textCol) throws Exception{
        if(!acceptProgramRoute(programCalling)){
            return;
        }
        if(isOpen()){
            int indexToStartAdding = indexOfSafety + startInd;
            if(empty){
                empty = false;
                consoleEditor.addText("\n", indexToStartAdding, textCol);
                indexToStartAdding++;
            }
            consoleEditor.addText(text, indexToStartAdding, textCol);
        } else {
            System.out.println("Error! Program Router not open.");
            throw new Exception("Program Router Not Open!");
        }
    }
    
    /**
     * This replaces all the text that has outputted by the program with new text.
     * @param programCalling
     * @param text This is the text that will replace the text deleted.
     * @param textCol The color of the text that will be added.
     * @see ConsoleTextEditor
     * @throws Exception
     */
    public void setProgramText(ConsoleProgram programCalling, String text, Color textCol) throws Exception{
        replaceProgramText(programCalling, text, 0, consoleEditor.getDocumentLength() - indexOfSafety, textCol);
    }
    
    /**
     *
     * @param text This is the text that will replace the text deleted.
     * @param startInd This is the start index of the text that will be deleted.
     * The minimum value, 0, will represent the first index where program output can be outputted.
     * @param length This is the length of the text that will be deleted.
     * @param textCol The color of the text that will be added.
     * @param programCalling
     * @see ConsoleTextEditor
     * @throws Exception
     */
    public void replaceProgramText(ConsoleProgram programCalling, String text, int startInd, int length, Color textCol) throws Exception{
        if(!acceptProgramRoute(programCalling)){
            return;
        }
        if(isOpen()){
            int indexToStartAdding = indexOfSafety + startInd;
            if(empty){
                empty = false;
                consoleEditor.addText("\n", indexToStartAdding, textCol);
                indexToStartAdding++;
            }
            
            consoleEditor.replaceText(indexToStartAdding, length, text, textCol);
        } else {
            System.out.println("Error! Program Router not open.");
            throw new Exception("Program Router Not Open!");
        }
    }
    
    /**
     * This will add text to the end of the of the console.
     * @param programCalling
     * @param text The text that will be added.
     * @throws Exception
     */
    public void addProgramTextToEnd(ConsoleProgram programCalling, ColorText... text) throws Exception{
        addProgramText(programCalling, text, consoleEditor.getDocumentLength() - indexOfSafety);
    }
    
    /**
     *
     * @param programCalling
     * @param text The text that will be added.
     * @param startInd This is where the text will be added.
     * The minimum value, 0, will represent the first index where program output can be outputted.
     * @throws Exception
     */
    public void addProgramText(ConsoleProgram programCalling, ColorText[] text, int startInd) throws Exception{
        if(!acceptProgramRoute(programCalling)){
            return;
        }
        if(isOpen()){
            int indexToStartAdding = indexOfSafety + startInd;
            if(empty){
                empty = false;
                consoleEditor.addText("\n", indexToStartAdding, ConsoleTextEditor.blueColor);
                indexToStartAdding++;
            }
            consoleEditor.addText(text, indexToStartAdding);
        } else {
            System.out.println("Error! Program Router not open.");
            throw new Exception("Program Router Not Open!");
        }
    }
    
    /**
     * This replaces all the text that has outputted by the program with new text.
     * @param programCalling
     * @param text This is the text that will replace the text deleted.
     * @throws Exception
     */
    public void setProgramText(ConsoleProgram programCalling, ColorText... text) throws Exception{
        replaceProgramText(programCalling, text, indexOfSafety, consoleEditor.getDocumentLength() - indexOfSafety);
    }
    
    /**
     *
     * @param programCalling
     * @param text This is the text that will replace the text deleted.
     * @param startInd This is the start index of the text that will be deleted.
     * The minimum value, 0, will represent the first index where program output can be outputted.
     * @param length This is the length of the text that will be deleted.
     * @throws Exception
     */
    public void replaceProgramText(ConsoleProgram programCalling, ColorText[] text, int startInd, int length) throws Exception{
        if(!acceptProgramRoute(programCalling)){
            return;
        }
        if(isOpen()){
            if(empty){
                empty = false;
                consoleEditor.addText("\n", startInd, consoleEditor.getDefaultColor());
            }
            
            consoleEditor.replaceText(startInd, length, text);
        } else {
            System.out.println("Error! Program Router not open.");
            throw new Exception("Program Router Not Open!");
        }
    }
    
    private boolean acceptProgramRoute(ConsoleProgram programCalling) throws Exception{
        if(!isOpen()){
            System.out.println("Error! Program Router not open.");
            throw new Exception("Program Router Not Open!");
        }
        return programCalling == currentRunningProgram;
    }
    
    public void routeKeyEvent(KeyEvent evt) throws Exception{
        if(!isOpen()){
            throw new Exception("Program Router Not Open!");
        }
//        pressedKeys.add(evt);
        if(currentRunningProgram != null){
            currentRunningProgram.keyPressed(evt);
        }
    }
    
    public void close(){
        if(!isOpen()){
            return;
        }
        if(!empty){
            consoleEditor.addTextToEnd("\n\n", consoleEditor.getDefaultColor());
        } else {
            consoleEditor.addTextToEnd("\n", consoleEditor.getDefaultColor());
        }
        reset();
    }
    
    public void clearCommandHistory(){
        console.getInteractor().clearHist();
    }
    
    private boolean isOpen(){
        return indexOfSafety != -1;
    }
    
    private void reset(){
//        pressedKeys = new ArrayList<>();
        empty = true;
        console.getInteractor().newPrompt();
        currentRunningProgram = null;
        indexOfSafety = -1;
    }
}
