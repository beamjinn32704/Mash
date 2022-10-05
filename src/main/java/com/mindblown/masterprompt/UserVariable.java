/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mindblown.masterprompt;

import com.mindblown.util.ArrayUtil;
import java.util.ArrayList;

/**
 *
 * @author beamj
 */
public class UserVariable implements Comparable<UserVariable>, Cacheable<UserVariable>{
    private String variableName;
    private String variableValue;
    private boolean longterm;
    
    public UserVariable(String variableName, String variableValue, boolean longterm) {
        this.variableName = variableName;
        this.variableValue = variableValue;
        this.longterm = longterm;
    }
    
    public String getVariableName() {
        return variableName;
    }
    
    public String getVariableValue() {
        return variableValue;
    }
    
    public boolean isLongterm() {
        return longterm;
    }
    
    @Override
    public int compareTo(UserVariable uv) {
        int nameRes = variableName.compareTo(uv.variableName);
        if (nameRes != 0) {
            return nameRes;
        }
        return variableValue.compareTo(uv.variableValue);
    }

    @Override
    public String cache() {
        return longterm + "\n" + variableName + "\n" + variableValue;
    }
    
    public static CacheScanner<UserVariable> getScan(){
        return new CacheScanner<UserVariable>() {
            
            private String str;
            private ArrayList<UserVariable> scanned = new ArrayList<>();
            
            @Override
            public UserVariable next() {
                int firstLine = str.indexOf("\n");
                if(firstLine == -1){
                    return null;
                }
                
                int secondLine = str.indexOf("\n", firstLine + 1);
                if(secondLine == -1){
                    return null;
                }
                
                boolean isLongTerm = Boolean.parseBoolean(str.substring(0, firstLine));
                String name = str.substring(firstLine + 1, secondLine);
                String val = str.substring(secondLine + 1);
                return new UserVariable(name, val, isLongTerm);
            }
            
            @Override
            public void analyze() {
                UserVariable var = next();
                if(var == null){
                    return;
                }
                scanned.add(var);
            }
            
            @Override
            public ArrayList<UserVariable> scanned() {
                return scanned;
            }
            
            @Override
            public void setParams(Object[] p) {
                str = (String)p[0];
            }
            
            @Override
            public Object[] debugData() {
                return ArrayUtil.toArray();
            }
        };
    }
}
