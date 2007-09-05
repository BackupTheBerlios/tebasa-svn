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

import model.Models;
import model.items.ItemAddress;
import model.items.ItemLanguagePair;
import controller.Titles;

/**This class defines which text object will be used for the displayed
 * form of the text.
 * 
 * @author Antje Huber
 *
 */
public class FactoryText {
    
    private static FactoryText factoryText;
    
    private Models models;
    private ComboBoxModel ctmModel;
    private String lineSeparator;
    
    private FactoryText(
            Models models, ComboBoxModel ctmModel, String lineSeparator) {
        this.models = models;
        this.ctmModel = ctmModel;
        this.lineSeparator = lineSeparator;
    }
    
    public static FactoryText getInstance(
            Models models, ComboBoxModel ctmModel, String lineSeparator) {
        if (factoryText == null) {
            factoryText =
                new FactoryText(models, ctmModel, lineSeparator);
        }
        
        return factoryText;
    }
    
    public Text getText(ItemAddress addresser, String style) {
        ItemLanguagePair language = ((ItemLanguagePair)models.get(
                Titles.titleLanguageM).getSelectedItem());        
        boolean isTextGerman = false;
        
        if (language.compareTargetLanguages(
                Titles.titleLanguageEnglishGerman) ||
                language.compareTargetLanguages(
                        Titles.titleLanguageFrenchGerman)) {
            isTextGerman = true;
        }
        
        if (style != null) {
            if (style.equals(Titles.titleDocumentStyleAd)) {
                return new TextAdvertisement(ctmModel);
            }
            else if (style.equals(Titles.titleDocumentStyleEMail)) {
                return new TextEMail(
                        ctmModel, addresser, isTextGerman, lineSeparator);
            }
            else {
                return new TextLetter(
                        ctmModel, addresser, isTextGerman, lineSeparator);
            }
        }
        else {
            return null;
        }
    }
}
