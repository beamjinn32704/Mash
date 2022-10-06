/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.mash;

import java.awt.Color;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/** This class is used to edit the text displayed in the Console Window.
 *
 * @author beamj
 */
public class ConsoleTextEditor {
    private JTextPane consolePane;
    private final JScrollPane consoleScrollPanel;
    private ColorText[] defaultPrompt;
    
    public static Color redColor = new Color(220, 0, 0);
    public static Color blueColor = new Color(52, 108, 187);
    public static Color greenColor = new Color(182, 137, 98);
    public static Color lightGreenColor = new Color(0, 255, 9);
    
    private Color defaultColor;
    Style styleToUse;
    
    private StyledDocument promptStyleDoc;
    
    private ConsoleDocumentFilter filter;

    public ConsoleTextEditor(JTextPane consolePane, JScrollPane consoleScrollPanel, Color defaultCol, ColorText... defaultPrompt) {
        this.consolePane = consolePane;
        this.consoleScrollPanel = consoleScrollPanel;
        this.defaultPrompt = defaultPrompt;
        defaultColor = defaultCol;
        
        initSetDocument();
        styleToUse = consolePane.addStyle("Temp Style To Use", null);
    }

    public Color getDefaultColor() {
        return defaultColor;
    }
    
    public void setPromptForegroundCol(Color fg){
        consolePane.setForeground(fg);
        consolePane.setCaretColor(fg);
        defaultColor = fg;
    }
    
    private void initSetDocument(){
        promptStyleDoc = consolePane.getStyledDocument();
        filter = new ConsoleDocumentFilter();
        ((DefaultStyledDocument)consolePane.getDocument()).setDocumentFilter(filter);
    }
    
    /**
     *
     * @param add The text to add to the console.
     * @param textCol The color of the text to add.
     */
    public void addTextToEnd(String add, Color textCol){
        addText(add, getDocumentLength(), textCol);
    }
    
    /**
     *
     * @param add The text to add to the console.
     * @param startInd The index where to add the text.
     * @param textCol The color of the text to add. If null, it will go to the default color.
     */
    public void addText(String add, int startInd, Color textCol){
        try {
            boolean atBottom = atBottom();
            filter.allowChange();
            if(textCol == null){
                textCol = defaultColor;
            }
            StyleConstants.setForeground(styleToUse, textCol);
            promptStyleDoc.insertString(startInd, add, styleToUse);
            formatScroll(atBottom);
            //bringCaretToFront();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public StyledDocument getPromptStyleDoc() {
        return promptStyleDoc;
    }
    
     public void clear(){
        removeText(0, getDocumentLength());
    }
     
     public int getDocumentLength(){
        return promptStyleDoc.getLength();
    }
    
    public void replaceText(int startIndex, int length, ColorText... texts){
        removeText(startIndex, length);
        addText(texts, startIndex);
    }
    
    public void replaceText(int startIndex, int length, String text, Color textCol){
        removeText(startIndex, length);
        addText(text, startIndex, textCol);
    }
    
    public void addTextToEnd(ColorText[] texts){
        addText(texts, getDocumentLength());
    }
    
    public void addText(ColorText[] texts, int si){
        int startInd = si;
        boolean atBottom = atBottom();
        for (ColorText ct : texts) {
            String textToAdd = ct.getText();
            addText(textToAdd, startInd, ct.getCol());
            startInd += textToAdd.length();
        }
        formatScroll(atBottom);
    }
    
    private boolean atBottom(){
        JScrollBar vertScrollBar = consoleScrollPanel.getVerticalScrollBar();
        int val = vertScrollBar.getValue();
        int extent = vertScrollBar.getModel().getExtent();
        int max = vertScrollBar.getMaximum();
        boolean adjusting = vertScrollBar.getValueIsAdjusting();
        boolean atBottom = (val+extent) == max && !adjusting;
        return  atBottom;
    }
    
    private void formatScroll(boolean atBottom){
        if(atBottom){
            SwingUtilities.invokeLater(() -> {
                JScrollBar vertScrollBar = consoleScrollPanel.getVerticalScrollBar();
                vertScrollBar.setValue(vertScrollBar.getMaximum());
            });
        }
    }
    
    public void removeText(int indexFrom, int length){
        try {
            boolean atBottom = atBottom();
            filter.allowChange();
            promptStyleDoc.remove(indexFrom, length);
            formatScroll(atBottom);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void deleteFrom(int startInd){
        removeText(startInd, getDocumentLength() - startInd);
    }
}