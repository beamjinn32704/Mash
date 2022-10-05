/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.masterprompt;

/**
 *
 * @author beamj
 */
public class Main {
    
    public static MasterPrompt master;
    
    public static void main(String[] args) {
        master = MasterPrompt.genMasterPrompt();
        master.setVisible(true);
    }
}
