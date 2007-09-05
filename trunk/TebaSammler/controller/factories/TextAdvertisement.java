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

import model.items.ItemTextModule;
import controller.Titles;

/**This class creates a displayed form of an advertisement text.
 * 
 * @author Antje Huber
 *
 */
public class TextAdvertisement extends Text {
    
    public TextAdvertisement(ComboBoxModel ctmModel) {
        super(ctmModel);
    }

    public String buildText() {
        text = new StringBuffer();
        
        for (int i = 0; i < ctmModel.getSize(); i++) {
            ItemTextModule tm = (ItemTextModule)ctmModel.getElementAt(i);
            String name = tm.getInternalName();
            
            if (name.equals(Titles.titleTextModuleText)) {
                text.append(tm.getTargetString()).append(" ");
            }
            else {
                continue;
            }
        }
        
        return text.toString();
    }
}
