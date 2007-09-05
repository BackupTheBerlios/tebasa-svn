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

/**Class for the specialized items of a comboBox for textModules.
 * 
 * @author Antje Huber
 *
 */
public class ItemTextModule extends Item {
    
    private String sourceString;
    private String targetString;
    
    public ItemTextModule(
            String sourceString, String targetString, String internalName) {
        super(internalName);
        
        this.sourceString = sourceString;
        this.targetString = targetString;
    }
    
    public String getSourceString() {
        return sourceString;        
    }
    
    public String getTargetString() {
        return targetString;
    }
    
    public String toString() {
        return sourceString;
    }

    @Override
    public boolean equals(Object object) {
        return this == object;
    }
}


