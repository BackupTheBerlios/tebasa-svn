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


package controller;

import java.util.Locale;
import java.util.ResourceBundle;

/**Class which provides Strings for internalNames and MessagesBundle keys.
 * It provides also titles in the chosen program language.
 * 
 * @author Antje Huber
 *
 */
public class Titles {

    //Frame titles
    public static final String frameTitleGUI = "frameTitleGUI";
    public static final String frameTitleAddress = "frameTitleAddress";
    
    //Dialog titles    
    public static final String dialogTitleAddress = "dialogTitleAddress";
    public static final String dialogTitleDefaultSettings =
        "dialogTitleDefaultSettings";
    public static final String dialogTitleSettings = "dialogTitleSettings";
    public static final String dialogTitleOpen = "dialogTitleOpen";
    public static final String dialogTitleSave = "dialogTitleSave";
    public static final String dialogTitleException = "dialogTitleException";
    public static final String dialogTitleEditable = "dialogTitleEditable";
    public static final String dialogTitleChooseAddresses =
        "dialogTitleChooseAddresses";
    public static final String dialogTitleChooseProgram =
        "dialogTitleChooseProgram";
    
    //Dialog texts
    public static final String dialogTitleTextEditable =
        "dialogTitleTextEditable";
    
    //Menu titles    
    public static final String menuTitleFile = "menuTitleFile";
    public static final String menuTitleTools = "menuTitleTools";
    public static final String menuTitleHelp = "menuTitleHelp";
    
    //Menu item titles
    public static final String menuItemTitleNew = "menuItemTitleNew";
    public static final String menuItemTitleCloseProgram =
        "menuItemTitleCloseProgram";
    public static final String menuItemTitleEMail = "menuItemTitleEMail";
    public static final String menuItemTitlePrint = "menuItemTitlePrint";
    public static final String menuItemTitleSaveAs = "menuItemTitleSaveAs";
    public static final String menuItemTitleSave = "menuItemTitleSave";
    public static final String menuItemTitleOpen = "menuItemTitleOpen";
    public static final String menuItemTitleSettings = "menuItemTitleSettings";
    public static final String menuItemTitleAddressbook =
        "menuItemTitleAddressbook";
    public static final String menuItemTitleHelp = "menuItemTitleHelp";
    
    //Button titles    
    public static final String buttonTitleRemoveAddress =
        "buttonTitleRemoveAddress";
    public static final String buttonTitleShowAddress =
        "buttonTitleShowAddress";
    public static final String buttonTitleNewAddress = "buttonTitleNewAddress";
    public static final String buttonTitleCloseAddressbook = 
        "buttonTitleCloseAddressbook";
    public static final String buttonTitleSaveAddress =
        "buttonTitleSaveAddress";
    public static final String buttonTitleCancelAddress =
        "buttonTitleCancelAddress";
    
    public static final String buttonTitleSearchWord = "buttonTitleSearchWord";
    
    public static final String buttonTitleResetTMs = "buttonTitleResetTMs";
    public static final String buttonTitleAppendTM = "buttonTitleAppendTM";
    public static final String buttonTitleRemoveTM = "buttonTitleRemoveTM";

    public static final String buttonTitleCloseDialog =
        "buttonTitleCloseDialog";    
    public static final String buttonTitleOpenAddress =
        "buttonTitleOpenAddress";
    
    public static final String buttonTitleEditTargetText =
        "buttonTitleEditTargetText";
    public static final String buttonTitleAddressTargetText =
        "buttonTitleAddressTargetText";
    
    public static final String buttonTitleOkSettings = "buttonTitleOkSettings";
    public static final String buttonTitleCancelSettings =
        "buttonTitleCancelSettings";
     
    public static final String buttonTitleOkNew = "buttonTitleOkNew";
    public static final String buttonTitleCancelNew = "buttonTitleCancelNew";
    
    public static final String buttonTitleDialogEditableYes =
        "buttonTitleDialogEditableYes";
    public static final String buttonTitleDialogEditableNo =
        "buttonTitleDialogEditableNo";
    
