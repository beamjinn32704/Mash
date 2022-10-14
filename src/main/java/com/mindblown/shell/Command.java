/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.shell;

import com.mindblown.util.ArrayUtil;
import java.util.ArrayList;

/**
 * Objects of this class contain the different parts of a command the user types
 * into a ConsoleWindow text area.
 *
 * @author beamj
 * @see ConsoleWindow
 */
public class Command {

    final private String commandName;
    final private ArrayList<PlainParam> plainParams = new ArrayList<>();
    final private ArrayList<Param> params = new ArrayList<>();
    final private ArrayList<Attribute> attributes = new ArrayList<>();

    /**
     * Creates a Command object by going through and processing the String
     * version of a command the user gives to a ConsolePrompt window.
     *
     * @param command a command. A command has this format: command_name
     * plain_param -param_name param_value --attr_name Plain params are normal
     * arguments that are given to a command. For example, in the command "cd
     * ../MyFolder", "../MyFolder" is a plain param. Plain params are generally
     * the main and most important parameter. Parameters (these are in the form
     * "-name value") allow the user to give information for a specific part of
     * the command. An example for, hypothetically, a command that runs a timer,
     * could be how many times the clock repeats. This might be written in the
     * form "timer -repeat 2" (in this example the timer would repeat twice)
     * Attributes turn on options for the command's functionality. Attributes
     * strictly control boolean-valued options. Attributes could,
     * hypothetically, govern whether a timer command repeats, but could not
     * determine how many times the clock repeats (that argument should be a
     * plain param or parameter).
     * @see ConsoleWindow
     */
    public Command(String command) {
        
        //Create an ArrayList of all the "words" in the command String.
        //A word is basically a set of chars that don't include any spaces/whitespace.
        ArrayList<String> words = ArrayUtil.toList(command.strip().split(" "));
        
        //Gets rid of all the words in the words ArrayList that are empty or just whitespace
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i).strip();
            if (word.isBlank()) {
                words.remove(i);
                i--;
            } else {
                //Strips all the words in the words list. It strips it by setting the word at index i to the 
                //word String, which was stripped earlier in this for loop
                words.set(i, word);
            }
        }
        
        
        if(!words.isEmpty()){
            //If there where chars that weren't whitespace in the command String, then set the name of the command
            //to the first word in the words list.
            commandName = words.get(0);
        } else {
            //If the command String contained only whitespace, then just exit out of the function
            commandName = "";
            return;
        }
        
        //Go through all the words. Start at i=1 because the word at the first index was already assigned as the command name
        for (int i = 1; i < words.size(); i++) {
            String word = words.get(i);
            if (word.startsWith("--")) {
                if (word.length() > 2) {
                    //If the word starts with "--" and there's something after it, it's an Attribute
                    attributes.add(new Attribute(word.substring(2)));
                } else {
                    //If the word is just "--", then treat it as a PlainParam
                    plainParams.add(new PlainParam(word));
                }
            } else if (word.startsWith("-")) {
                if (word.length() > 1) {
                    //If the word starts with "-" and has something after that
                    if(i != words.size() - 1){
                        //Is Param
                        //If there is a word after this word in the words list, then set the current word as the Param's 
                        //name and set the word after this word as the Param's value
                        params.add(new Param(word, words.get(i+1)));
                        i++;
                    } else {
                        //If there's no word after this word in the words list, then just treat the word as a PlainParam instead 
                        //of as a Param
                        plainParams.add(new PlainParam(word));
                    }
                } else {
                    //If the word is just "-", treat it as a PlainParam
                    plainParams.add(new PlainParam(word));
                }
            } else {
                //If the word doesn't start with any "-", then it's a PlainParam
                plainParams.add(new PlainParam(word));
            }
        }
    }
    
    /**
     * Returns the name of the command this Command object represents
     * @return the name of the command
     * 
     * @see Command
     * @see #Command(java.lang.String) 
     */
    public String getCommandName() {
        return commandName;
    }
    
    /**
     * Returns the PlainParams in the command this Command object represents.
     * @return the PlainParams in the command
     * 
     * @see Command
     * @see #Command(java.lang.String) 
     */
    public ArrayList<PlainParam> getPlainParams() {
        return plainParams;
    }
    
    /**
     * Returns the Params in the command this Command object represents.
     * @return the Params in the command
     * 
     * @see Command
     * @see #Command(java.lang.String) 
     */
    public ArrayList<Param> getParams() {
        return params;
    }
    
    /**
     * Returns the Attributes in the command this Command object represents.
     * @return the Attributes in the command
     * 
     * @see Command
     * @see #Command(java.lang.String) 
     */
    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    /**
     * Plain Params are generally the main and most important parameter
     *
     * @author beamj
     */
    public class PlainParam {

        final private String argument;

        /**
         * Creates a PlainParam object that holds the String value passed to
         * this constructor
         *
         * @param arg the String value that the PlainParam object will hold
         */
        public PlainParam(String arg) {
            argument = arg;
        }

        /**
         * Returns the argument String that PlainParam holds
         *
         * @return returns the argument String that PlainParam holds
         *
         * @see Command
         */
        public String getArgument() {
            return argument;
        }
    }

    /**
     * Parameters (these are in the form "-name value") allow the user to give
     * information for a specific part of the command. An example for,
     * hypothetically, a command that runs a timer, could be how many times the
     * clock repeats. This might be written in the form "timer -repeat 2" (in
     * this example the timer would repeat twice).
     *
     * @author beamj
     */
    public class Param {

        final private String name;
        final private String value;

        /**
         * Instantiates a Param object.
         *
         * @param name the category that the value String falls into
         * @param value the argument given by the user. It falls under the
         * category described by the name String
         *
         */
        public Param(String name, String value) {
            this.name = name;
            this.value = value;
        }

        /**
         * Returns the name String
         *
         * @return the name String
         *
         * @see #Param(java.lang.String, java.lang.String)
         * @see Param
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the value String
         *
         * @return the value String
         *
         * @see #Param(java.lang.String, java.lang.String)
         * @see Param
         */
        public String getValue() {
            return value;
        }
    }

    /**
     * Attributes turn on options for the command's functionality. Attributes
     * strictly control boolean-valued options. Attributes could,
     * hypothetically, govern whether a timer command repeats, but could not
     * determine how many times the clock repeats (that argument should be a
     * plain param or parameter).
     *
     * @author beamj
     */
    public class Attribute {

        final private String name;

        /**
         * Creates a new Attribute object.
         *
         * @param name the category the Attribute falls into. In other words,
         * the option that the Attribute object is turning on.
         *
         * @see Attribute
         */
        public Attribute(String name) {
            this.name = name;
        }

        /**
         * Returns the name of the option that this Attribute turns on
         *
         * @return the name of the option that this Attribute turns on
         *
         * @see Attribute
         */
        public String getName() {
            return name;
        }
    }
}
