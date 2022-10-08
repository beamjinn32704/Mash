/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.mash;

/**
 *
 * @author beamj
 */
public class CommandNameHelper {
    private String commandName;
    private String[] alternativeCommandNames;

    public CommandNameHelper(String commandName, String... alternativeCommandNames) {
        this.commandName = commandName;
        this.alternativeCommandNames = alternativeCommandNames;
    }

    public String[] getAlternativeCommandNames() {
        return alternativeCommandNames;
    }

    public String getCommandName() {
        return commandName;
    }
    
    public String getCommandNameRep(){
        String rep = commandName + "";
        for(int i = 0; i < alternativeCommandNames.length; i++){
            rep += " / " + alternativeCommandNames[i];
        }
        return rep;
    }
}