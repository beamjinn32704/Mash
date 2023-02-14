/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.shell.consolewindow;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 * Objects of this class are used to make changes to the text area in the
 * Console Window. This includes adding, deleting, and replacing text. This also
 * includes functionality like changing the location of the text cursor.
 *
 * @author beamj
 */
public class TextEditor {

    //This is the Console Window that objects of this class will be modifying
    private ConsoleWindow consoleWindow;

    /*These are components of the console */
    private JScrollPane consoleScrollPane; //the window pane that contains the console text pane and adds the scrollbar
    private JTextPane consoleTextPane; // the console window text pane
    private ConsoleStyledDocument consoleTextStyleDoc;

    /**
     * Instantiates a Console Window Text Editor.
     *
     * @param consoleWin the console window that objects of this class will
     * modify and change the text of
     */
    public TextEditor(ConsoleWindow consoleWin) {
        consoleWindow = consoleWin;

        consoleScrollPane = consoleWindow.getScrollPane();
        consoleTextPane = consoleWindow.getTextPane();
        consoleTextStyleDoc = consoleWindow.getConsoleWindowTextStyledDocument();
    }

    /**
     * Sets the text cursor in the console window to a certain index in the
     * console window. For example, if the index given is 0, the text cursor
     * will be moved to the beginning of all the text in the console window. If
     * the index given is 3, and the text in the console window is "hi there",
     * then the text cursor will be between the space and the 't' in 'there'.
     *
     * @param index
     */
    public void setTextCursorIndex(int index) {
        consoleTextPane.setCaretPosition(Math.min(index, consoleWindow.getNumOfCharacters()));
    }

    /**
     * Sets the text cursor to the end of the console window's text
     */
    public void bringCaretToEnd() {
        consoleTextPane.setCaretPosition(consoleWindow.getNumOfCharacters());
    }

    /**
     * Adds a specified string to the console window text, starting at the index
     * given
     *
     * @param text the string to add to the console window text
     * @param index the index in the console window text to start adding the
     * text to
     */
    public void addText(String text, int index) {
        consoleTextStyleDoc.allowEdits();
        try {
            consoleTextStyleDoc.insertString(index, text, null);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Appends a specified string to the text in the console window. The string
     * would be added to the end of the console window text.
     *
     * @param text the string to add to the console window text
     */
    public void addText(String text) {
        addText(text, consoleTextStyleDoc.getLength());
    }

    /**
     * Removes text from the console window text. All text is removed in between
     * and including the text at startIndex and endIndex
     *
     * @param startIndex the index in the console window text where text should
     * start getting removed (so if the startIndex was 1, the second character
     * in the console window text would be removed, but not the first
     * character).
     * @param endIndex the last index in the console window text where text
     * should be removed. To clarify, a character at this index will be removed,
     * but not the character after this index.
     */
    public void removeText(int startIndex, int endIndex) {
        assert(endIndex > startIndex && startIndex >= 0 && endIndex < consoleWindow.getNumOfCharacters());
        consoleTextStyleDoc.allowEdits();
        try {
            //Do the "endIndex - startIndex + 1" because this makes sure the endIndex is included
            consoleTextStyleDoc.remove(startIndex, endIndex - startIndex + 1);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Removes a character from the console window text at the index given.
     *
     * @param index the index to remove a character from
     */
    public void removeCharacter(int index) {
        consoleTextStyleDoc.allowEdits();
        try {
            //The "1" indicates that only one character is to be removed: the character at the index given
            consoleTextStyleDoc.remove(index, 1);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Replace the text currently selected by the user with the string passed to this function.
     * @param text the string to replace the currently selected text with
     */
    public void replaceSelectedText(String text){
        consoleTextStyleDoc.allowEdits();
        try {
            consoleTextStyleDoc.replace(consoleTextPane.getSelectionStart(), consoleTextPane.getSelectionEnd(), text, null);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

//    ..When adding / changing text and wanting to set the text''s color, and wanting a "default" color to use when you''re not 
//            given a color to use, use the console text pane''s foreground color
}
