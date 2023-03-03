/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.shell.consolewindow;

import com.mindblown.shell.Command;
import com.mindblown.shell.MainCompiler;

/**
 * Objects of this class filter the text edits that the user wants to make to the console document, 
 * and handle the logistics of those edits. A fundamental piece of "philosophy" underlying the objects 
 * of this class is that it assumes all edits are being specified by the user. All other edits (which 
 * would be made by the system or programs) don't need to be handled and thus would not be sent to this class.
 * @author beamj
 */
public class UserInputHandler {
    
    private TextEditor textEditor;
    private ConsoleWindow consoleWindow;
    private MainCompiler compiler;

    public UserInputHandler(ConsoleWindow consoleWindow) {
        this.consoleWindow = consoleWindow;
        
        textEditor = consoleWindow.getTextEditor();
        
        compiler = new MainCompiler(consoleWindow);
    }
    
    
    /**
     * This is the starting index where the user can make changes to the
     * document (the user can make changes at any index in the document, as long
     * as that index >= this=startingIndex
     */
    private int startingIndex = 7;
    
        /**
     * Return the index of the first character in the console window that can be edited by the user.
     * @return the index of the first character that can be edited
     */
    public int getStartEditableIdx() {
        return startingIndex;
    }
    
    /**
     * Set the starting index. In the console text window, every piece of text
     * at an index >= the starting index can be edited/changed. All text before
     * the starting index can not be edited or changed
     *
     * @param startingIndex
     */
    public void setStartingIndex(int startingIndex) {
        this.startingIndex = startingIndex;
    }
    
    /**
     * Moves the starting index to the end. This means all the text currently in the console window will be 
     * uneditable by the user
     *
     * @see #setStartingIndex(int) 
     */
    public void moveStartingIndexToEnd(){
        
        setStartingIndex(consoleWindow.getTextLength());
    }
    
    /**
     * Returns whether the index given is in an area in the console text window
     * where text can be edited.
     *
     * @param index whether the index (corresponding to a location in the
     * console text window) is a place that can be edited.
     * @return whether text can be edited in the console text window at the
     * index given
     */
    public boolean inEditableZone(int index) {
        return index >= startingIndex;
    }
    
    /**
     * Returns whether the text cursor in the console window is in an area where
     * text can be edited
     *
     * @return whether text can be edited/changed in the location where the text
     * cursor is at
     */
    public boolean isCursorInEditableZone() {
        return inEditableZone(consoleWindow.getTextCursorIndex());
    }
    
    /**
     * Appends text to the console window
     * @param text the text to add
     */
    public void appendText(String text){
        addText(text, consoleWindow.getTextLength());
    }
    
    /**
     * Adds text to the console window
     * @param text the text to add
     * @param idx the index location in the console window text area to add the text to
     */
    public void addText(String text, int idx){
        int index = idx;
        
        if (!inEditableZone(index)) {
            //If text cursor is in the un-editable zone, bring it to the end
            textEditor.bringCaretToEnd();
            index = consoleWindow.getTextCursorIndex();
        }
        
        if (inEditableZone(index)) {
            //If the text cursor index is now in the editable zone, add the character the user typed into 
            //the console window
            
            //We call the text editor instead of allowing the system, 
            //so that the allow edits boolean doesn't accidentally stay true while no edits happen
            textEditor.addText(text, index);
        }
    }
    
    /**
     * Removes the text at indices from startInd to endInd (both inclusive) from the console window. If startInd==endInd, 
     * the only the character at index = startInd = endInd will be removed
     * @param startInd the index referring to the first character in the console window to be removed
     * @param endInd the index referring to the last character in the console window to be removed
     */
    public void removeText(int startInd, int endInd){
        assert(endInd >= startInd && startInd >= 0 && endInd < consoleWindow.getNumOfCharacters());
        
        int[] correctSelection = correctSelection(startInd, endInd);
        textEditor.removeText(correctSelection[0], correctSelection[1]);
    }
    
    /**
     * Removes a character from the console window.
     * @param ind the index corresponding to the character. If this was 0, for example, it would refer to the first 
     * character in the console window
     */
    public void removeCharacter(int ind){
        if (inEditableZone(ind)) {
            //If the character before the text cursor (aka the character that will be deleted) can be edited, remove 
            //the character
            textEditor.removeCharacter(ind);
        } else {
            //Otherwise, just bring the text cursor to the end of the document
            textEditor.bringCaretToEnd();
        }
    }
    
//    /**
//     * Takes an index corresponding to a position in the console window text (that the user 
//     * presumably wants to do some edit operations at) and returns an index that the user can edit at. 
//     * If the user can't edit at the index, the index returned will be the largest editable index. If the user 
//     * can edit at the index, then the index passed to the function will be returned.
//     * @param ind the index the user wants to edit at
//     * @return the modified index
//     */
//    private int correctInd(int ind){
//        if (ind >= startingIndex){
//            return ind;
//        } else {
//            return Math.max(startingIndex, )
//        }
//    }
    
    /**
     * Takes the indices corresponding to the beginning and end of the text in the console window that the user 
     * has selected, modifies them so that they don't refer to indices before the starting index (the first 
     * index editable by the user), and returns an array  containing them.
     * @param startInd the first index of the user-selected text
     * @param endInd the last index of the user-selected text
     * @return an array containing the new start index and end index. If array = A, then A[0] = new-start-index, and 
     * A[1] = new-end-index
     */
    private int[] correctSelection(int startInd, int endInd){
        startInd = Math.max(startingIndex, startInd);
        endInd = Math.max(startInd, endInd);
        endInd = Math.min(endInd, consoleWindow.getTextLength()-1);
        return new int[]{startInd, endInd};
    }
    
    /**
     * Follows the actions needed to be taken when the user presses the "Enter" key in the console window
     */
    public void enter(){
        appendText("\n\n");
        setStartingIndex(consoleWindow.getNumOfCharacters());
        compiler.processCommand(new Command("test"));
    }
}
