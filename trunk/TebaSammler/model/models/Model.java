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

import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListDataListener;

import model.items.Item;

import controller.Titles;

/**Abstract class for models.
 * 
 * @author Antje Huber
 *
 */
public abstract class Model extends DefaultComboBoxModel {
    
    protected void loadModel(
            String defaultItem, Titles titles, Vector<String> items) {        
        for (String e: items) {
            addElement(new Item(e, titles));
        }
        setSelectedItem(new Item(defaultItem, titles));
    }
    
    public void removeListeners() {        
        for (ListDataListener e: getListDataListeners()) {
            removeListDataListener(e);
        }
    }
    
    public Vector<Object> getAllItems() {
        Vector<Object> items = new Vector<Object>();
        
        for (int i = 0; i < getSize(); i++) {
            items.add(getElementAt(i));
        }
        
        return items;
    }
}
