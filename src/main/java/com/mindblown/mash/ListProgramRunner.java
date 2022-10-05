/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mindblown.mash;

import com.mindblown.util.ArrayUtil;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

/**
 *
 * @author beamj
 */
public class ListProgramRunner extends ConsoleProgramRunner{
    
    private FileSystemTraveller fileFollower;
    
    public static CommandNameHelper name = new CommandNameHelper("list", "lst", "ls");
    
    private static final String commandDesc = "This command lists the contents of a directory.";
    
    private static final PlainParamsHelper dirToListOption = new PlainParamsHelper("dir_to_list", true, "The value you specify"
            + " is the relative name of or absolute path to the directory you want to list."
            + " If a file is put, the folder listed will be the file's parent."
            + " The default is the current directory.");
    
    private static final ParamsHelper filesOnlyOption = new ParamsHelper("filesonly", true, "This will turn on the list-files-only value."
            + " When turned on, the list command will only list files instead of directories. The default is off.", "fo");
    
    private static final AttrHelper subLevelNumOption = new AttrHelper("sublevelnum", true, "list command will list. Ex: If the value is set to 1, any folders "
            + "in the folder will also have their contents listed. The default is 0", "sln", "subnum");
    
    private static final ParamsHelper hideParentOption = new ParamsHelper("hideparents", true, "This is only used when sublevelnum is at least 1. "
            + "By default, sublevelnum will also show the parents of the sub-files and folders found. If you only want to see the names of the files "
            + "and folders, use this option.", "hp");
    
    private static String examples = "An example using standard listing commands (with all attributes beside help):\n"
            + "list MyFolder " + helpOption.toString() + " " + subLevelNumOption.toString() + " 1 " + hideParentOption.toString() + "\n"
            + "This will list the files of MyFolder (relative to current folder location)"
            + " and will also list all files of 1 extra sublevel without showing their parents.\n\n"
            + "Using the help function:\n"
            + "list " + helpOption.toString();
    
    public ListProgramRunner(ProgramRouter router, FileSystemTraveller fileFollower) {
        super(router);
        this.fileFollower = fileFollower;
    }

    @Override
    public ConsoleProgram genProgram(CommandData data) {
        return new ListProgram(data);
    }
    
    private class ListProgram extends ConsoleProgram {
        
        public ListProgram(CommandData data) {
            super(data);
        }
        
        @Override
        public void run() throws Exception{
            ProgramData options = genData();
            System.out.println(options);
            
            if(options.showHelp){
                CommandOptionHelper[] optionsList = ArrayUtil.toArray(dirToListOption, filesOnlyOption, helpOption, subLevelNumOption, hideParentOption);
                router.addProgramTextToEnd(this, ProgramHelper.helpGenerator(name, commandDesc, examples, optionsList), null);
                return;
            }
            if(options.dirToList == null){
                router.addProgramTextToEnd(this, "Invalid folder chosen! Go to command \'list -help\' for syntax help.", ConsoleTextEditor.redColor);
                return;
            }
            
            if(!options.dirToList.exists()){
                router.addProgramTextToEnd(this, options.dirToList + " doesn't exist!", null);
                return;
            }
            
            if(options.dirToList.isFile()){
                options.dirToList = options.dirToList.getParentFile();
            }
            
            int sublayersToList;
            try {
                sublayersToList = Integer.parseInt(options.layersOfSubListing);
            } catch (Exception e){
                router.addProgramTextToEnd(this, ProgramHelper.genGoToHelpMenuMessage("list", options.layersOfSubListing + " isn't a valid number!"));
                return;
            }
            String list = listDir(options.dirToList, options.listFilesOnly, 0);
            ArrayList<File> dirsToSubList = ArrayUtil.toList(options.dirToList.listFiles(File::isDirectory));
            ArrayList<File> dirsToSubListFoundHolder = new ArrayList<>();
            
            for(int i = 0; i < sublayersToList; i++){
                for(int j = 0; j < dirsToSubList.size(); j++){
                    File subDir = dirsToSubList.get(j);
                    dirsToSubListFoundHolder.addAll(ArrayUtil.toList(subDir.listFiles(File::isDirectory)));
                    int maxParentsToShow = i+1;
                    if(options.hideParents){
                        maxParentsToShow = 0;
                    }
                    String listing = listDir(subDir, options.listFilesOnly, maxParentsToShow);
                    if(!listing.isBlank()){
                        if(!list.isBlank()){
                            list += "\n";
                        }
                        list += listing;
                    }
                }
                dirsToSubList = new ArrayList<>();
                dirsToSubList.addAll(dirsToSubListFoundHolder);
                dirsToSubListFoundHolder = new ArrayList<>();
            }
            
            if(list.isBlank()){
                list = "Empty List!";
            }
            
            router.addProgramTextToEnd(this, "Listing of " + options.dirToList.getName() + ":\n\n" + list, ConsoleTextEditor.blueColor);
        }
        
        private String listDir(File dir, boolean listFilesOnly, int maxNumParentsShown){
            FileFilter filter = (File f) -> {
                if(listFilesOnly){
                    return f.isFile();
                }
                return true;
            };
            
            if(!dir.exists()){
                System.out.println("Error! File " + dir + " doesn't exist!");
                return "";
            }
            File[] dirContents = dir.listFiles(filter);
            
            String list = "";
            
            for(File f : dirContents){
                String parents = "";
                File currentParent = f.getParentFile();
                for(int i = 0; i < maxNumParentsShown; i++){
                    if(currentParent == null){
                        i = maxNumParentsShown;
                    } else {
                        parents = currentParent.getName() + File.separatorChar + parents;
                        currentParent = currentParent.getParentFile();
                    }
                }
                list += parents + f.getName() + "\n";
            }
            return list.strip();
        }
        
        private ProgramData genData(){
            CommandData ops = getOptions();
            String[] plainParams = ops.getPlainParams();
            
            File dirToList;
            boolean filesOnly = ops.getParamVal(filesOnlyOption);
            boolean showHelp = ops.getParamVal(helpOption);
            boolean hideParents = ops.getParamVal(hideParentOption);
            String subListNum = "0";
            if(plainParams.length > 0){
                dirToList = new File(plainParams[0]);
                if(!dirToList.exists()){
                    dirToList = new File(fileFollower.getCurrentFile(), plainParams[0]);
                }
            } else {
                dirToList = fileFollower.getCurrentFile();
            }
            
            Object o = ops.getAttr(subLevelNumOption);
            if(o == null){
                
            } else {
                subListNum = ((Attr)o).getAttrVal();
            }
            
            return new ProgramData(dirToList, subListNum, filesOnly, showHelp, hideParents);
        }
        
        private class ProgramData {
            private File dirToList;
            private String layersOfSubListing;
            private boolean listFilesOnly;
            private boolean showHelp;
            private boolean hideParents;
            
            public ProgramData(File dirToList, String layersOfSubListing, boolean listFilesOnly, boolean showHelp, boolean hideParents) {
                this.dirToList = dirToList;
                this.layersOfSubListing = layersOfSubListing;
                this.listFilesOnly = listFilesOnly;
                this.showHelp = showHelp;
                this.hideParents = hideParents;
            }
        }
        
    }
}
