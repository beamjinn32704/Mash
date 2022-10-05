/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mindblown.mash;

import com.mindblown.util.ArrayUtil;
import java.io.File;

/**
 *
 * @author beamj
 */
public class CdProgramRunner extends ConsoleProgramRunner {
    
    public static CommandNameHelper name = new CommandNameHelper("cd");
    
    private static final String commmandDesc = "This changes the current directory you are in. You can go to a path relative to "
            + "your location, or you can go to an absolute path in your filesystem.";
    
    private static final PlainParamsHelper destinationOption = new PlainParamsHelper("destination", false,
            "This is the relative or absolute path to the folder you want to go to.");
    
    private static final String examples = "To go to folder relative folder MyFolder:"
            + "\ncd MyFolder\n\nTo go to absolute path C:\\Users\\:\ncd C:\\Users\\\n\n"
            + "To access the help menu:\ncd " + helpOption.toString();
    
    private FileSystemTraveller fileFollower;
    
    public CdProgramRunner(ProgramRouter router, FileSystemTraveller fileFollower) {
        super(router);
        this.fileFollower = fileFollower;
    }
    
    @Override
    public ConsoleProgram genProgram(CommandData data) {
        return new CdProgram(data);
    }
    
    private class CdProgram extends ConsoleProgram {
        
        public CdProgram(CommandData data) {
            super(data);
        }
        
        private class ProgramOptions {
            private File destination;
            
            public ProgramOptions(File destination) {
                this.destination = destination;
            }
        }
        
        public ProgramOptions genOptions() {
            CommandData d = getOptions();
            String[] plainParams = d.getPlainParams();
            File dest;
            if(plainParams.length > 0){
                String workWith = plainParams[0];
                if(workWith.equals("..")){
                    dest = fileFollower.getCurrentFile().getParentFile();
                    if(dest == null){
                        dest = fileFollower.getCurrentFile();
                    }
                } else {
                    dest = new File(fileFollower.getCurrentFile(), workWith);
                    if(!dest.exists()){
                        File tempCheck = new File(workWith);
                        if(tempCheck.exists()){
                            if(tempCheck.toString().equals(tempCheck.getAbsolutePath())){
                                dest = tempCheck;
                            }
                        }
                    }
                }
            } else {
                dest = null;
            }
            return new ProgramOptions(dest);
        }
        
        @Override
        public void run() throws Exception{
            ProgramOptions parameters = genOptions();
            File destFile = parameters.destination;
            if(getOptions().showHelp()){
                CommandOptionHelper[] optionsList = ArrayUtil.toArray(destinationOption, helpOption);
                router.addProgramTextToEnd(this, ProgramHelper.helpGenerator(name, commmandDesc, examples, optionsList), null);
                return;
            }
            if(destFile == null){
                router.addProgramTextToEnd(this, ProgramHelper.genGoToHelpMenuMessage("cd", "Invalid syntax!"));
                return;
            }
            
            if(!fileFollower.goToFile(destFile)){
                router.addProgramTextToEnd(this, new ColorText(ConsoleTextEditor.blueColor, "Invalid File Location!"));
            }
        }
    }
    
}
