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

import gui.GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.ChosenTextModules;
import model.Models;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import controller.commands.Command;
/** This class controlls the program sequence and it's the most important class.
 * So from here the program starts. It creates important objects like instances
 * of class GUI and class Models. It defines file path to external files and
 * loads external files for program titles and user-defined data.
 * The class provides objects and information needed by other
 * classes.
 * 
 * @author Antje Huber
 *
 */
public class Controller implements InterfaceController, ExceptionHandler {
    
    private static Controller controller;
    private static Properties properties = null;
    
    private static final String namePropertiesUserData =
        "propertiesUserData.properties";
    
    private File dirProperties;
    private File dirUserData;
    private File dirPropertiesUserData;
    private File dirDocuments;
    private Properties propertiesUserData;
    private String home;
    private String sep;
    
    private static Vector<String> vectorLocales;

    private Models models;
    private ChosenTextModules ctmModel;
    private Titles titles;
    private GUI ui;    
    private File file;

    private Controller() {        
        sep = File.separator;
        home = System.getProperty("user.home");
        
        setDirs();
        
        loadProperties();
        propertiesUserData = new Properties();
        loadPropertiesUserData();
        
        titles = new Titles(loadLocale(), this);
        
        try {
            models = Models.getInstance(this, titles);
        } catch (Exception e) {
            handleException(e.toString(), e);
        }
        
        ctmModel = ChosenTextModules.getInstance();
        ui = new GUI(this, titles, models, ctmModel, GUI.Status.START);
    }
        
    public static Controller getInstance() {        
        if (controller == null) {
            controller = new Controller();
        }
        
        return controller;
    }
    
    private void setDirs() {
        dirProperties = new File("properties.properties");
        String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) {
            dirUserData = new File(System.getenv("APPDATA"), "TebaSa");
            dirDocuments = new File(home, "TebaSa");
        }
        else if (os.toLowerCase().startsWith("mac os")) {
            dirUserData = new File(home, ".tebaSa");
            dirDocuments = new File(home + sep + "Documents", "TebaSa");
        }
        else {
            dirUserData = new File(home, ".tebaSa");
            dirDocuments = new File(home, "TebaSa");
        }
        
        if (!dirUserData.exists()) {
            dirUserData.mkdir();
        }
        
        dirPropertiesUserData = new File(dirUserData, namePropertiesUserData);
    }
    
    private void loadPropertiesUserData() {
        try {
            propertiesUserData.loadFromXML(
                    new FileInputStream(dirPropertiesUserData));
        } catch (Exception e1) {
            try {
                propertiesUserData.loadFromXML(
                        ClassLoader.getSystemResourceAsStream(
                                namePropertiesUserData));
                propertiesUserData.storeToXML(
                        new FileOutputStream(dirPropertiesUserData), "");
            } catch (Exception e2) {
                handleException(Titles.loadPropertiesUserDataException, e2);
            }
        }
    }
    
    private void loadProperties() {
        if (properties == null) {
            properties = new Properties();
        
            try {
                properties.loadFromXML(ClassLoader.getSystemResourceAsStream(
                        dirProperties.toString()));
            } catch (Exception e) {
                handleException(Titles.loadPropertiesException, e);
                properties = null;
            }
        }
    }
    
    public void commitCommand(Command command) {
        if (command != null) {
            command.setController(this);
            command.execute();
        }
    }
    
    public Models getModels() {
        return models;
    }
    
    public ChosenTextModules getChosenTextModules() {
        return ctmModel;        
    }
    
    public Titles getTitles() {
        return titles;
    }

    public File getFile() {
        return file;
    }
    
    public GUI getUI() {
        return ui;
    }
    
    public static Vector<String> getLocales()
            throws ParserConfigurationException, SAXException, IOException {
        if (vectorLocales == null) {
            vectorLocales = loadDataFromFile(
                    ClassLoader.getSystemResourceAsStream(
                            properties.getProperty(PropertyKeys.dirLocale)));
        }
        
        return vectorLocales;
    }
    
    public File getDirPropertiesUserData() {
        return dirPropertiesUserData;
    }
    
    public File getDirUserData() {
        return dirUserData;
    }
    
    public static Properties getProperties() {
        return properties;
    }
    
    public Properties getPropertiesUserData() {
        return propertiesUserData;    
    }
    
    public File getDirDocuments() {
        if (!dirDocuments.exists()) {
            dirDocuments.mkdir();
        }
        
        return dirDocuments;
    }
    
    public TitledBorder getTitledBorder(String title) {
        if (title == null) {
            title = "";
        }
        
        return BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(EtchedBorder.RAISED),
                titles.getString(title));
    }
    
    public void setFile(File file) {
        this.file = file;
        
        if(file != null) {
            ui.setTitle(GUI.title + " - " + file.getName());
        }
        else {
            ui.setTitle(GUI.title);
        }
    }
    
    public void setUI(GUI gui) {
        this.ui = gui;
    }
    
    public static Vector<String> loadDataFromFile(InputStream file)
            throws ParserConfigurationException, SAXException, IOException {
        Vector<String> data = new Vector<String>();
        
        if (file != null) {
            DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();        
            factory.setIgnoringElementContentWhitespace(true);
            
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            Node node = new DOMIterator(document, "modelEntries").next();
            
            for (Node entry: new DOMIterator(node, "entry"))
                data.add(entry.getTextContent());
        }
        
        return data;
    }
    
    public Locale loadLocale() {
        String standardLocale =
            propertiesUserData.getProperty(PropertyKeys.defaultLocale);

        if (! standardLocale.equals("")) {
            try {                
                String stringLocale = new String();                
                vectorLocales = loadDataFromFile(
                        ClassLoader.getSystemResourceAsStream(
                            properties.getProperty(PropertyKeys.dirLocale)));
                String language;
                String country;
                
                for(String e: vectorLocales) {
                    if(standardLocale.equals(e)) {
                        stringLocale = e;
                    }
                }
                
                language = (stringLocale.substring(
                        stringLocale.length() - 4, stringLocale.length() -2));                
                country = (stringLocale.substring(stringLocale.length() - 2));
                
                return new Locale(language, country);
            } catch (Exception exception) {
                handleException(Titles.loadLocaleException, exception);                
                return Locale.getDefault();
            }
        }
        else {
            return Locale.getDefault();
        }
    }
    
    public void handleException(String text, Exception exception) {
        if (ui != null) {
            ui.printException(text);
        }
        
        System.out.println(text);
        exception.printStackTrace();
    }
    
    public static void main(String args[]) {
        Controller.getInstance();
    }
}
