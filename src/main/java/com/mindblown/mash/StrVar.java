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
public class StrVar implements Variable {
    
    private String str;
    
    public StrVar(String str) {
        this.str = str;  
    }

    @Override
    public String getString() {
        return str;
    }
    
}
