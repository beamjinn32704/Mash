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
public class ParamsHelper extends CommandOptionHelper{
    
    public ParamsHelper(String optionName, boolean optional, String description, String... alternativeOptionNames) {
        super(optionName, "-", "[", "]", optional, description, alternativeOptionNames);
    }
    
}
