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
public class ClearProgramRunner extends ConsoleProgramRunner {
    
    public static CommandNameHelper name = new CommandNameHelper("clear");
    
    private static final String commandDesc = "This command clears the screen.";
    
    public ClearProgramRunner(ProgramRouter router) {
        super(router);
    }

    @Override
    public ConsoleProgram genProgram(CommandData data) {
        return new ClearProgram(data);
    }
    
    private class ClearProgram extends ConsoleProgram {
        
        public ClearProgram(CommandData data) {
            super(data);
        }
        
        @Override
        public void run() throws Exception{
            if(getOptions().showHelp()){
                router.addProgramTextToEnd(this, ProgramHelper.helpGenerator(name, commandDesc, "", helpOption), null);
                return;
            }
            router.clearAndClose();
        }
    }
}
