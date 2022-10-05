/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.mash;

import java.io.File;

/**
 * Objects of this class are like vehicles that travel around your computer's file system. They start in one folder, 
 * can go to the parent folder of the current folder, can go to a different folder. Objects of this class 
 * are more locators/references instead of actually doing things. (They can be used by other programs to travel 
 * through the file system).
 * @author beamj
 */
public class FileSystemTraveller implements Variable {
    
    //This is which folder the traveller is currently in
    private File currentFolder;

    /**
     * Instantiates a FileSystemTraveller object. It sets the folder where the traveller is located at to the parameter File 
     * the user provides. If the File the user provides isn't a folder, the traveller will instead be located at the 
     * File's parent folder.
     * @param currentFile the file the traveller is to be located. The function will use the file's parent folder if 
     * the file isn't a folder.
     */
    public FileSystemTraveller(File currentFile) {
        //Goes to the File
        goToFile(currentFile);
    }

    /**
     * Instantiates a FileSystemTraveller object. It sets the current folder where the file system traveller is located 
     * to the folder in the computer's file system where the program is running.
     */
    public FileSystemTraveller() {
        //The "System.getProperty("user.dir")" is a string containing the path of the folder the program is running in
        goToFile(new File(System.getProperty("user.dir")));
    }
    
    /**
     * The file system traveller becomes located at the current folder (the folder the traveller is located)'s parent folder 
     * (so the folder containing the current folder). If the current folder does not have a parent folder
     * (this could happen if the current folder is a drive like the C: drive), then the traveller will not 
     * change its foder location. 
     * @return whether the file system traveller changed its location (it wouldn't if the current file doesn't have a 
     * parent folder)
     */
    public boolean goToParent(){
        File parent = currentFolder.getParentFile();
        if(parent != null){
            //Make sure the current folder has a parent to go to. If it does, go there and return "true", saying that the 
            //traveller did go there
            currentFolder = parent;
            return true;
        }
        //Rwturn false if the parent folder was null (if the current folder doesn't have a parent file)
        return false;
    }
    
    /**
     * Sets the folder location of the file system traveller to the file parameter given when calling this function. 
     * The file passed to this function must exist (in the computer's file system). If the file exists
     * and isn't a folder, the file system traveller will set its location to the file's parent
     * folder (the folder containing the file passed to the function).
     * @param file the file that the file traveller object is to be located at
     * @return whether the file traveller changed its file location. Whether the file given is a file or a folder 
     * doesn't influence this.
     */
    public boolean goToFile(File file){
        //If the file is null, then just return false.
        if(file == null){
            return false;
        }
        //Make sure the file exists in the file system
        if(file.exists()){
            if(file.isFile()){
                //Set the current folder to the File's parent folder if the File itself isn't a folder
                currentFolder = file.getParentFile();
            } else {
                currentFolder = file;
            }
            return true;
        } else {
            //Return false if the File doesn't exist in the file system
            return false;
        }
    }
    
    /**
     * Returns the folder the file system traveller object is currently located at.
     * @return the folder the file system traveller is currently located at.
     */
    public File getCurrentFile() {
        return currentFolder;
    }

    /**
     * This returns the string format of this object. The string format is the path of the folder the file traveller
     * is located at.
     * @return the path of the folder the traveller is located at.
     */
    @Override
    public String getString() {
        //Returns the string version of the current folder
        return currentFolder.toString();
    }
}