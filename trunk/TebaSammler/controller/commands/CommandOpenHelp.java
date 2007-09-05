/*
 * TebaSa is a software for creating letters in foreign languages
 * on the basis of text modules.
 * 
 * Copyright (C) 2007  Antje Huber
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses/.
 */


package controller.commands;

import java.io.File;
import java.net.MalformedURLException;

import controller.Controller;
import controller.PropertyKeys;

/**This command opens the help in a web browser.
 *  
 * @author Antje Huber
 * 
 */
public class CommandOpenHelp extends CommandOpenBrowser {
    
    public void execute() {
        File file = new File(System.getProperty("user.dir"),
                Controller.getProperties().getProperty(PropertyKeys.dirHelp)); 
        
        try {
            url = file.toURI().toURL().toString();
        } catch (MalformedURLException e) {
            controller.handleException(e.toString(), e);
        }
        
        super.execute();
    }
}
