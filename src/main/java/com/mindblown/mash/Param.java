/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mindblown.mash;

import com.mindblown.util.ArrayListUtil;
import com.mindblown.util.ArrayUtil;
import java.util.ArrayList;

/**
 *
 * @author beamj
 */
public class Param implements Cacheable<Param>, Comparable{
    private String paramName;

    public Param(String paramName) {
        this.paramName = paramName;
    }

    public String getParamName() {
        return paramName;
    }

    @Override
    public String cache() {
        return "-" + paramName;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Param){
            Param p = (Param)o;
            return paramName.compareTo(p.paramName);
        } else if(o instanceof String){
            String s = (String)o;
            return s.compareTo(paramName);
        } else {
            return toString().compareTo(o.toString());
        }
    }
    
    public static CacheScanner<Param> getScan(){
        
        return new CacheScanner<Param>() {
            private String text;
            private ArrayList<Param> found = new ArrayList<>();
            private ArrayList<String> textDiscarded = new ArrayList<>();
            
            @Override
            public Param next() {
                text = text.strip();
                int dashInd = text.indexOf("-");
                if(dashInd == -1){
                    textDiscarded.add(text + "");
                    text = "";
                    return null;
                }
                
                boolean isAtFront = dashInd == 0;
                boolean isAtEnd = dashInd == text.length() - 1;
                if((!isAtFront && !Character.isWhitespace(text.charAt(dashInd - 1)))){
                    textDiscarded.add(text.substring(0, dashInd));
                    text = text.substring(dashInd+1);
                    return next();
                }
                
                if(isAtEnd){
                    textDiscarded.add(text.substring(0, dashInd));
                    text = "";
                    return null;
                }
                
                if(text.charAt(dashInd + 1) == '-' ||
                        Character.isWhitespace(text.charAt(dashInd + 1))){
                    textDiscarded.add(text.substring(0, dashInd+2));
                    text = text.substring(dashInd + 2);
                    return next();
                }
                
                int firstSpaceInd = text.indexOf(" ", dashInd + 1);
                if(firstSpaceInd == -1){
                    String paramName = text.substring(dashInd+1);
                    
                    textDiscarded.add(text.substring(0, dashInd));
                    text = "";
                    return new Param(paramName);
                } else {
                    String paramName = text.substring(dashInd+1, firstSpaceInd);
                    text = text.substring(firstSpaceInd+1);
                    return new Param(paramName);
                }
            }

            @Override
            public void analyze() {
                while(true){
                    Param param = next();
                    if(param == null){
                        return;
                    } else {
                        ArrayListUtil.sortedAdd(found, param);
                    }
                }
            }

            @Override
            public ArrayList<Param> scanned() {
                return found;
            }

            @Override
            public void setParams(Object[] p) {
                text = ((String) p[0]) + "";
            }

            @Override
            public Object[] debugData() {
                return ArrayUtil.toArray(textDiscarded);
            }
        };
    }

    @Override
    public String toString() {
        return cache();
    }
}
