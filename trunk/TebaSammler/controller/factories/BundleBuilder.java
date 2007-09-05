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

import model.items.ItemAddress;
import controller.Controller;
import controller.Titles;

/**This class creates a new instance of class Locale accordingly to a country.
 * 
 * @author Antje Huber
 *
 */
public class BundleBuilder {
    
    private static BundleBuilder factoryBundle;
    
    private Controller controller;
    
    private BundleBuilder(Controller controller) {
        this.controller = controller;
    }
    
    public static BundleBuilder getInstance(Controller controller) {
        if (factoryBundle == null) {
            factoryBundle = new BundleBuilder(controller);
        }
        
        return factoryBundle;
    }
    
    public Titles buildBundle(ItemAddress address) {
        if (address != null) {
            String country =
                address.getElementsString().get(Titles.titleCountryA);
            Locale locale;
            
            if (country.equals(Titles.titleCountryGermany)) {
                locale = new Locale("de", "DE");
            }
            else if (country.equals(Titles.titleCountryFrance)) {
                locale = new Locale("fr", "FR");
            }
            else {
                locale = new Locale("en", "UK"); 
            }
            
            return new Titles(locale, controller);
        }
        return null;
    }
}
