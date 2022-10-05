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
public class ClearHistProgramRunner extends ConsoleProgramRunner {
    
    public static CommandNameHelper name = new CommandNameHelper("clearhist");
    
    private static final String commandDesc = "This clears all past commands that you would be able to "
            + "retrieve and go through by pressing Ctrl + Up and Ctrl + Down.";
    
    public ClearHistProgramRunner(ProgramRouter router) {
        super(router);
    }

    @Override
    public ConsoleProgram genProgram(CommandData data) {
        return new ClearHistProgram(data);
    }
    
    private class ClearHistProgram extends ConsoleProgram {
        
        public ClearHistProgram(CommandData data) {
            super(data);
        }
        
        @Override
        public void run() throws Exception{
            if(getOptions().showHelp()){
                router.addProgramTextToEnd(this, ProgramHelper.helpGenerator(name, commandDesc, "", helpOption), null);
                return;
            }
            router.clearCommandHistory();
        }
        
    }
    
}
