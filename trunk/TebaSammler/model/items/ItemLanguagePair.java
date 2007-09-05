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

/**Class for the comboBox items which are specialized for language pairs.
 * 
 * @author Antje Huber
 *
 */
public class ItemLanguagePair extends Item {
        
    public ItemLanguagePair(String internalName, Titles titles) {
        super(internalName, titles);
    }
    
    public String getSourceLanguage() {
        return getSourceLanguage(internalName);
    }
    
    public String getTargetLanguage() {
        return getTargetLanguage(internalName);
    }
    
    public String getSourceLanguage(String languagePair) {
        if (languagePair != null) {
            return languagePair.substring(0, languagePair.lastIndexOf("-"));
        }
        
        else return "";
    }
        
    public String getTargetLanguage(String languagePair) {
        if (languagePair != null) {
            return languagePair.substring(languagePair.lastIndexOf("-") + 1);
        }
        
        else return "";
    }
    
    public boolean compareTargetLanguages(String language) {
        if (getTargetLanguage(internalName)
                .equals(getTargetLanguage(language))) {
            return true;
        }
        else {
            return false;
        }
    }
}
