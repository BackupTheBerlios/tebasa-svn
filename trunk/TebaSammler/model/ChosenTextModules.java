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


package model;

import javax.swing.DefaultComboBoxModel;

import model.items.ItemTextModule;

import controller.Titles;

/**Class for the model which contains the chosen text modules. This is the
 * only model in this program not getting it's content from external files. 
 * 
 * @author Antje Huber
 *
 */
public class ChosenTextModules extends DefaultComboBoxModel {
    
    private static ChosenTextModules ctmModel;
    
    private int numberOfSentences;
    private boolean containsSalutation;
    private boolean containsSubject;
    private boolean containsClosing;
    
    private ChosenTextModules() {}
    
    public static ChosenTextModules getInstance() {        
        if (ctmModel == null) {            
            ctmModel = new ChosenTextModules();
        }
        
        return ctmModel;
    }
    
    public void addSubject(ItemTextModule tm) {
        if (tm != null) {
            removeSubject();
            insertElementAt(tm, 0);
            containsSubject = true;
            setSelectedItem();
        }
    }
    
    public void addSalutation(ItemTextModule tm) {
        if (tm != null) {
            removeSalutation();
            insertElementAt(tm,
                    getIndexOfHeaderItem(Titles.titleTextModuleSubject) + 1);
            containsSalutation = true;
            setSelectedItem();
        }
    }
    
    public void addClosing(ItemTextModule tm) {
        if (tm != null) {
            removeClosing();
            insertElementAt(tm, getSize());
            containsClosing = true;
            setSelectedItem();
        }
    }
    
    public void addSentence(ItemTextModule tm, int index) {
        if (tm != null) {
            insertElementAt(tm, index);
            numberOfSentences++;
            setSelectedItem();
        }
    }
    
    public void removeSubject() {
        if (containsSubject) {            
            removeElementAt(
                    getIndexOfHeaderItem(Titles.titleTextModuleSubject));
            containsSubject = false;
        }
        else {
            setSelectedItem();
        }
    }
    
    public void removeSalutation() {
        if (containsSalutation) {
            removeElementAt(
                    getIndexOfHeaderItem(Titles.titleTextModuleSalutation));
            containsSalutation = false;
        }
        else {
            setSelectedItem();
        }
    }
    
    public void removeClosing() {
        if (containsClosing) {
            removeElementAt(                    
                    getIndexOfHeaderItem(Titles.titleTextModuleClosing));
            containsClosing = false;
        }
        else {
            setSelectedItem();
        }
    }
    
    public void removeSentence() {        
        numberOfSentences--;
        removeElement(getSelectedItem());
        setSelectedItem();
    }
    
    public void removeAllElements() {
        reset();
        super.removeAllElements();
    }
    
    private void reset() {
        numberOfSentences = 0;
        containsSubject = false;
        containsSalutation = false;
        containsClosing = false;
    }
    
    private void setSelectedItem() {
        int index;
        
        if (numberOfSentences > 0) {
            index = getIndexOfLastSentence();
        }
        else if (containsSalutation) {            
                index = getIndexOfHeaderItem(Titles.titleTextModuleSalutation);
        }
        else if (containsSubject) {
            index = getIndexOfHeaderItem(Titles.titleTextModuleSubject);
        }
        else {
            index = 0;
        }
        
        setSelectedItem(getElementAt(index));
    }
    
    private int getIndexOfHeaderItem(String internalName) {
        for (int i = 0; i < getSize(); i++) {
            if (((ItemTextModule)getElementAt(i)).getInternalName()
                    .equals(internalName)) {
                return i;
            }
        }
        
        return -1;
    }
    
    private int getIndexOfLastSentence() {        
        for (int i = getSize() - 1; i > -1; i--) {
            if (((ItemTextModule)getElementAt(i)).getInternalName()
                    .equals(Titles.titleTextModuleText)) {
                return i;
            }
        }
        
        return getSize() - 1;
    }
}
