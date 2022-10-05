/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mindblown.masterprompt;

import java.util.Comparator;

/** This class compares UserVariables based only on the names of the UserVariables. The case 
 * of the names are factored. For example, "myname" will be considered different than "MYNAME"
 *
 * @author beamj
 */
public class UserVariableNameComparator implements Comparator<UserVariable> {
    
    public UserVariableNameComparator(){
        
    }

    @Override
    public int compare(UserVariable o1, UserVariable o2) {
        return o1.getVariableName().compareTo(o2.getVariableName());
    }
    
}
