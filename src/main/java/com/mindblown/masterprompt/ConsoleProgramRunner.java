/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mindblown.masterprompt;

import com.mindblown.util.ArrayUtil;
import java.awt.event.KeyEvent;

/**
 *
 * @author beamj
 */
public class ConsoleProgramRunner implements Comparable<Object>{
    
    public static CommandNameHelper name = new CommandNameHelper("");
    
    public static final ParamsHelper helpOption = new ParamsHelper("help", true, "Instead of running the command's function,"
            + " this displays the help menu.");
    
    protected ProgramRouter router;
    
    public ConsoleProgramRunner(ProgramRouter router) {
        this.router = router;
    }
    
    public ConsoleProgram genProgram(CommandData data){
        return new ConsoleProgram(data);
    }
    
    @Override
    public int compareTo(Object o) {
        if(o instanceof ConsoleProgramRunner){
            Class<? extends ConsoleProgramRunner> oClass = o.getClass().asSubclass(ConsoleProgramRunner.class);
            try {
                CommandNameHelper helper = ((CommandNameHelper) oClass.getDeclaredField("name").get(null));
                return ((CommandNameHelper)this.getClass().getDeclaredField("name").get(null)).getCommandName().compareTo(helper.getCommandName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if(o instanceof String){
            try {
                CommandNameHelper thisHelper = (CommandNameHelper)this.getClass().getDeclaredField("name").get(null);
                String oString = (String)o;
                //Used to be this:
//                if(Util.get(ProgramHelper.toArr(thisHelper), o) != null){
//                    return 0;
//                } else {
//                    return thisHelper.getCommandName().compareTo(oString);
//                }
                //I think what I have now is equivalent, but I could be wrong. Keeping the old version
                //just in case
                if(ArrayUtil.has(ProgramHelper.toArr(thisHelper), o)){
                    return 0;
                } else {
                    return thisHelper.getCommandName().compareTo(oString);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        int h1 = o.hashCode();
        int h2 = name.getCommandName().hashCode();
        return new Integer(h1).compareTo(h2);
    }
    
    public class ConsoleProgram {
        
        private final CommandData options;
        
        public ConsoleProgram(CommandData options) {
            this.options = options;
        }
        
        public void run() throws Exception{
            
        }
        
        public CommandData getOptions() {
            return options;
        }
        
        public void keyPressed(KeyEvent evt){
            int keyCode = evt.getKeyCode();
            if(keyCode == KeyEvent.VK_X && evt.isControlDown()){
                router.close();
            }
        }
    }
}
