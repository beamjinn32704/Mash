/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.shell.consolewindow;

import com.mindblown.shell.Command;
import com.mindblown.shell.MainCompiler;
import com.mindblown.util.StringUtil.StringEvaluator;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 * Objects of this class are used to interpret any key strokes the user types.
 * These key strokes include backspaces, up and down arrows, etc. An example of
 * using this object would be telling the object that a backspace was done, and
 * this object would return saying that a character needs to be deleted.
 *
 * @author beamj
 */
public class InputInterpreter {

    //This is the Console Window that objects of this class will be modifying
    private ConsoleWindow consoleWindow;

    /* These are components of the console */
    private JTextPane consoleTextPane; // the console window text pane

    // This contains all the text and the styles of the text (text color, bold, italic, etc.) in the console text pane
    private ConsoleStyledDocument consoleTextStyledDocument;

    private UserInputHandler uiHandler;

    /**
     * Instantiates a Console Window Text Editor.
     *
     * @param consoleWin the console window that objects of this class will
     * modify and change the text of
     * @param uiHandler the input handler that will decide which of the user's
     * "inputs" will take effect
     */
    public InputInterpreter(ConsoleWindow consoleWin, UserInputHandler uiHandler) {
        consoleWindow = consoleWin;
        
        consoleTextPane = consoleWindow.getTextPane();
        consoleTextStyledDocument = consoleWindow.getConsoleWindowTextStyledDocument();

        this.uiHandler = uiHandler;
    }

