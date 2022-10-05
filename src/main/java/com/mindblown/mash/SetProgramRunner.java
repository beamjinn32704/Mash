/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mindblown.mash;

import com.mindblown.util.ArrayUtil;

/**
 *
 * @author beamj
 */
public class SetProgramRunner extends ConsoleProgramRunner{
    
    public static final char varIdentifier = '|';
    
    public static final CommandNameHelper name = new CommandNameHelper("set");
    
    private static final String commandDesc = "This command takes in at least two inputs."
            + " The first input will be the variable name. The variable name can't have the symbol "
            + varIdentifier + " in it. The value of the input becomes everything after"
            + " the first space character after the variable name. The variable's value can then be used by using "
            + varIdentifier + "variable_name" + varIdentifier + ". If you want to be able to use the character sequence " + varIdentifier + ","
            + " then use " + varIdentifier + varIdentifier + ".";
    
    private static final String examples = "To set a variable called MyVar to the value Hello:\n"
            + "set MyVar Hello";
    
    private static final PlainParamsHelper variableNameOption = new PlainParamsHelper("variable_name", false, "This is "
            + "the name of the variable that you want to set a value to.");
    
    private static final PlainParamsHelper variableValueOption = new PlainParamsHelper("variable_value", false, "This is "
            + "will be what the variable holds.");
    
    private static final AttrHelper longTermOption = new AttrHelper("longterm", true, "When this option is used, the command"
            + " will save the variable and will allow the user to use the variable's value after the program has "
            + "exited and opened again.");
    
    public SetProgramRunner(ProgramRouter router) {
        super(router);
    }
    
    @Override
    public ConsoleProgram genProgram(CommandData data) {
        return new SetProgram(data);
    }
    
    private class SetProgram extends ConsoleProgram {
        
        public SetProgram(CommandData options) {
            super(options);
        }
        
        @Override
        public void run() throws Exception {
            if(getOptions().showHelp()){
                CommandOptionHelper[] options = ArrayUtil.toArray(variableNameOption, variableValueOption, longTermOption);
                router.addProgramTextToEnd(this, ProgramHelper.helpGenerator(name, commandDesc, examples, options), null);
                return;
            }
            ProgramData data = genData();
            String n = data.variable.getVariableName();
            String v = data.variable.getVariableValue();
            if(n == null){
                router.addProgramTextToEnd(this, ProgramHelper.genGoToHelpMenuMessage(name.getCommandName(), "Invalid syntax! No variable name or "
                        + "value was given!"));
                return;
            }
            
            if(v == null){
                router.addProgramTextToEnd(this, ProgramHelper.genGoToHelpMenuMessage(name.getCommandName(), "Invalid syntax! No variable"
                        + " value was given!"));
                return;
            }
            if(n.contains(varIdentifier + "")){
                router.addProgramTextToEnd(this, ProgramHelper.genGoToHelpMenuMessage(name.getCommandName(), "The variable name can't "
                        + "have the symbol " + varIdentifier  + " in it!"));
                return;
            }
            ConsoleCompiler.addVariable(data.variable);
        }
        
        private ProgramData genData(){
            String[] plainParams = getOptions().getPlainParams();
            String name;
            String val;
            boolean isLongTerm = false;
            Attr longTermAttrObj = getOptions().getAttr(longTermOption);
            if(longTermAttrObj != null){
                isLongTerm = Boolean.parseBoolean(longTermAttrObj.getAttrVal());
            }
            if(plainParams.length == 0){
                name = val = null;
            } else {
                String varName = plainParams[0] + "";
                String plainParamsSum = getOptions().getPlainParamsSum() + "";
                int startInd = plainParamsSum.indexOf(varName) + varName.length() + 1;
                if(startInd >= plainParamsSum.length()){
                    name = varName;
                    val = null;
                } else {
                    name = varName;
                    val = plainParamsSum.substring(startInd);
                }
            }
            UserVariable var = new UserVariable(name, val, isLongTerm);
            return new ProgramData(var);
        }
        
        private class ProgramData {
            private UserVariable variable;
            
            public ProgramData(UserVariable variable) {
                this.variable = variable;
            }
        }
        
    }
    
}