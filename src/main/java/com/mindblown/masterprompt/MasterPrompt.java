/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.masterprompt;

import java.awt.Color;


/**
 *
 * @author beamj
 */
public class MasterPrompt extends ConsoleWindow {
    
    private static Color bg = new Color(12, 12, 12);
    private static Color fg = new Color(0, 255, 9);
    
    public MasterPrompt(ConsoleCompiler compiler, ColorText... texts){
        super(compiler, texts);
        //Changes MasterPrompt settings to defualt (text of color, title of program, blink rate, etc.)
        settings();
        //addText("bettercap firstOne secondOne --version 2.23 firstTwo secondTwo --iface wlan0 firstThree secondThree", 0);
    }
    
    /**
     * 
     * @return 
     */
    public static MasterPrompt genMasterPrompt(){
        //Initiates the file system traveller
        FileSystemTraveller fileSystemTraveller = new FileSystemTraveller();
        
        ProgramRouter router = new ProgramRouter();
        ConsoleCompiler compiler = new ConsoleCompiler(router, fileSystemTraveller);
        
        ColorText[] texts = new ColorText[]{
            new ColorText(ConsoleTextEditor.redColor, new StrVar("beamj@relic")),
            new ColorText(ConsoleTextEditor.greenColor, new StrVar("~")),
            new ColorText(ConsoleTextEditor.greenColor, fileSystemTraveller),
            new ColorText(fg.darker(), new StrVar("# "))
        };
        
        MasterPrompt mp = new MasterPrompt(compiler, texts);
        router.setPrompt(mp);
        return mp;
    }
    
    /**
     * Sets the settings of the console. This includes the title of the console page, the blink rate of the text cursor, 
     * the background colors, etc.
     */
    private void settings(){
        setTitle("CN Relic");
        setPromptBackgroundCol(bg);
        setPromptForegroundCol(fg);
        setScrollBarColors(new Color(23, 23, 23), new Color(77, 77, 77));
        setBlinkRate(500);
    }
    
    /*
        LightenedNight
        InvisibleDay
        Fast Quick Dark
        cebris
        relic
        yinmi
        komohi
        seri
        mystic
        mystique
        gizli
        gizmak
        lain
        lirain
        kardan
        anak
        karnak
        karnax
        xanrak
        nark
        verlig
        kryos
        RAX
        NAR
        ANAK
        THRAX
        RIM
        DAW
        kar
        nar
        Ends With ax or rk
        kyrox
        anark
        anark
        raxnar
        world changer
        kingdom shaper
        walls come down
        narax
        antrax
        narthrax
        kar
        karthrax
        rogue
        rague
        Raemen
        */
}