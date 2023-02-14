/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.shell.consolewindow;

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

    public UserInputHandler(ConsoleWindow consoleWindow) {
        this.consoleWindow = consoleWindow;
        
        textEditor = consoleWindow.getTextEditor();
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
}
