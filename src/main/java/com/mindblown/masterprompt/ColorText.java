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
public class ColorText{
    private Color col;
    private Variable text;

    public ColorText(Color col, Variable text) {
        this.col = col;
        this.text = text;
    }
    
    public ColorText(Color col, String t){
        this.col = col;
        text = new StrVar(t);
    }

    public Color getCol() {
        return col;
    }

    public String getText() {
        return text.getString();
    }

    public void setCol(Color col) {
        this.col = col;
    }

    public void setText(Variable text) {
        this.text = text;
    }
}