    //Border titles
    public static final String borderTitleTMHeaderAd = "borderTitleTMHeaderAd";
    public static final String borderTitleTMHeaderLetterEMail =
        "borderTitleTMHeaderLetterEMail";
    public static final String borderTitleTMContent = "borderTitleTMContent";
    public static final String borderTitleTMReset = "borderTitleTMReset";
    public static final String borderTitleSearch = "borderTitleSearch";
    public static final String borderTitleSourceText = "borderTitleSourceText";
    public static final String borderTitleTargetText = "borderTitleTargetText";
    public static final String borderTitleAddresseeA = "borderTitleAddresseeA";
    public static final String borderTitleAddresserA = "borderTitleAddresserA";
    
    //TextField titles
    public static final String textFieldTitleSearch = "textFieldTitleSearch";
    
    //CheckBox titles
    public static final String checkBoxTitleIsAddressee =
        "checkBoxTitleIsAddressee";
    public static final String checkBoxTitleIsAddresser =
        "checkBoxTitleIsAddresser";
    
    //Exception texts
    public static final String noSentenceChosenException =
        "noSentenceChosenException";
    public static final String writeDefaultSettingsException =
        "writeDefaultSettingsException";
    public static final String saveTextException = "saveTextException";
    public static final String openTextException = "openTextException";
    public static final String printTextException = "printTextException";
    public static final String recipientEMailException =
        "recipientEMailException";
    public static final String subjectEMailException = "subjectEMailException";
    public static final String textEMailException = "textEMailException";
    public static final String openEMailClientException =
        "openEMailClientException";
    public static final String openBrowserException = "openBrowserException";
    public static final String loadLocaleException = "loadLocaleException";
    public static final String loadPropertiesUserDataException =
        "loadPropertiesUserDataException";
    public static final String loadPropertiesException =
        "loadPropertiesException";
    public static final String loadCollectingAreasException =
        "loadCollectingAreasException";
    public static final String loadDictionariesException =
        "loadDictionariesException";
    public static final String loadLanguagesException =
        "loadLanguagesException";    
    public static final String loadAddressesException =
        "loadAddressesException";
    public static final String loadCountriesException =
        "loadCountriesException";    
    public static final String loadDocumentStylesException =
        "loadDocumentStylesException";
    public static final String setLookAndFeelException =
        "setLookAndFeelException";
    public static final String loadAddressbookException =
        "loadAddressbookException";
    public static final String writeAddressbookException =
        "writeAddressbookException";
    public static final String loadTextModuleException =
        "loadTextModuleException";
    public static final String noLabelChosenException =
        "noLabelChosenException";
    public static final String setTextException = "setTextException";
    public static final String getTextException = "getTextException";
    public static final String setAddresseeException = "setAddresseeException";
    public static final String getAddresseeException = "getAddresseeException";
    public static final String setAddresserException = "setAddresserException";
    public static final String getAddresserException = "getAddresserException";
    public static final String setDateException = "setDateException";
    public static final String getDateException = "getDateException";
    public static final String setSubjectException = "setSubjectException";
    public static final String getSubjectException = "getSubjectException";
    public static final String bundleNotFoundException =
        "bundleNotFoundException";    
    
    //Tool tip text titles
    public static final String toolTipTextTitlePrint = "toolTipTextTitlePrint";
    public static final String toolTipTextTitleSave = "toolTipTextTitleSave";
    public static final String toolTipTextTitleOpen = "toolTipTextTitleOpen";
    public static final String toolTipTextTitleEMail = "toolTipTextTitleEMail";
    public static final String toolTipTextTitleNew = "toolTipTextTitleNew";
        
    //Label titles
    public static final String labelTitleChooseCategory =
        "labelTitleChooseCategory";
    public static final String labelTitleChooseTM = "labelTitleChooseTM";
    public static final String labelTitleAppendTM = "labelTitleAppendTM";
    public static final String labelTitleRemoveTM = "labelTitleRemoveTM";
    
