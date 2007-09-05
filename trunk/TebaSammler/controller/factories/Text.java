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


package controller.factories;

import javax.swing.ComboBoxModel;

import model.items.ItemAddress;
import model.items.ItemTextModule;
import controller.Titles;

/**This is an abstract class for displayed forms of text. It provides
 * methods for sub-classes which return Strings with text parts.
 * 
 * @author Antje Huber
 *
 */
public abstract class Text {

    protected ComboBoxModel ctmModel;
    protected ItemAddress addresser;
    protected boolean isTextGerman;
    protected String lineSeparator;
    
    protected boolean afterSalutation;
    protected StringBuffer text;
    
    public Text(ComboBoxModel ctmModel, ItemAddress addresser,
            boolean isTextGerman, String lineSeparator) {
        this.addresser = addresser;
        this.lineSeparator = lineSeparator;
        this.isTextGerman = isTextGerman;
        this.ctmModel = ctmModel;
        
        afterSalutation = false;
        
        if(lineSeparator == null) {
            lineSeparator = "\n";
        }
    }
    
    public Text(ComboBoxModel ctmModel) {
        this.ctmModel = ctmModel;
    }
    
    public Text() {}
    
    public String buildText() {
        return "";
    }
    
    protected String getSentence(ItemTextModule tm) {
        String s = new String();
        
        try {
            s = tm.getTargetString();
        
            if (afterSalutation) {
                afterSalutation = false;
                return s.substring(0, 1).toLowerCase() + s.substring(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return s;
    }
    
    protected String getName() {
        try {
            return new StringBuffer().append(lineSeparator)
                    .append(lineSeparator).append(addresser.getName())
                    .append(lineSeparator).append(addresser.getElementsString()
                            .get((Titles.titleCompanyA))).toString();
        }
        catch (Exception e) {
            return "";
        }
    }
    
    protected String getClosing(ItemTextModule tm) {
        try {
            return lineSeparator + lineSeparator + tm.getTargetString();
        } catch (Exception e) {
            return "";
        }
    }
    
    protected String getSubject(ItemTextModule tm) {
        try {
            return tm.getTargetString() + lineSeparator + lineSeparator
                + lineSeparator;
        } catch (Exception e) {
            return "";
        }
    }
    
    protected String getSalutation(ItemTextModule tm) {
        try {
            return new StringBuffer().append(tm.getTargetString())
                    .append(lineSeparator).append(lineSeparator).toString();
        } catch (Exception e) {
            return "";
        }
    }
}