    /**
     * This function decides how to interpret a key event that the user types.
     * If necessary, it will modify the console window's text.
     *
     * @param evt They key event describing the key the user pressed
     */
    public void keyPressed(KeyEvent evt) {
        int keyCode = evt.getKeyCode();
        boolean ctrl = evt.isControlDown();
        boolean alt = evt.isAltDown();
        boolean shift = evt.isShiftDown();
        boolean altGraph = evt.isAltGraphDown();
        try {
            String styleText = consoleTextStyledDocument.getText(0, consoleTextStyledDocument.getLength());
            String textPaneText = consoleWindow.getTextPane().getText();
            System.out.println("\"" + styleText + "\"");
            System.out.println(styleText.length() + ", " + consoleTextStyledDocument.getLength());
            System.out.println("\"" + textPaneText + "\"");
            System.out.println(textPaneText.length());
        } catch (BadLocationException ex) {
            Logger.getLogger(InputInterpreter.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Find out what the user pressed and map it to the right function to take care of it
        /**
         * Note to self: in ConsoleStyledDocument, this is how edits/key-events
         * effect it (naturally): 1. Whenever text is added (through a key
         * press, Ctrl-V, etc.), the functions called are replace() nest
         * insertString() nest insertUpdate(). 2. Whenever text is removed
         * (through backspace, Ctrl-Backspace, select and then delete, etc.),
         * the functions in ConsoleStyledDocument that are called is remove().
         *
         * If you want to prevent text from being added, block insertString. If
         * you want to prevent text from being removed, block remove.
         *
         *
         * Using evt.consume() only helps in blocking actions that the system
         * needs to manually carry out. For example, backspace, enter, tab,
         * Ctrl-A, Ctrl-Backspace, etc. It doesn't block typing letters (doesn't
         * block uppercase either), symbols, numbers, spaces, etc.
         */
        char keyChar = evt.getKeyChar();
        if (isModifier(evt)) {
            //Do nothing if the event is triggered by pressing a modifier (ctrl, alt, shift, etc.)
        } else if (modified(evt) && !shift) { // Don't let shift trigger it because that will just go to the typers
            if (ctrl && !alt && !shift && !altGraph) { // If Ctrl is being held and nothing else
                //Support Ctrl-A, Ctrl-C, and Ctrl-V.
                switch (keyCode) {
                    case KeyEvent.VK_A -> {
                        //Ctrl-A
                    }
                    case KeyEvent.VK_C -> {
                        //Ctrl-C
                    }
                    case KeyEvent.VK_V -> {
                        //Ctrl-V
                    }
                    case KeyEvent.VK_BACK_SPACE -> {
                        keyPressedCtrlBackspace(evt);
                    }
                }
            }
        } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            keyPressedBackspace(evt);
        } else if (isArrow(evt)){
            keyPressedArrow(evt);
        } else {
            if (keyCode != KeyEvent.VK_ENTER) {
                //If un-modified letter, number, whitespace, or symbol,--that is not an enter--then go as normal.
                keyPressedCharacter(evt);
            } else {
                keyPressedEnter(evt);
            }
        }

    }
    
    /**
     * Returns whether or not a KeyEvent was caused by the user pressing one of the arrows on the keyboard
     * @param evt the key event triggered by the user
     * @return whether the key event is caused by an arrow
     */
    private boolean isArrow(KeyEvent evt){
        int evtNum = evt.getKeyCode();
        return evtNum == KeyEvent.VK_LEFT || evtNum == KeyEvent.VK_RIGHT || 
                evtNum == KeyEvent.VK_UP || evtNum == KeyEvent.VK_DOWN;
    }
    
    
    /**
     * Handles what happens when the user presses an arrow on their keyboard, into the console window
     * @param evt the key event triggered by the user pressing the arrow
     */
    private void keyPressedArrow(KeyEvent evt){
        int evtCode = evt.getKeyCode();
        if (evtCode == KeyEvent.VK_LEFT || evtCode == KeyEvent.VK_RIGHT){
            //If pressed left or right arrow do nothing
        } else {
            //If pressed up or down arrow...do nothing for now; later implement retrieving previous commands
        }
    }

    /**
     * Returns if the key event has any modifiers
     *
     * @param evt the key event to check
     * @return whether the key event has any modifiers. A key event has
     * modifiers if Alt, Alt-Graph, Ctrl, or Shift is being held while the key
     * event happens
     */
    private boolean modified(KeyEvent evt) {
        return evt.isAltDown() || evt.isAltGraphDown() || evt.isControlDown() || evt.isShiftDown();
    }

    /**
     * Returns if the key event is a modifier
     *
     * @param evt the key event to check
     * @return whether the key event is a modifier. The key events generated by
     * pressing Ctrl, Alt, Alt-Graph, and Shift would cause this.
     */
    private boolean isModifier(KeyEvent evt) {
        int keyCode = evt.getKeyCode();
        return keyCode == KeyEvent.VK_CONTROL || keyCode == KeyEvent.VK_ALT || keyCode == KeyEvent.VK_ALT_GRAPH
                || keyCode == KeyEvent.VK_SHIFT;
    }

    /**
     * Returns whether the key event describes something that can be typed.
     *
     * @param evt the key event to check
     * @return whether the key event describes something that can be typed. This
     * includes a letter in the alphabet, a number, a whitespace, a symbol, etc.
     */
    private boolean isTyper(KeyEvent evt) {
        int keyCode = evt.getKeyCode();
        return Character.isAlphabetic(keyCode) || Character.isDigit(keyCode)
                || Character.isWhitespace(keyCode);
    }

    /**
     * Handles the key event sent into keyPressed(KeyEvent evt) when it isn't
     * "special". It's essentially the "default" option to handle text.
     * Generally, keys that come to this function are letters of the alphabet,
     * numbers/digits, maybe tabs, etc.
     *
     * Whenever the user types a character that would cause this function to be
     * called, if the user's text cursor's index is less than the starting index
     * (the region where the user isn't allowed to edit text), then the text
     * will be added to end of the console window's text, and the text cursor
     * will move there as well.
     *
     * @param evt the key event the user started when pressing a key on their
     * keyboard
     * @see #keyPressed(java.awt.event.KeyEvent)
     * @see #startingIndex
     */
    private void keyPressedCharacter(KeyEvent evt) {
        int textCursorIndex = consoleWindow.getTextCursorIndex();
        uiHandler.addText(evt.getKeyChar() + "", textCursorIndex);
    }

    /**
     * Handles the key event sent into keyPressed(KeyEvent evt) when the key
     * pressed is a normal backspace. However, if the key is a Ctrl-Backspace,
     * then this function won't be called, but instead
     * keyPressedCtrlBackspace(KeyEvent)
     *
     * @param evt the key event the user started when pressing a key on their
     * keyboard
     * @see #keyPressed(java.awt.event.KeyEvent)
     * @see #keyPressedCtrlBackspace(java.awt.event.KeyEvent)
     */
    private void keyPressedBackspace(KeyEvent evt) {
        int selectionStart = consoleTextPane.getSelectionStart();
        int selectionEnd = consoleTextPane.getSelectionEnd();
        
        if (selectionStart != selectionEnd) {
            //If text is selected
            uiHandler.removeText(selectionStart, selectionEnd);
            unselectText();
            
        } else {
            uiHandler.removeCharacter(consoleWindow.getTextCursorIndex() - 1);
        }
        
    }
    
    /**
     * If the user has any text selected in the console window, it will be unselected.
     */
    private void unselectText(){
        int cursorIdx = consoleWindow.getTextCursorIndex();
        consoleTextPane.setSelectionStart(cursorIdx);
        consoleTextPane.setSelectionEnd(cursorIdx);
    }

    /**
     * Handles the key event sent into keyPressed(KeyEvent evt) when the key
     * pressed is a backspace while Ctrl is being held down.
     *
     * @param evt the key event the user started when pressing a key on their
     * keyboard
     * @see #keyPressed(java.awt.event.KeyEvent)
     */
    private void keyPressedCtrlBackspace(KeyEvent evt) {

        int selectionStart = consoleTextPane.getSelectionStart();
        int selectionEnd = consoleTextPane.getSelectionEnd();

        int cursorIdx = consoleWindow.getTextCursorIndex();

        if (selectionStart != selectionEnd) {
            //If something is selected, unselect it
            unselectText();
        }

        //Figures out what type of character the character before the text cursor is, and deletes all consecutive characters that are 
        //of that same type.
        String consoleWindowText = consoleWindow.getTextPane().getText();
        char c = consoleWindowText.charAt(cursorIdx - 1);
        int cType = charTypeEval(c);
        
        int idxToRemoveTo = 0;
        
        for (int i = cursorIdx - 2; i >= 0; i--) { // We start at cursor idx - 2 since we are checking the characters before the character that is directly before the text cursor
            char currentChar = consoleWindowText.charAt(i); 
            if (charTypeEval(currentChar) != cType) {
                idxToRemoveTo = i + 1; // Once we find a character not of the same type, we set the index of the character before it
                break;                   // to be removed
            }
        }
        uiHandler.removeText(idxToRemoveTo, cursorIdx - 1);
    }

    /**
     * A private little helper function that gives a character a number based on
     * what type of character it is.
     *
     * @param c the character to evaluate.
     * @return returns 0 if c is alphabetic, 1 if c is a number, 2 if c is
     * whitespace, and otherwise 3.
     */
    private int charTypeEval(char c) {
        if (Character.isAlphabetic(c)) {
            return 0;
        } else if (Character.isDigit(c)) {
            return 1;
        } else if (Character.isWhitespace(c)) {
            return 2;
        } else {
            return 3;
        }
    }

    /**
     * Handles the key event sent into keyPressed(KeyEvent evt) when the key
     * pressed is the enter button.
     *
     * @param evt the key event the user started when pressing a key on their
     * keyboard
     * @see #keyPressed(java.awt.event.KeyEvent)
     */
    private void keyPressedEnter(KeyEvent evt) {
        //Add the two new lines for formatting
        uiHandler.enter();
//        ..Have created the Command object. Now work on conntecting the InputInterpreter to the MainCompiler
//                ..Start getting a command to work, even if its useless (like making it so that when the user types "hi"),
//                the program does System.out.println("Hi to you too!")
    }
}
