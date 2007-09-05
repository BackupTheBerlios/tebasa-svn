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

/**This class creates a displayed form of an letter text.
 * 
 * @author Antje Huber
 *
 */
public class TextLetter extends Text {
    
    public TextLetter(ComboBoxModel ctmModel, ItemAddress addresser,
            boolean isTextGerman, String lineSeparator) {
        super(ctmModel, addresser, isTextGerman, lineSeparator);
    }
    
    public String buildText() {
        text = new StringBuffer();
        String closing = null;
        
        for (int i = 0; i < ctmModel.getSize(); i++) {                
            ItemTextModule tm = (ItemTextModule)ctmModel.getElementAt(i);
            String name = tm.getInternalName();
            
            if (name.equals(Titles.titleTextModuleSubject)) {
                text.insert(0, getSubject(tm));
            }
            else if (name.equals(Titles.titleTextModuleClosing)) {
                closing = getClosing(tm);
            }
            else if (name.equals(Titles.titleTextModuleSalutation)) {
                text.append(getSalutation(tm));
                if (isTextGerman) {
                    afterSalutation = true;
                }
            }
            else {
                text.append(getSentence(tm)).append(" ");
            }
        }
        
        if (closing != null) {
            text.insert(text.length(), closing);
            
            if (addresser != null && closing.length() > 0) {
                text.append(lineSeparator).append(lineSeparator)
                        .append(getName());
            }
        }
        
        return text.toString();
    }
}
