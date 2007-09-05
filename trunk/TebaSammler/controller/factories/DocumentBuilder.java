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

import java.text.DateFormat;
import java.util.Locale;

import javax.swing.ComboBoxModel;

import model.Models;
import model.items.ItemAddress;
import model.models.ModelAddress;
import controller.Controller;
import controller.Titles;

/**This class provides displayable forms of texts, dates and addresses to
 * other classes.
 * 
 * @author Antje Huber
 *
 */
public class DocumentBuilder {
    
    private Address addresseeText;
    private Address addresserText;
    private Text textText;
    private Date dateText;
    
    private Titles titlesAddressee;
    private Titles titlesAddresser;    
    private Locale localeTargetLanguage;    
    private ItemAddress addresser;
    private ItemAddress addressee;    
    private String lineSeparator;    
    private ModelAddress aModel;
    
    private Controller controller;
    private Models models;
    private ComboBoxModel ctmModel;
    private String style;

    public DocumentBuilder(Controller controller, Titles titles, Models models,
            ComboBoxModel ctmModel, String style) {
        this.controller = controller;
        this.models = models;
        this.ctmModel = ctmModel;
        this.style = style;
        
        lineSeparator =  "\n";
        
        try {
            aModel = (ModelAddress)models.get(Titles.titleAddressM);
            addressee = aModel.getAddressee();
            addresser = aModel.getAddresser();
            
            titlesAddressee =
                BundleBuilder.getInstance(controller).buildBundle(addressee);
            titlesAddresser =
                BundleBuilder.getInstance(controller).buildBundle(addresser);
            localeTargetLanguage =
                LocaleBuilderTargetLanguage.getInstance(models).buildLocale();
            
            addresseeText =
                    FactoryAddressee.getInstance(lineSeparator).getAddressee(
                            addressee, titlesAddressee, titlesAddresser);
            addresserText = FactoryAddresser.getInstance(lineSeparator)
                    .getAddresser(addresser, titlesAddresser);
            textText = FactoryText.getInstance(
                    models, ctmModel, lineSeparator).getText(addresser, style);
            dateText = FactoryDate.getInstance(models, lineSeparator)
                    .getDate(addresser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void languageChanged() {
        localeTargetLanguage =
            LocaleBuilderTargetLanguage.getInstance(models).buildLocale();
        dateText =
            FactoryDate.getInstance(models, lineSeparator).getDate(addresser);
        textText = FactoryText.getInstance(
                models, ctmModel, lineSeparator).getText(addresser, style);
    }
        
    public void addresseeChanged() {
        try {
            addressee = aModel.getAddressee();
            titlesAddressee =
                BundleBuilder.getInstance(controller).buildBundle(addressee);
            addresseeText =
                    FactoryAddressee.getInstance(lineSeparator).getAddressee(
                            addressee, titlesAddressee, titlesAddresser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addresserChanged() {
        try {
            addresser = aModel.getAddresser();
            titlesAddresser =
                BundleBuilder.getInstance(controller).buildBundle(addresser);            
            addresserText = FactoryAddresser.getInstance(lineSeparator)
                .getAddresser(addresser, titlesAddresser);
            addresseeText =
                    FactoryAddressee.getInstance(lineSeparator).getAddressee(
                            addressee, titlesAddressee, titlesAddresser);
            dateText = FactoryDate.getInstance(
                    models, lineSeparator).getDate(addresser);
            textText = FactoryText.getInstance(
                    models, ctmModel, lineSeparator).getText(addresser, style);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String buildAddresser() {
        if (addresserText != null) {
            return addresserText.buildAddress();
        }
        else {
            return "";
        }
    }
    
    public String buildAddressee() {
        if (addresseeText != null) {
            return addresseeText.buildAddress();
        }
        else {
            return "";
        }
    }
    
    public String buildDate() {
        if (dateText != null) {
            String date = DateFormat.getDateInstance(DateFormat.LONG,
                    localeTargetLanguage).format(new java.util.Date());
            
            return dateText.buildDate(date);
        }
        else {
            return "";
        }
    }
    
    public String buildText(ComboBoxModel ctmModel) {
        if (textText != null) {
            return textText.buildText();
        }
        else {
            return "";
        }
    }
}
