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

import java.lang.reflect.Method;

import controller.PropertyKeys;
import controller.Titles;

/**This command starts a web browser.
 * 
 * @author Antje Huber
 *
 */
public class CommandOpenBrowser extends Command {
    
    protected String url;
    
    public void execute() {
        try {
            String path = controller.getPropertiesUserData().getProperty(
                    PropertyKeys.defaultBrowser);
            
            if (path != null && !path.equals("")) {
                Runtime.getRuntime().exec(path + " " + url);
            }
            else if (System.getProperty("os.name").startsWith("Windows")) {
                Runtime.getRuntime().exec(
                        "rundll32 url.dll, FileProtocolHandler " + url);
            }
            else if (System.getProperty("os.name").startsWith("Mac OS")) {
                Class clazz = Class.forName("com.apple.eio.FileManager");
                Method openURL = clazz.getDeclaredMethod(
                        "openURL", new Class[] {String.class});
                openURL.invoke(null, new Object[] {url});
            }
            else {
                Runtime.getRuntime().exec("firefox " + url);
            }
            
        } catch (Exception exception) {
            controller.handleException(Titles.openBrowserException, exception);
        }
    }
}
