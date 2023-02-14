/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.shell.consolewindow;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

/**
 * This is the document used to contain and add text to the main body of text in
 * the ConsoleWindow
 *
 * @author beamj
 */
public class ConsoleStyledDocument extends DefaultStyledDocument {

    //This is a lock that allows  edits only when the boolean is true
    private boolean allowEdits = false;

    public ConsoleStyledDocument() {
        super();
    }

    @Override
    protected void insert(int offset, ElementSpec[] data) throws BadLocationException {
//        DebugPrinter.printlnCustomNorm("Insert", offset, data);
        super.insert(offset, data); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
//        DebugPrinter.println("Insert Allowed");
    }

    @Override
    protected void insertUpdate(DefaultDocumentEvent chng, AttributeSet attr) {
//        DebugPrinter.printlnCustomNorm("InsertUpdate", chng, attr);
        super.insertUpdate(chng, attr); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
//        DebugPrinter.println("Insert Update Allowed");
    }

    /**
     * Allow an edit to be made to the document. If this isn't called before
     * making an edit, no changes will be made to the document
     */
    public void allowEdits() {
        allowEdits = true;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        //Understanding params: offs means the index where the text is being added, str is the text being added, and I don't know
        //what a is

//        DebugPrinter.printlnCustomNorm("InsertString", offs, str, a);
        if (allowEdits) {
            allowEdits = false;
            super.insertString(offs, str, a);
        }

    }

    @Override
    public void remove(int offs, int len) throws BadLocationException {
//        DebugPrinter.printlnCustomNorm("Remove", offs, len, "Edits Allowed: " + allowEdits);
        //Understanding params: offs means the index at which the text is to start to be removed. Len is how much text is to be 
        //removed
        if (allowEdits) {
            allowEdits = false;
            super.remove(offs, len); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }
    }

    @Override
    public void replace(int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if(allowEdits){
            allowEdits = false;
            super.replace(offset, length, text, attrs);
        }
    }

}


/** Interpreting Keys Outline
If it's just a modifier, ignore.

Nothing happens in styleddoc

If un-modified letter, number, or symbol, then go as normal.

Styled Doc Steps: Replace nest InsertString nest InsertUpdate

If backspace, go as normal

Remove (only if there is a letter or whtaever to remove. Otherwise, nothing happens)

If enter, go as normal

Replace nest insertString nest insertUpdate

If Ctrl is being held, support Ctrl-A, Ctrl-C, and Ctrl-V.

Ctrl-A: nothing in style doc
Ctrl-C: nothign in style doc
Ctrl-V: replace nest insertString nest insertUpdate

When text is replaced with other text (through selecting text and then typing words), what is called is replace nest (remove, (insertString nest insertUpdate))

It looks like that when something is added, it goes like replace nest insertString nest insertUpdate
When something is removed, remove is called
When nothing happens to the text, nothing happens


These show that if you want to prevent text from being added, block insertString
If you want to prevent text from being removed, block remove



 */
