/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.shell;

import com.mindblown.debuggerutil.DebugPrinter;
import com.mindblown.util.StringUtil;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author beamj
 */
public class MainForTrialSake {

    public static void main(String[] args) {
        ConsoleWindow consoleWindow = new ConsoleWindow();
        consoleWindow.setVisible(true);
        ConsoleWindowSettingsEditor settingsEditor = consoleWindow.getSettingsEditor();

        //Set the font
        try {
            Font font1 = Font.createFont(Font.TRUETYPE_FONT, new File("FiraCode-VariableFont_wght.ttf"));
            //Font font2 = Font.createFont(Font.TRUETYPE_FONT, new File("DejaVuSansMono-Bold-webfont.ttf"));
            settingsEditor.setFont(font1);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        settingsEditor.setBackgroundCol(Color.black);
        settingsEditor.setPromptForegroundCol(Color.green);
    }
}
