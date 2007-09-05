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

import java.util.Locale;

import model.Models;
import model.items.ItemLanguagePair;
import controller.Titles;

/**This class creates a new instance of class Locale accordingly to the
 * selected target language.
 * 
 * @author Antje Huber
 *
 */
public class LocaleBuilderTargetLanguage {
    
    private static LocaleBuilderTargetLanguage factory;
    
    private Models models;
        
    private LocaleBuilderTargetLanguage(Models models) {
        this.models = models;
    }
    
    public static LocaleBuilderTargetLanguage getInstance(Models models) {
        if (factory == null) {
            factory = new LocaleBuilderTargetLanguage(models);
        }
        
        return factory;
    }

    public Locale buildLocale() {
        ItemLanguagePair language = ((ItemLanguagePair)models.get(
                Titles.titleLanguageM).getSelectedItem());
        
        if (language.compareTargetLanguages(
                Titles.titleLanguageEnglishGerman) ||
                language.compareTargetLanguages(
                        Titles.titleLanguageFrenchGerman)) {
            return new Locale("de", "DE");
        }
        else if (language.compareTargetLanguages(
                Titles.titleLanguageEnglishFrench) ||
                language.compareTargetLanguages(
                        Titles.titleLanguageGermanFrench)) {
            return new Locale("fr", "FR");
        }
        else {
            return new Locale("en", "UK");
        }
    }
}
