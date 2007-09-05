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
import java.net.URI;
import java.net.URISyntaxException;

import controller.EMail;
import controller.PropertyKeys;
import controller.Titles;

/**This command creates an URI and opens an e-mail program for sending a
 * document as an e-mail.
 * 
 * @author Antje Huber
 *
 */
public class CommandSendAsEMail extends Command {
    
    private EMail email;
    
    public CommandSendAsEMail(EMail email) {
        this.email = email;
    }

    public void execute() {
        String recipient = new String();
        String subject = new String();
        String text = new String();
        
        String uriRecipient = new String();
        String uriSubject = new String();
        String uriText = new String();
        
        if (email != null) {
            recipient = email.getRecipient();
            subject = email.getSubject();
            text = email.getText();
        }
        
        uriRecipient = getStringURI(recipient, Titles.recipientEMailException);
        uriSubject = getStringURI(subject, Titles.subjectEMailException);
        uriText = getStringURI(text, Titles.textEMailException);
        
        String uri = "mailto:" + uriRecipient + "?subject=" + uriSubject
            + "&body=" + uriText;
        
        String path = controller.getPropertiesUserData().getProperty(
                PropertyKeys.defaultEMailClient);
        
        try {
            if (path != null && !path.equals("")) {
                Runtime.getRuntime().exec(path + " " + uri);
            }
            else if (System.getProperty("os.name").startsWith("Windows")) {
                Runtime.getRuntime().exec(
                    "rundll32 url.dll, FileProtocolHandler " + uri);
            }
            else if (System.getProperty("os.name").startsWith("Mac OS")) {
                Class clazz = Class.forName("com.apple.eio.FileManager");
                Method openURL = clazz.getDeclaredMethod(
                        "openURL", new Class[] {String.class});
                openURL.invoke(null, new Object[] {uri});
            }
            else {
                Runtime.getRuntime().exec("thunderbird " + uri);
            }
        } catch (Exception e) {
            controller.handleException(Titles.openEMailClientException, e);
        }
    }
    
    private String getStringURI(String string, String titleException) {
        if (string.length() > 0) {
            try {
                return new URI("mailto", string, null).toString()
                        .substring(7).replace("&", "%26");
            } catch (URISyntaxException e) {
                controller.handleException(titleException, e);
                return "";
            }
        }
        else {
            return "";
        }
    }
}
