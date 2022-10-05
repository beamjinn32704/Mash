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
public class CommandOptionHelper {
    private String optionName;
    private String[] alternativeOptionNames;
    private String prefix;
    private String syntaxRepStart;
    private String syntaxRepEnd;
    private boolean optional;
    private String description;
    
    public CommandOptionHelper(String optionName, String prefix, String syntaxRepStart, String syntaxRepEnd, boolean optional, String description, String... alternativeOptionNames) {
        this.optionName = optionName;
        this.alternativeOptionNames = alternativeOptionNames;
        this.prefix = prefix;
        this.syntaxRepStart = syntaxRepStart;
        this.syntaxRepEnd = syntaxRepEnd;
        this.optional = optional;
        this.description = description;
    }
    
    public String getSyntaxRep(){
        String rep = syntaxRepStart + prefix + optionName + syntaxRepEnd;
        for(int i = 0; i < alternativeOptionNames.length; i++){
            rep += " / " + syntaxRepStart + prefix + alternativeOptionNames[i] + syntaxRepEnd;
        }
        return rep;
    }
    
    public String getDescriptionOfOption(){
        String desc = "";
        String startTemp;
        String endTemp;
        startTemp = prefix + "";
        endTemp = "";
        desc += getDC(startTemp + optionName + endTemp, description, false);
        for(int i = 0; i < alternativeOptionNames.length; i++){
            desc += "\n" + getDC(startTemp + alternativeOptionNames[i] + endTemp, "Go to " + optionName, true);
        }
        return desc;
    }
    
    private String getDC(String name, String d, boolean isAlternative){
        String desc;
        if(isAlternative){
            desc = "[ALTR_OPT] ";
        } else {
            if(optional){
                desc = "[OPTIONAL] ";
            } else {
                desc = "[REQUIRED] ";
            }
        }
        desc += name + " : " + d;
        return desc;
    }
    
    public String[] getAlternativeOptionNames() {
        return alternativeOptionNames;
    }
    
    public String getOptionName() {
        return optionName;
    }
    
    public String getPrefix() {
        return prefix;
    }

    @Override
    public String toString() {
        return prefix + optionName;
    }
}