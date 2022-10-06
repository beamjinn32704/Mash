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
public class DemoProgramRunner extends ConsoleProgramRunner {
    
    public static CommandNameHelper name = new CommandNameHelper("demo");
    
    public DemoProgramRunner(ProgramRouter router) {
        super(router);
    }

    @Override
    public ConsoleProgram genProgram(CommandData data) {
        return new DemoProgram(data);
    }
    
    private class DemoProgram extends ConsoleProgram {
        
        public DemoProgram(CommandData data) {
            super(data);
        }
        
        @Override
        public void run() throws Exception {
            router.addProgramTextToEnd(this, new ColorText(ConsoleTextEditor.greenColor, "---------DEMO PROGRAM---------\n"));
            for(int i = 0; i < 1000; i++){
                long now = System.currentTimeMillis();
                while(System.currentTimeMillis() - now < 500){
                    
                }
                System.out.println(now);
                router.addProgramTextToEnd(this, new ColorText(ConsoleTextEditor.blueColor, "Running at " + now + " miliseconds!\n"));
            }
        }
    }
}
