/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mindblown.masterprompt;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

/** This is the Java window that displays the information in the console (it displays the prompts given by the 
 * console, the user's commands, the result's from the user's commands, etc.)
 *
 * @author beamj
 */
public class ConsoleWindow extends javax.swing.JFrame {
    
    private ConsoleWindowCaret consoleCaret;
    private ConsoleInteractor interactor;
    
    public static ConsoleWindow staticConsole;
    
    public ConsoleWindow(Compiler compiler, ColorText... colorPrompt){
        consoleCaret = new ConsoleWindowCaret();
        initComponents();
        //Making the switch to ConsoleInteractor. Moved lots of important
        //stuff to interactor. Fix the errors and move to ConsoleInteractor.
        //Basically be able to make a way for commands and programs to be able
        //to summon a prompter and create a custom compiler to compile custom 
        //commands. So once finishing making it portable, implement it in Scripter.
        staticConsole = this;
        interactor = new ConsoleInteractor(new ConsoleTextEditor(consolePane, consoleScrollPanel, consolePane.getForeground(), colorPrompt)
                , compiler, colorPrompt);
        interactor.formatCaretAndIndexOfSafety();
        String start = "WCD Compiler.\n\n";
        
        setFont();
        
        interactor.getConsoleEditor().addTextToEnd(start, null);
        interactor.newPrompt();
    }

    public JTextPane getConsolePane() {
        return consolePane;
    }

    public ConsoleInteractor getInteractor() {
        return interactor;
    }
    
    private void setFont(){
        try {
            Font myFont = Font.createFont(Font.TRUETYPE_FONT, new File("FiraCode-VariableFont_wght.ttf"));
//            Font myFont = Font.createFont(Font.TRUETYPE_FONT, new File("DejaVuSansMono-Bold-webfont.ttf"));
            myFont = myFont.deriveFont(13f);
            myFont = myFont.deriveFont(Font.BOLD);
            System.out.println("SIZE: " + myFont.getSize());
            consolePane.setFont(myFont);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("UNABLE TO USE CUSTOM FONT!");
        }
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
    
    
    public void bringCaretToFront(){
        consolePane.setCaretPosition(interactor.getDocumentLength());
    }
    
    public void setBlinkRate(int rate){
        consoleCaret.setBlinkRate(rate);
    }
    
    public void setPromptBackgroundCol(Color c){
        consolePane.setBackground(c);
        consoleScrollPanel.setBorder(null);
        repaint();
    }
    
    public void setScrollBarColors(Color track, Color thumb){
        
        consoleScrollPanel.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.trackColor = new Color(track.getRGB());
                this.thumbColor = new Color(thumb.getRGB());
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton b = super.createDecreaseButton(orientation);
                b.setBorder(new LineBorder(trackColor));
                b.setBackground(trackColor);
                return b;
            }
            
            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton b = super.createIncreaseButton(orientation);
                b.setBorder(new LineBorder(trackColor));
                b.setBackground(trackColor);
                return b;
            }
        });
        repaint();
    }
    
    public void setPromptForegroundCol(Color fg){
        interactor.getConsoleEditor().setPromptForegroundCol(fg);
        repaint();
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        consoleScrollPanel = new javax.swing.JScrollPane();
        consolePane = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WCD");

        consoleScrollPanel.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        consoleScrollPanel.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        consolePane.setBorder(null);
        consolePane.setFont(new java.awt.Font("Arial", 2, 12)); // NOI18N
        consolePane.setForeground(new java.awt.Color(182, 137, 98));
        consolePane.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                consolePaneKeyPressed(evt);
            }
        });
        consoleScrollPanel.setViewportView(consolePane);

        getContentPane().add(consoleScrollPanel, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 637, 385);
    }// </editor-fold>//GEN-END:initComponents
    
    private void consolePaneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_consolePaneKeyPressed
        interactor.keyPressed(evt);
    }//GEN-LAST:event_consolePaneKeyPressed

    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane consolePane;
    private javax.swing.JScrollPane consoleScrollPanel;
    // End of variables declaration//GEN-END:variables
}
