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
public class UnSetProgramRunner extends ConsoleProgramRunner{
    
    public static final CommandNameHelper name = new CommandNameHelper("unset");
    
    private static final String commandDesc = "This command deletes user-set variables.";
    
    private static final String examples = "To delete variable MyVar:\n"
            + "unset MyVar\n\nTo delete all variables:\nunset";
    
    private static final PlainParamsHelper varNameOption = new PlainParamsHelper("variable_name", true, "This is the name "
            + "of the user-set variable to delete.");
    
    public UnSetProgramRunner(ProgramRouter router) {
        super(router);
    }

    @Override
    public ConsoleProgram genProgram(CommandData data) {
        return new UnSetProgram(data);
    }
    
    private class UnSetProgram extends ConsoleProgram {
        
        public UnSetProgram(CommandData options) {
            super(options);
        }

        @Override
        public void run() throws Exception {
            if(getOptions().showHelp()){
                router.addProgramTextToEnd(this, ProgramHelper.helpGenerator(name, commandDesc, examples, varNameOption), null);
                return;
            }
            ProgramData data = genData();
            if(data.deleteAll){
                ConsoleCompiler.removeAllVariables();
            } else {
                ConsoleCompiler.removeVariable(data.varName);
            }
        }
        
        private ProgramData genData(){
            String[] plainParams = getOptions().getPlainParams();
            if(plainParams.length == 0){
                return new ProgramData("", true);
            } else {
                return new ProgramData(plainParams[0], false);
            }
        }
        
        private class ProgramData {
            private String varName;
            private boolean deleteAll;

            public ProgramData(String varName, boolean deleteAll) {
                this.varName = varName;
                this.deleteAll = deleteAll;
            }
        }
        
    }
    
}
