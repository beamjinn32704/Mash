/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.mash;

import com.mindblown.util.ArrayUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author beamj
 */
public class CommandData implements Cacheable<CommandData>{
    private String commandName;
    private Param[] params;
    private Attr[] attrs;
    private String[] plainParams;
    private String plainParamsSum;

    public CommandData(String commandName, Param[] params, Attr[] attrs, String plainParamsSum) {
        this.commandName = commandName;
        this.params = params;
        this.attrs = attrs;
        this.plainParamsSum = plainParamsSum;
        ArrayList<String> plainPar = new ArrayList<>();
        try(Scanner in = new Scanner(plainParamsSum)){
            while(in.hasNext()){
                plainPar.add(in.next());
            }
        }
        plainParams = new String[plainPar.size()];
        plainPar.toArray(plainParams);
        
        Arrays.sort(params);
        Arrays.sort(attrs);
    }
    

    public CommandData(String commandName, Param[] params, Attr[] attrs) {
        this.commandName = commandName;
        this.params = params;
        this.attrs = attrs;
        plainParams = ArrayUtil.toArray();
        
        Arrays.sort(params);
        Arrays.sort(attrs);
    }
    
    public CommandData(String commandName){
        this.commandName = commandName;
        params = ArrayUtil.toArray();
        attrs = ArrayUtil.toArray();
        plainParams = ArrayUtil.toArray();
    }
    
    public String getCommandName() {
        return commandName;
    }

    public Attr[] getAttrs() {
        return attrs;
    }

    public Param[] getParams() {
        return params;
    }

    public String[] getPlainParams() {
        return plainParams;
    }

    public String getPlainParamsSum() {
        return plainParamsSum;
    }
    
    public Attr getAttr(AttrHelper attrOption){
        return ArrayUtil.sortedGet(attrs, ProgramHelper.toArr(attrOption));
    }
    
    public boolean getParamVal(ParamsHelper paramOption){
        String[] paramOptions = ProgramHelper.toArr(paramOption);
        for (String param : paramOptions){
            if (ArrayUtil.sortedHas(params, param)){
                return true;
            }
        }
        return false;
    }
    
    public boolean showHelp(){
        return getParamVal(ConsoleProgramRunner.helpOption);
    }
    
    public static CommandData getCommandData(String c){
        String command = c.trim();
        if(command.isEmpty()){
            return null;
        }
        int spaceInd = command.indexOf(" ");
        if(spaceInd == -1){
            return new CommandData(command + "");
        }
        String commandName = command.substring(0, spaceInd);
        command = command.substring(spaceInd + 1);
        
        CacheScanner<Param> paramScanner = Param.getScan();
        ArrayList<Param> p = Cacheable.convert(paramScanner, command);
        
        ArrayList<String> pDiscardedText = (ArrayList<String>) paramScanner.debugData()[0];
        String pDiscardTotal = "";
        for(String str : pDiscardedText){
            pDiscardTotal += str;
        }
        
        CacheScanner<Attr> attrScanner = Attr.getScan();
        ArrayList<Attr> a = Cacheable.convert(attrScanner, pDiscardTotal);
        
        ArrayList<String> aDiscardedText = (ArrayList<String>) attrScanner.debugData()[0];
        
        String aDiscardTotal = "";
        for(String str : aDiscardedText){
            aDiscardTotal += str;
        }
        
        Param[] params = new Param[p.size()];
        Attr[] attrs = new Attr[a.size()];
        
        
        p.toArray(params);
        a.toArray(attrs);
        
        return new CommandData(commandName, params, attrs, aDiscardTotal);
    }

    @Override
    public String cache() {
        String ret = commandName + " ";
        for(int i = 0; i < plainParams.length; i++){
            ret += plainParams[i];
        }
        ret += " ";
        for(Param param : params){
            ret += param.cache();
        }
        ret += " ";
        for(Attr attr : attrs){
            ret += attr.cache();
        }
        ret = ret.trim();
        return ret;
    }

    @Override
    public String toString() {
        return cache();
    }
    
    public static CacheScanner<CommandData> getScan(){
        return new CacheScanner<CommandData>() {
            
            private String text;
            ArrayList<CommandData> commandDatas = new ArrayList<>();
            
            @Override
            public CommandData next() {
                CommandData d = getCommandData(text);
                text = "";
                return d;
            }

            @Override
            public void analyze() {
                CommandData d = next();
                if(d != null){
                    commandDatas.add(d);
                }
            }

            @Override
            public ArrayList<CommandData> scanned() {
                return commandDatas;
            }

            @Override
            public void setParams(Object[] p) {
                text = (String) p[0];
            }

            @Override
            public Object[] debugData() {
                return ArrayUtil.toArray();
            }
        };
    }
}
