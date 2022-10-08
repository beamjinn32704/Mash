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
public class Attr implements Cacheable<Attr>, Comparable<Object>{
    private String attrName;
    private String attrVal;

    public Attr(String attrName, String attrVal) {
        this.attrName = attrName;
        this.attrVal = attrVal;
    }

    public String getAttrVal() {
        return attrVal;
    }

    public String getAttrName() {
        return attrName;
    }
    
    public static Attr getAttr(String a){
        String tribHolder = a.trim();
        if(!tribHolder.startsWith("--") || tribHolder.startsWith("---")){
            return null;
        }
        tribHolder = tribHolder.substring(1);
        int spaceInd = tribHolder.indexOf(" ");
        if(spaceInd == -1){
            return null;
        }
        if(spaceInd != tribHolder.lastIndexOf(" ")){
            System.out.println("Error! Attr Finder found more than 1 value for attr.");
            return null;
        }
        String attrName = tribHolder.substring(0, spaceInd);
        tribHolder = tribHolder.substring(spaceInd+1);
        tribHolder = tribHolder.trim();
        String attrVal = tribHolder + "";
        return new Attr(attrName, attrVal);
    }

    @Override
    public String cache() {
        return "--" + attrName + " " + attrVal;
    }
    
    public static CacheScanner<Attr> getScan(){
        return new CacheScanner<Attr>() {
            
            private String text;
            private ArrayList<Attr> found = new ArrayList<>();
            private ArrayList<String> textDiscarded = new ArrayList<>();
            
            @Override
            public Attr next() {
                text = text.trim();
                int dashInd = text.indexOf("--");
                if(dashInd == -1){
                    textDiscarded.add(text + "");
                    text = "";
                    return null;
                }
                
                textDiscarded.add(text.substring(0, dashInd));
                boolean isAtFront = dashInd == 0;
                boolean isAtEnd = dashInd == text.length() - 2;
                
                if((!isAtFront && !Character.isWhitespace(text.charAt(dashInd - 1)))){
                    text = text.substring(dashInd+1);
                    return next();
                }
                
                if(isAtEnd){
                    text = "";
                    return null;
                }
                
                if(text.charAt(dashInd + 2) == '-' ||
                        Character.isWhitespace(text.charAt(dashInd + 1))){
                    text = text.substring(dashInd + 2);
                    return next();
                }
                
                int firstSpaceInd = text.indexOf(" ", dashInd + 2);
                if(firstSpaceInd == -1){
                    text = "";
                    return null;
                }
                String attrName = text.substring(dashInd+2, firstSpaceInd);
                int secondSpaceInd = text.indexOf(" ", firstSpaceInd + 1);
                if(secondSpaceInd == -1){
                    String attrVal = text.substring(firstSpaceInd+1).trim();
                    text = "";
                    return new Attr(attrName, attrVal);
                } else {
                    String attrVal = text.substring(firstSpaceInd+1,secondSpaceInd);
                    text = text.substring(secondSpaceInd+1).trim();
                    return new Attr(attrName, attrVal);
                }
            }

            @Override
            public void analyze() {
                while(true){
                    Attr attr = next();
                    if(attr == null){
                        return;
                    } else {
                        ArrayListUtil.sortedAdd(found, attr);
                    }
                }
            }

            @Override
            public ArrayList<Attr> scanned() {
                return found;
            }

            @Override
            public void setParams(Object[] p) {
                text = ((String) p[0]) + "";
            }

            public ArrayList<String> getTextDiscarded() {
                return textDiscarded;
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
    
    @Override
    public int compareTo(Object o) {
        if(o instanceof Attr){
            Attr a = (Attr)o;
            int nameRes = attrName.compareTo(a.attrName);
            if(nameRes != 0){
                return nameRes;
            }
            return attrVal.compareTo(a.attrVal);
        } else if(o instanceof String){
            String s = (String)o;
            return s.compareTo(attrName);
        } else {
            return toString().compareTo(o.toString());
        }
    }
}
