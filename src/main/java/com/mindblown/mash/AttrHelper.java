/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mindblown.mash;

/**
 *
 * @author beamj
 */
public class AttrHelper extends CommandOptionHelper{
    public AttrHelper(String optionName, boolean optional, String description, String... alternativeOptionNames) {
        super(optionName, "--", "[", " [val]]", optional, description, alternativeOptionNames);
    }
}