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


package model.items;

import controller.Titles;

/**Class for comboBox items.
 * 
 * @author Antje Huber
 *
 */
public class Item {
    
    protected String internalName;
    private Titles titles;
    
    public Item(String internalName, Titles titles) {        
        this.internalName = internalName;
        this.titles = titles;
    }
    
    public Item(String internalName) {
        this.internalName = internalName;
    }
    
    public String toString() {
        try {
            return titles.getString(internalName);
        } catch (Exception e){
            return "";
        }
    }
    
    public String getInternalName() {
        return internalName;
    }

    /**Overrides the method equals.*/
    public boolean equals(Object object) {
        return (this == object)
            || ((object != null) && (object instanceof Item)
                    && (((Item)object).internalName != null)
                    && (((Item)object).internalName.equals(internalName)));
    }
}
