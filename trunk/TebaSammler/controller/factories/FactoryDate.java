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

import model.Models;
import model.items.ItemAddress;
import model.items.ItemLanguagePair;
import controller.Titles;

/**This class defines which date object will be used for the displayed
 * form of the date.
 * 
 * @author Antje Huber
 *
 */
public class FactoryDate {
    
    private static FactoryDate factoryDate;
    
    private String lineSeparator;
    private Models models;
    
    private FactoryDate(Models models, String lineSeparator) {
        this.models = models;
        this.lineSeparator = lineSeparator;
    }
    
    public static FactoryDate getInstance(Models models, String lineSeparator) {
        if (factoryDate == null) {
            factoryDate = new FactoryDate(models, lineSeparator);
        }
        
        return factoryDate;
    }
    
    public Date getDate(ItemAddress addresser) {
        ItemLanguagePair language = ((ItemLanguagePair)models.get(
                Titles.titleLanguageM).getSelectedItem());
        
        if (language.compareTargetLanguages(
                Titles.titleLanguageEnglishGerman) ||
                language.compareTargetLanguages(
                        Titles.titleLanguageFrenchGerman)) {
            return new DateGerman(addresser);
        }
        else if (language.compareTargetLanguages(
                Titles.titleLanguageEnglishFrench) ||
                language.compareTargetLanguages(
                        Titles.titleLanguageGermanFrench)) {
            return new DateFrench(addresser, lineSeparator);
        }
        else {
            return new Date();
        }
    }
}
