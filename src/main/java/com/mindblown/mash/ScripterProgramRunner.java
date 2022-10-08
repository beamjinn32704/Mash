/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.mash;

import java.awt.event.KeyEvent;

/**
 *
 * @author beamj
 */
public class ScripterProgramRunner extends ConsoleProgramRunner{
    
    public static final CommandNameHelper name = new CommandNameHelper("scripter");
    
    private static final String examples = "To launch Scripter:\nscripter";
    
    private static final String commandDesc = "This command launches Scripter, "
            + "which is a tool that allows you to create and publish your own"
            + " libraries and commands.";
    
    public ScripterProgramRunner(ProgramRouter router) {
        super(router);
    }

    @Override
    public ConsoleProgram genProgram(CommandData data) {
        return new ScripterProgram(data);
    }
    
    private class ScripterProgram extends ConsoleProgram {
        
        public ScripterProgram(CommandData options) {
            super(options);
        }
        
        private Compiler getScripterCompiler(){
        return new Compiler() {
            @Override
            public void compile(String command) {
                System.out.println("Recieved command \"" + command + "\"!");
                if(command.equals("exit")){
                    ConsoleWindow.staticConsole.getInteractor().restoreOrigController();
//                    router.addProgramTextToEnd(, "\n", null);//figure out a away to refernce the ScripterProgram
                    ConsoleWindow.staticConsole.getInteractor().newPrompt();
                }
            }

            @Override
            public void routeKeyEvent(KeyEvent evt) {
                
            }
        };
    }

        @Override
        public void run() throws Exception {
            if(getOptions().showHelp()){
                router.addProgramTextToEnd(this, ProgramHelper.helpGenerator(name,
                        commandDesc, examples, helpOption), null);
                return;
            }
            router.setProgramText(this, "Scripter\n-----------\n\n\n", null);
            /*
            Load Custom Libraries and Commands.
            Show Options
            Create a prompter
            */
            ColorText[] scripterPrompt = new ColorText[]{
               new ColorText(ConsoleTextEditor.greenColor, "Scripter~# ")
            };
            ConsoleWindow.staticConsole.getInteractor().newController(getScripterCompiler(), scripterPrompt);
        }
        
    }
    
}
