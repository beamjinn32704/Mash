/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mindblown.mash;
import com.mindblown.mash.ConsoleProgramRunner.ConsoleProgram;
import com.mindblown.util.ArrayListUtil;
import com.mindblown.util.ArrayUtil;
import com.mindblown.util.FileUtil;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author beamj
 */
public class ConsoleCompiler implements Compiler {
    
    private static File longTermVarContainer = new File("vars").getAbsoluteFile();
    
    private static final String varFileExtension = ".compvar";
    
    private ProgramRouter router;
    private FileSystemTraveller fileFollower;
    private ConsoleProgramRunner[] programArr;
    private ArrayList<ConsoleProgramRunner> programList = new ArrayList<>();
    
    public static ArrayList<UserVariable> variables = new ArrayList<>();
    
    //A comparator used to compare uservariables based only on their names (considers upper and lower case)
    private static UserVariableNameComparator uvNameComparator = new UserVariableNameComparator();
    
    public ConsoleCompiler(ProgramRouter router, FileSystemTraveller fileFollower) {
        this.router = router;
        this.fileFollower = fileFollower;
        initProgramList();
        retrieveLongTermVars();
    }
    
    private void initProgramList(){
        programArr = new ConsoleProgramRunner[]{
            new CdProgramRunner(router, fileFollower), new DemoProgramRunner(router),
            new ClearProgramRunner(router), new ExitProgramRunner(router),
            new ClearHistProgramRunner(router), new OpenProgramRunner(router, fileFollower),
            new ListProgramRunner(router, fileFollower), new SetProgramRunner(router),
            new UnSetProgramRunner(router), new ScripterProgramRunner(router)
        };
        programList = ArrayUtil.toList(programArr);
        Collections.sort(programList);
    }
    
    public static void addVariable(UserVariable var){
        //Use the variable to add and a name comparator to see if there are any other variables in the variables list
        //with the same name as the variable (var)
        int existingVarInd = ArrayListUtil.sortedIndexOf(variables, var, uvNameComparator);
        if(existingVarInd < 0){
            ArrayListUtil.sortedAdd(variables, var);
        } else {
            variables.set(existingVarInd, var);
        }
        if(var.isLongterm()){
            File varFile = new File(longTermVarContainer, var.getVariableName() + varFileExtension);
            Cacheable.cache(var, varFile);
        }
    }
    
    public static void removeAllVariables(){
        while(!variables.isEmpty()){
            removeVariable(variables.get(0).getVariableName());
        }
    }
    
    public static void removeVariable(String varName){
        //Removes the variable from the variables list by creating a new variable with the variable-to-remove's name 
        //and a comparator that makes user variables equal to each other if they have the same name
        UserVariable varRemoved = ArrayListUtil.sortedRemove(variables, new UserVariable(varName, "", false), uvNameComparator);
        //If the variable exists (if a variable was removed) and if the variable is a long term variable 
        //(i.e. stored in the file system), then delete the file containing that variable's information
        if(varRemoved != null && varRemoved.isLongterm()){
            FileUtil.delete(new File(longTermVarContainer, varRemoved.getVariableName() + varFileExtension));
        }
    }
    
    private void retrieveLongTermVars(){
        longTermVarContainer.mkdirs();
        CacheScanner<UserVariable> scanner = UserVariable.getScan();
        ArrayList<File> varFiles = FileUtil.getFilesOfType(longTermVarContainer, false, FileUtil.filesOnlyFilter);
        for(File varFile : varFiles){
            scanner.setParams(ArrayUtil.toArray(FileUtil.getText(varFile)));
            UserVariable var = scanner.next();
            if(var != null){
                addVariable(var);
            }
        }
    }
    
    private String substituteVars(String com){
        String command = com + "";
        String suffix = "~";
        command = command.replace(SetProgramRunner.varIdentifier + "" + SetProgramRunner.varIdentifier,
                SetProgramRunner.varIdentifier + SetProgramRunner.varIdentifier + suffix);
        for(UserVariable var : variables){
            command = command.replace(SetProgramRunner.varIdentifier + var.getVariableName()
                    + SetProgramRunner.varIdentifier, var.getVariableValue());
        }
        command = command.replace(SetProgramRunner.varIdentifier + SetProgramRunner.varIdentifier + suffix,
                SetProgramRunner.varIdentifier + "");
        return command;
    }
    
    @Override
    public void compile(String com){
        // Variable is represented by |var_name|
        // Arithmetic signs are represented by |Artithmetic_sign|
        //for-loop --start 1 --end 100 --change |+|2
        
        String command = substituteVars(com + "");
        CommandData data = CommandData.getCommandData(command);
        if(data == null){
            return;
        }
        
        System.out.println("Data Recieved: \nCommand Name: " + data.getCommandName() +
                "\nCommand Attrs: " + Arrays.toString(data.getAttrs()) +
                "\nCommand Params: " + Arrays.toString(data.getParams()) +
                "\nCommand Plain Params: " + Arrays.toString(data.getPlainParams()));
        String commandName = data.getCommandName().toLowerCase();
        
        try {
            int index = ArrayListUtil.sortedIndexOf(programList, commandName);
            if(index >= 0){
                ConsoleProgramRunner programRunner = programList.get(index);
                ConsoleProgram program = programRunner.genProgram(data);
                router.open(program);
                program.run();
            } else {
                router.open(null);
                router.addProgramTextToEnd(null, new ColorText(ConsoleTextEditor.blueColor, "\nInvalid command!"));
            }
        } catch (Exception e){
            System.out.println("ERROR! Compile Function caught an error probably by the router!");
            e.printStackTrace();
        } finally {
            router.close();
        }
        
    }
    
    @Override
    public void routeKeyEvent(KeyEvent evt) {
        int keyCode = evt.getKeyCode();
        if(keyCode == KeyEvent.VK_X && evt.isControlDown() && evt.isShiftDown()){
            router.close();
            return;
        }
        try {
            router.routeKeyEvent(evt);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}