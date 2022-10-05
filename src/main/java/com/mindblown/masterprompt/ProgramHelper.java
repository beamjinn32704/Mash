/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mindblown.masterprompt;

import static com.mindblown.masterprompt.ConsoleProgramRunner.helpOption;
import com.mindblown.util.ArrayUtil;
import com.mindblown.util.StringUtil;

/**
 *
 * @author beamj
 */
public class ProgramHelper {
    public static String[] toArr(CommandOptionHelper option){
        String[] alt = option.getAlternativeOptionNames();
        String opt = option.getOptionName();
        String[] strs = ArrayUtil.joinToArray(alt, opt);
        return strs;
    }
    
    public static String[] toArr(CommandNameHelper name){
        String[] alt = name.getAlternativeCommandNames();
        String opt = name.getCommandName();
        String[] strs = ArrayUtil.joinToArray(alt, opt);
        return strs;
    }
    
    public static ColorText[] genGoToHelpMenuMessage(String commandName, String before) {
        String bef = before + " ";
        if(bef.isBlank()){
            bef = "";
        }
        return ArrayUtil.toArray(new ColorText(ConsoleTextEditor.blueColor, bef + "Type command "),
                new ColorText(ConsoleTextEditor.lightGreenColor, commandName + " " + helpOption.toString() + " "),
                new ColorText(ConsoleTextEditor.blueColor, "to visit help!"));
    }
    
    public static ColorText[] genGoToHelpMenuMessage(String commandName) {
        return genGoToHelpMenuMessage(commandName, "");
    }
    
    public static String helpGenerator(CommandNameHelper name, String commandDesc, String examples, CommandOptionHelper... options){
        String leftMargin = "  ";
        String help = "\n" + StringUtil.toFirstUpperCase(name.getCommandName()) + " Help";
        help += "\n" + genNumOfDashes(help.length() + 2) + "\n\n";
        
        if(!commandDesc.isBlank()){
            help += "Description:\n" + commandDesc + "\n\n";
        }
        
        help += "Official syntax:\n" + name.getCommandNameRep();
        for(int i = 0; i < options.length; i++){
            CommandOptionHelper option = options[i];
            help += " " + option.getSyntaxRep();
        }
        
        if(!examples.isBlank()){
            help += "\n\nExamples:\n\n" + examples;
        }
        
        if(options.length != 0){
            help += "\n\nCommand Options:\n\n";
            for(int i = 0; i < options.length; i++){
                CommandOptionHelper option = options[i];
                String desc = option.getDescriptionOfOption() + "";
                help += desc;
                if(i != options.length - 1){
                    help += "\n";
                }
            }
        }
        
        boolean go = true;
        help = help.replace("\n", "\n" + leftMargin);
        while(go){
            String lookingFor = "\n" + leftMargin + "\n";
            int index = help.indexOf(lookingFor);
            if(index < 0){
                go = false;
            } else {
                help = help.substring(0, index) + "\n\n" + help.substring(index + lookingFor.length());
            }
        }
        
        return help;
    }
    
    private static String genNumOfDashes(int num){
        String dashes = "";
        for(int i = 0; i < num; i++){
            dashes += "-";
        }
        return dashes;
    }
}
