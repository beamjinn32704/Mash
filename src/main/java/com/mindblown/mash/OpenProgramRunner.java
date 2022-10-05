/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mindblown.mash;

import com.mindblown.util.ArrayUtil;
import com.mindblown.util.FileUtil;
import com.mindblown.util.PCUtil;
import java.io.File;

/**
 *
 * @author beamj
 */
public class OpenProgramRunner extends ConsoleProgramRunner {
    
    public static CommandNameHelper name = new CommandNameHelper("open");
    
    private static final String commandDesc = "This opens up a file or folder. If the file selected is a folder, "
            + "it will open up the file explorer in that folder. If it's a file, this will run the file.";
    
    private static final String examples = "To open up file MyFile.txt:\n"
            + "open MyFile.txt";
    
    private static final PlainParamsHelper fileToOpenOption = new PlainParamsHelper("file_to_open", false, "This is a file or"
            + " folder that you want to open.");
    
    private FileSystemTraveller fileFollower;
    
    public OpenProgramRunner(ProgramRouter router, FileSystemTraveller fileFollower) {
        super(router);
        this.fileFollower = fileFollower;
    }

    @Override
    public ConsoleProgram genProgram(CommandData data) {
        return new OpenProgram(data);
    }
    
    private class OpenProgram extends ConsoleProgram {
        
        public OpenProgram(CommandData data) {
            super(data);
        }
        
        private class ProgramOptions {
            private File fileToOpen;
            
            public ProgramOptions(File fileToOpen) {
                this.fileToOpen = fileToOpen;
            }
        }
        
        public ProgramOptions genOptions() {
            CommandData d = getOptions();
            String[] plainParams = d.getPlainParams();
            File dest;
            if(plainParams.length > 0){
                String workWith = plainParams[0];
                dest = new File(workWith);
                if(!dest.exists()){
                    dest = new File(fileFollower.getCurrentFile(), workWith);
                }
            } else {
                dest = null;
            }
            return new ProgramOptions(dest);
        }
        
        @Override
        public void run() throws Exception{
            ProgramOptions parameters = genOptions();
            File fileToOpen = parameters.fileToOpen;
            
            if(getOptions().showHelp()){
                CommandOptionHelper[] commandOptions = ArrayUtil.toArray(fileToOpenOption, helpOption);
                router.addProgramTextToEnd(this, ProgramHelper.helpGenerator(name, commandDesc, examples, commandOptions), null);
                return;
            }
            
            if(fileToOpen == null){
                router.addProgramTextToEnd(this, ProgramHelper.genGoToHelpMenuMessage(name.getCommandName(), "Invalid Syntax!"));
                return;
            }
            
            if(fileToOpen.exists()){
                if(fileToOpen.isFile()){
                    if(!PCUtil.openFile(fileToOpen)){
                        router.addProgramTextToEnd(this, "\nError! File exists...Try again or report this to developers.", null);
                    }
                } else {
                    if(!PCUtil.openDir(fileToOpen)){
                        router.addProgramTextToEnd(this, "\nError! Folder exists...Try again or report this to developers.", null);
                    }
                }
            } else {
                router.addProgramTextToEnd(this, fileToOpen + " doesn't exist!", null);
            }
        }
    }
}
