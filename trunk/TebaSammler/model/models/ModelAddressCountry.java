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

import controller.Controller;
import controller.PropertyKeys;
import controller.Titles;

/**Class for the model which contains the possible countries of an address.
 * 
 * @author Antje Huber
 *
 */
public class ModelAddressCountry extends Model {
        
    public ModelAddressCountry(Titles titles, String defaultItem)
            throws Exception {
        try {
            Vector<String> data = Controller.loadDataFromFile(
                    ClassLoader.getSystemResourceAsStream(
                            Controller.getProperties().getProperty(
                                    PropertyKeys.dirCountries)));
            
            loadModel(defaultItem, titles, data);
        } catch (Exception e) {
            throw new Exception(Titles.loadCountriesException, e);
        }
    }
}