    public static final String labelTitleChooseAddresses =
        "labelTitleChooseAddresses";
    public static final String labelTitleShowAddresses =
        "labelTitleShowAddresses";
    public static final String labelTitleChangeBrowser =
        "labelTitleChangeBrowser";
    public static final String labelTitleChangeEMailClient =
        "labelTitleChangeEMailClient";
    
    //Label titles and map keys
    public static final String titleAddressA = "titleAddressA";
    public static final String titleCompanyA = "titleCompanyA";
    public static final String titleSurnameA = "titleSurnameA";
    public static final String titleForenameA = "titleForenameA";
    public static final String titleStreetA = "titleStreetA";
    public static final String titleStreetNumberA = "titleStreetNumberA";
    public static final String titlePostalCodeA = "titlePostalCodeA";
    public static final String titleCityA = "titleCityA";
    public static final String titleCountryA = "titleCountryA";
    public static final String titleTelefoneNumberA = "titleTelefoneNumberA";
    public static final String titleMobileNumberA = "titleMobileNumberA";
    public static final String titleFaxNumberA = "titleFaxNumberA";
    public static final String titleEmailAddressA = "titleEmailAddressA";
    public static final String titleBankA = "titleBankA";
    public static final String titleBankCodeA = "titleBankCodeA";
    public static final String titleAccountNumberA = "titleAccountNumberA";

    /*Label titles and model names, titleAddressM and titleTextModuleM not
     * needed in MessagesBundles
     */
    public static final String titleLanguageM = "titleLanguageM";
    public static final String titleDocumentStyleM = "titleDocumentStyleM";
    public static final String titleCollectingAreaM = "titleCollectingAreaM";
    public static final String titleDictionaryM = "titleDictionaryM";
    public static final String titleLocaleM = "titleLocaleM";
    public static final String titleAddressM = "titleAddressM";
    public static final String titleTextModuleM = "titleTextModuleM";
        
    //ComboBox titles and internal names
    public static final String titleDictionaryLeoDE = "titleDictionaryLeoDE";
    public static final String titleDictionaryLeoDF = "titleDictionaryLeoDF";
        
    public static final String titleDocumentStyleLetter =
        "titleDocumentStyleLetter";
    public static final String titleDocumentStyleEMail =
        "titleDocumentStyleEMail";
    public static final String titleDocumentStyleAd = "titleDocumentStyleAd";
    
    public static final String titleCountry = "titleCountry";
    public static final String titleCountryFrance = "titleCountryFrance";
    public static final String titleCountryGermany = "titleCountryGermany";
    
    //ComboBox titles and internal names
    public static final String titleLanguageEnglishGerman = "English-German";
    public static final String titleLanguageEnglishFrench = "English-French";
    public static final String titleLanguageFrenchGerman = "French-German";
    public static final String titleLanguageGermanFrench = "German-French";
    
    //Text module titles, not needed in MessagesBundles
    public static final String titleTextModuleSalutation =
        "titleTextModuleSalutation";
    public static final String titleTextModuleSubject =
        "titleTextModuleSubject";
    public static final String titleTextModuleCategory =
        "titleTextModuleCategory";
    public static final String titleTextModuleText = "titleTextModuleText";
    public static final String titleTextModuleClosing =
        "titleTextModuleClosing";
    
    
    private ResourceBundle bundle;
    private Controller controller;
    
    public Titles(Locale locale, Controller controller) {
        this.controller = controller;
        
        loadLocale(locale);
    }
    
    /**Loads the MessagesBundle files.
     * 
     * @param locale
     */
    public void loadLocale(Locale locale) {
        try {
            bundle = ResourceBundle.getBundle("MessagesBundle", locale);
        }
        catch (Exception e) {
            controller.handleException(bundleNotFoundException, e);
        }
    }
    
    /**This method provides a title for an internal name.
     * 
     * @param internalName
     * @return title
     */
    public String getString(String internalName) {
        try {
            return bundle.getString(internalName);
        } catch (Exception e) {
            return internalName;
        }
    }
}
