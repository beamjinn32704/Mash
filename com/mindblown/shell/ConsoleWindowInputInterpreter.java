/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.shell;

import com.mindblown.debuggerutil.DebugPrinter;
import com.mindblown.debuggerutil.DebugWatch;
import com.mindblown.util.ArrayListUtil;
import com.mindblown.util.StringUtil;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument.Content;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 * Objects of this class are used to interpret any key strokes the user types.
 * These key strokes include backspaces, up and down arrows, etc. An example of
 * using this object would be telling the object that a backspace was done, and
 * this object would return saying that a character needs to be deleted.
 *
 * @author beamj
 */
public class ConsoleWindowInputInterpreter {

    //This is the Console Window that objects of this class will be modifying
    private ConsoleWindow consoleWindow;

    /* These are components of the console */
    private JScrollPane consoleScrollPane; //the window pane that contains the console text pane and adds the scrollbar
    private JTextPane consoleTextPane; // the console window text pane

    //Used to make edits to the console window text
    private ConsoleWindowTextEditor textEditor;

    // This contains all the text and the styles of the text (text color, bold, italic, etc.) in the console text pane
    private ConsoleStyledDocument consoleTextStyledDocument;

    private MainCompiler mainCompiler;

    /**
     * Instantiates a Console Window Text Editor.
     *
     * @param consoleWin the console window that objects of this class will
     * modify and change the text of
     */
    public ConsoleWindowInputInterpreter(ConsoleWindow consoleWin) {
        consoleWindow = consoleWin;

        consoleScrollPane = consoleWindow.getScrollPane();
        consoleTextPane = consoleWindow.getTextPane();

        textEditor = consoleWindow.getTextEditor();
        consoleTextStyledDocument = consoleWindow.getConsoleWindowTextStyledDocument();

        mainCompiler = new MainCompiler(consoleWin);
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
            Logger.getLogger(ConsoleWindowInputInterpreter.class.getName()).log(Level.SEVERE, null, ex);
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
        } else if (modified(evt)) {
            if (ctrl && !alt && !shift && !altGraph) {
                //If Ctrl is being held, support Ctrl-A, Ctrl-C, and Ctrl-V.
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
                        keyPressedCtrlBackspace(evt); //Currently does nothing
                    }
                }
            }
        } else if (isTyper(evt)) {
            if (keyCode != KeyEvent.VK_ENTER) {
                //If un-modified letter, number, whitespace, or symbol,--that is not an enter--then go as normal.
                keyPressedCharacter(evt);
            } else {
                keyPressedEnter(evt);
            }
        } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            keyPressedBackspace(evt);
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
        return Character.isAlphabetic(keyCode) || Character.isDigit(keyCode) || Character.isWhitespace(keyCode);
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

        //If text cursor is in the un-editable zone, bring it to the end
        if (!textEditor.isCursorInEditableZone()) {
            textEditor.bringCaretToEnd();
            textCursorIndex = consoleWindow.getTextCursorIndex();
        }

        if (textEditor.isCursorInEditableZone()) {
            //If the text cursor index is now in the editable zone, add the character the user typed into 
            //the console window
            //We call the text editor instead of allowing the system, 
            //so that the allow edits boolean doesn't accidentally stay true while no edits happen
            textEditor.addChar(evt.getKeyChar(), textCursorIndex);
        }
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
        if (textEditor.inEditableZone(consoleWindow.getTextCursorIndex() - 1)) {
            //If the character before the text cursor (aka the character that will be backspaced) can be edited, remove 
            //the character
            textEditor.removeCharacter(consoleWindow.getTextCursorIndex() - 1);
        } else {
            //Otherwise, just bring the text cursor to the end of the document
            textEditor.bringCaretToEnd();
        }
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
        textEditor.addText("\n\n");
        textEditor.setStartingIndex(consoleWindow.getNumOfCharacters());
        mainCompiler.processCommand(new Command("go"));
//        ..Have created the Command object. Now work on conntecting the ConsoleWindowInputInterpreter to the MainCompiler
//                ..Start getting a command to work, even if its useless (like making it so that when the user types "hi"),
//                the program does System.out.println("Hi to you too!")
    }
}
