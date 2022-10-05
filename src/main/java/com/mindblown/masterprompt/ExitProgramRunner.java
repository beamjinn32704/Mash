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
public class ExitProgramRunner extends ConsoleProgramRunner {
    
    public static CommandNameHelper name = new CommandNameHelper("exit");
    
    private static final String commandDesc = "This command exits the program.";
    
    public ExitProgramRunner(ProgramRouter router) {
        super(router);
    }

    @Override
    public ConsoleProgram genProgram(CommandData data) {
        return new ExitProgram(data);
    }
    
    private class ExitProgram extends ConsoleProgram {
        
        public ExitProgram(CommandData data) {
            super(data);
        }

        @Override
        public void run() throws Exception{
            if(getOptions().showHelp()){
                router.addProgramTextToEnd(this, ProgramHelper.helpGenerator(name, commandDesc, "", helpOption), null);
                return;
            }
            System.exit(0);
        }
    }
}
