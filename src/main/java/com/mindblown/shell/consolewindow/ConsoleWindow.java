/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package com.mindblown.shell.consolewindow;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * This is the Java window that displays the information in the console (it
 * displays the prompts given by the console, the user's commands, the result's
 * from the user's commands, etc.). This class also gives information about the
 * text in the console window (where the text cursor is, the length of the text,
 * etc.).
 *
 * @author beamj
 */
public class ConsoleWindow extends javax.swing.JFrame {

    private SettingsEditor settingsEditor;
    private TextEditor textEditor;
    private InputInterpreter inputInterpreter;

    // This contains all the text and the styles of the text (text color, bold, italic, etc.) in the console text pane
    private ConsoleStyledDocument consoleTextStyledDocument = new ConsoleStyledDocument();
    
//    /**
//     * This is the Program object that the ConsoleWindow is linked to. All commands typed into the console window are routed to 
//     * the Program, which then interprets the commands.
//     */
//    final private Program program;
//    
    /**
     * Instantiates a ConsoleWindow object.The Compiler object is used to interpret the commands that the user types into the 
 ConsoleWindow.
     * @param compiler the Compiler used to interpret the commands that the user types into the ConsoleWindow.
     */
    public ConsoleWindow() {
        initComponents();
        
        settingsEditor = new SettingsEditor(this);
        textEditor = new TextEditor(this);
        inputInterpreter = new InputInterpreter(this, new UserInputHandler(this));
        
//        program = new Program(compiler, this);
        
        textEditor.addText("Prompt>", 0);
        
    }
    
    /**
     * Gets the length of the text in the console window
     * @return the length of the text
     */
    public int getTextLength(){
        return consoleTextStyledDocument.getLength();
    }
    
    /**
     * This object is used to edit the settings of the console window. This
     * includes background color, foreground color, fonts, etc.
     *
     * @return
     */
    public SettingsEditor getSettingsEditor() {
        return settingsEditor;
    }

    /**
     * This object is used to make changes to the text of the console window.
     *
     * @return
     */
    public TextEditor getTextEditor() {
        return textEditor;
    }

    /**
     * Get the scroll pane that contains the console window text pane
     *
     * @return the scroll pane
     */
    public JScrollPane getScrollPane() {
        return consoleScrollPanel;
    }

    /**
     * Get the console window text pane.
     *
     * @return the text pane
     */
    public JTextPane getTextPane() {
        return consoleTextPane;
    }

    /**
     * This returns the styled document containing the text, styles (italics,
     * bold, colors, etc), and more, of the console window's text
     *
     * @return the styled document containing the text, styles (italics, bold,
     * colors, etc), and more, of the console window's text
     */
    public ConsoleStyledDocument getConsoleWindowTextStyledDocument() {
        return consoleTextStyledDocument;
    }

    /**
     * Returns the index of the user's text cursor
     *
     * @return the index in the console window's main body of text where the
     * text cursor is located. For ex: if the text cursor was located at the
     * very beginning of the console's text, the index would be 0.
     */
    public int getTextCursorIndex() {
        return consoleTextPane.getCaretPosition();
    }

    /**
     * Returns the length of the console window's text.
     *
     * @return how many characters are in the console window's main body of
     * text. For example, if there are no characters, this function will return
     * 0. If there is just the text " hi there", this will return 9.
     */
    public int getNumOfCharacters() {
        return consoleTextStyledDocument.getLength();
        
        //Important Note: If you get the length through consoleWindow.getTextPane().getText().length(), you may get a length that is larger than the 
        //length returned by this function--a prime example is when the text in the console window includes new lines (\n).
        //One of the reasons for this is that the text gotten through consoleWindow.getTextPane().getText() contains "\r" which isn't a character,
        //while the consoleTextStyleDoc doesn't count that when calculating its length
    }

//    public void replaceText(int startIndex, int length, ColorText... texts){
//        boolean atBottom = atBottom();
//        removeText(startIndex, length);
//        addText(texts);
//        formatScroll(atBottom);
//    }
//    
//    public void replaceText(int startIndex, int length, String text, int mode){
//        removeText(startIndex, length);
//        addText(text, mode);
//    }
//    
//    public void addText(ColorText[] texts){
//        for(int i = 0; i < texts.length; i++){
//            ColorText ct = texts[i];
//            setCurrentPrintStyle(ct.getCol());
//            addText(ct.getText(), 27);
//        }
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        consoleScrollPanel = new javax.swing.JScrollPane();
        consoleTextPane = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Console");

        consoleScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        consoleScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        consoleTextPane.setDocument(consoleTextStyledDocument);
        consoleTextPane.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        consoleTextPane.setForeground(new java.awt.Color(182, 137, 98));
        consoleTextPane.setToolTipText("");
        consoleTextPane.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                consoleTextPaneKeyPressed(evt);
            }
        });
        consoleScrollPanel.setViewportView(consoleTextPane);

        getContentPane().add(consoleScrollPanel, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 637, 385);
    }// </editor-fold>//GEN-END:initComponents

    private void consoleTextPaneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_consoleTextPaneKeyPressed
        inputInterpreter.keyPressed(evt);
    }//GEN-LAST:event_consoleTextPaneKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane consoleScrollPanel;
    private javax.swing.JTextPane consoleTextPane;
    // End of variables declaration//GEN-END:variables
}
