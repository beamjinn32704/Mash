/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.shell;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

/** 
 * Objects of this class are used to change the settings of a Console Window. These include things like changes to 
 * the scroll rate of the scroll bars, the background color of the Console Window, etc.
 * @author beamj
 */
public class ConsoleWindowSettingsEditor {
    
    //This is the Console Window that objects of this class will be modifying
    private ConsoleWindow consoleWindow;
    
    /*These are components of the console */
    private JScrollPane consoleScrollPane; //the window pane that contains the console text pane and adds the scrollbar
    private JTextPane consoleTextPane; // the console window text pane
    
    //The text cursor of the console window
    private ConsoleWindowCaret consoleCaret;
    
    /* Font Settings */
    //System font settings are the font settings objects of this class 'prefers'. What this means is that in functions when this object 
    //gets to choose the type of formatting the font has, it will use these. An example of a function like this setFont(Font font).
    private float systemFontSize = 13;
    private int systemFontStyle = Font.BOLD;
    
    //This is used to determine the color of a piece of text in the console window when the piece of text doesn't come 
    //with a certain color to set the text to
    private Color defaultTextColor;
    
    /**
     * Instantiates a Console Window Settings Editor. 
     * @param consoleWin the console window that objects of this class will modify and change the settings of
     */
    public ConsoleWindowSettingsEditor(ConsoleWindow consoleWin) {
        consoleWindow = consoleWin;
        
        
        consoleScrollPane = consoleWindow.getScrollPane();
        consoleTextPane = consoleWindow.getTextPane();
    }
    
    /** Set the font for the text in the console window. This function will use the font passed to it, however, it won't use the font size or the 
     * font style passed to this function. It will instead use its default settings. To set the font without these changes being made, use the 
     * setFont(Font, float, int) function.
     * 
     * @param font the font to be used
     * @see #setFont(java.awt.Font, float, int) 
     * 
     */
    public void setFont(Font font){
        //Pass the default settings to the setFont function
        setFont(font, systemFontSize, systemFontStyle);
    }
    
    public void setFont(Font font, float fontSize, int fontStyle){
        //Get the font and set it to the specified font size and font style
        Font changedFont = font.deriveFont(fontSize);
            changedFont = changedFont.deriveFont(fontStyle);
            
            //Apply the font to the console window
            consoleTextPane.setFont(changedFont);
    }
    
    /**
     * Sets the blink rate of the console window's text cursor
     * @param rate the blink rate
     */
    public void setBlinkRate(int rate){
        consoleCaret.setBlinkRate(rate);
    }
    
    /**
     * Sets the background color of the console window
     * @param c the color
     */
    public void setBackgroundCol(Color c){
        consoleTextPane.setBackground(c);
        consoleScrollPane.setBorder(null);
        consoleWindow.repaint();
    }
    
     /**
     * Sets the colors of the console's scroll bar and its buttons. Credit: This code (is probably) from 
     * https://stackoverflow.com/questions/11074172/change-background-color-of-scrollbar-end-buttons
     * @param track The color of what can be considered as the "background" (or the track) of the scrollbar
     * @param thumb The color of what is used to scroll on the scrollbar (called the thumb)
     */
    public void setScrollBarColors(Color track, Color thumb){
        
        consoleScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                //Set the colors to the track and thumb
                this.trackColor = new Color(track.getRGB());
                this.thumbColor = new Color(thumb.getRGB());
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                //Overrides the color setting for the decrease (down) button on the scrollbar so that it fits the track color
                JButton b = super.createDecreaseButton(orientation);
                b.setBorder(new LineBorder(trackColor));
                b.setBackground(trackColor);
                return b;
            }
            
            @Override
            protected JButton createIncreaseButton(int orientation) {
                //Overrides the color setting for the increase (up) button on the scrollbar so that it fits the track color
                JButton b = super.createIncreaseButton(orientation);
                b.setBorder(new LineBorder(trackColor));
                b.setBackground(trackColor);
                return b;
            }
        });
        consoleWindow.repaint();
    }
    
    public void setPromptForegroundCol(Color fg){
        //Sets the foreground of the console window and the text cursor to match the foreground color
        consoleTextPane.setForeground(fg);
        consoleTextPane.setCaretColor(fg);
        
        consoleWindow.repaint();
    }
    
    /**
     * This sets the title of the console window
     * @param title the title
     */
    public void setConsoleWindowTitle(String title){
        consoleWindow.setTitle(title);
    }
}
