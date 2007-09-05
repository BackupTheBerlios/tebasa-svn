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


package model.models;

import java.util.Properties;
import java.util.Vector;

import model.items.Item;
import model.items.ItemDictionary;

import controller.Controller;
import controller.PropertyKeys;
import controller.Titles;

/**Class for the model which contains the dictionaries.
 * 
 * @author Antje Huber
 *
 */
public class ModelDictionary extends Model {
    
    private Titles titles;
    
    public ModelDictionary(
            Properties propertiesUserData, Titles titles) throws Exception {
        this.titles = titles;
        
        try {
            Vector<String> data = Controller.loadDataFromFile(
                    ClassLoader.getSystemResourceAsStream(
                            Controller.getProperties().getProperty(
                                    PropertyKeys.dirDictionaries)));
             
             loadModel((String)propertiesUserData.get(
                     PropertyKeys.defaultDictionary),titles, data);
        } catch (Exception e) {
            throw new Exception(Titles.loadDictionariesException, e);
        }
    }
    
    protected void loadModel(
            String defaultItem, Titles titles, Vector<String> items) {
        for (String e: items) {
            addElement(new ItemDictionary(e, titles));
        }
        setSelectedItem(new ItemDictionary(defaultItem, titles));
    }
    
    public void setSelectedItem(Object item) {
        if (!(item instanceof Item)) {
            throw new IllegalArgumentException("Argument is not of type Item");
        }
        
        if (! (item instanceof ItemDictionary)) {
            item = new ItemDictionary(((Item)item).getInternalName(), titles);
        }
        
        super.setSelectedItem(item);
    }
}
