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

import java.util.HashMap;
import java.util.Properties;

import model.models.Model;
import model.models.ModelAddress;
import model.models.ModelCollectingArea;
import model.models.ModelDictionary;
import model.models.ModelDocumentStyle;
import model.models.ModelLanguage;
import model.models.ModelLocale;
import model.models.ModelTextModule;

import controller.Controller;
import controller.Titles;

/**Class containing almost all models.
 * Exceptions are the model for chosen text modules, the model for
 * address titles and the model for address countries.
 * 
 * @author Antje Huber
 *
 */
public class Models extends HashMap<String, Model> {
    
    private static Models models;

    private Models(Controller controller, Titles titles) throws Exception {
        
        Properties propertiesUserData = controller.getPropertiesUserData();

        put(Titles.titleDictionaryM, new ModelDictionary(
                propertiesUserData, titles));
        put(Titles.titleLanguageM, new ModelLanguage(
                propertiesUserData, titles));
        put(Titles.titleDocumentStyleM, new ModelDocumentStyle(
                propertiesUserData, titles));
        put(Titles.titleCollectingAreaM,
                new ModelCollectingArea(propertiesUserData, titles));
        put(Titles.titleLocaleM, new ModelLocale(propertiesUserData, titles));
        
        put(Titles.titleAddressM, ModelAddress.getInstance(
                controller.getDirUserData()));
        put(Titles.titleTextModuleM, ModelTextModule.getInstance(controller,
                (ModelLanguage)get(Titles.titleLanguageM),
                (ModelCollectingArea)get(Titles.titleCollectingAreaM),
                (ModelDocumentStyle)get(Titles.titleDocumentStyleM)));
    }
    
    public static Models getInstance(
            Controller controller, Titles titles) throws Exception {
        if (models == null) {
            models = new Models(controller, titles);
        }
        
        return models;
    }
    
    public void removeListeners() {
        for (Model e: values()) {
            e.removeListeners();
        }
    }
}
