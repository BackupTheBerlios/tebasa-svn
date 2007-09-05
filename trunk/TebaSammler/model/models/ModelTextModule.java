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

import gui.listenerNotifier.ListenerSettingChanged;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.items.Item;
import model.items.ItemLanguagePair;
import model.items.ItemTextModule;
import model.items.ItemTextModuleCategory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import controller.Controller;
import controller.DOMIterator;
import controller.ExceptionHandler;
import controller.PropertyKeys;
import controller.Titles;

/**Class for the models which contains the text modules.
 * It covers models for salutation, subject, closing, category and text.
 * It can have different status, which depends on the chosen document types.
 * 
 * @author Antje Huber
 *
 */
public class ModelTextModule extends Model {

    private final static String advertisementTitle = "Ad";
    private final static String partSubject = "Subject";
    private final static String partSalutation = "Salutation";
    private final static String partClosing = "Closing";
    private final static String partText = "Text";
    
    private static ModelTextModule tmModel;
    
    private final ModelTextModulePart modelSu;
    private final ModelTextModulePart modelSa;
    private final ModelTextModulePart modelCat;
    private final ModelTextModulePart modelT;
    private final ModelTextModulePart modelC;
    
    private InterfaceModelTextModule inner;
    private String style;
    
    private ExceptionHandler exceptionHandler;
    private ModelLanguage lModel;
    private ModelDocumentStyle tsModel;
    private ModelCollectingArea caModel;
    
    private ModelTextModule(final ExceptionHandler exceptionHandler,
            final ModelLanguage lModel, final ModelCollectingArea caModel,
            final ModelDocumentStyle tsModel) {

        this.exceptionHandler = exceptionHandler;
        this.lModel = lModel;
        this.tsModel = tsModel;
        this.caModel = caModel;
        
        modelSu = new ModelTextModulePart() {
            public void loadModel() {
                super.load(partSubject,
                        ((Item)caModel.getSelectedItem()).getInternalName(),
                        this, Titles.titleTextModuleSubject);
            }
        };
        
        modelSa = new ModelTextModulePart() {
            public void loadModel() {
                super.load(partSalutation, "", this,
                        Titles.titleTextModuleSalutation);
            }
        };
        
        modelCat = new ModelTextModulePart() {
            public void loadModel() {
                load(partText,
                        ((Item)caModel.getSelectedItem()).getInternalName(),
                        this, Titles.titleTextModuleText);
            }
            
            private void load(String part, String area, Model model,
                    String internalName) { 
                try {
                    String sourceLanguage =
                            ((ItemLanguagePair)lModel.getSelectedItem())
                            .getSourceLanguage();
                    String targetLanguage =
                            ((ItemLanguagePair)lModel.getSelectedItem())
                            .getTargetLanguage();
                    
                    Map<Integer, CategoryEntry> mapSource =
                            fillMap(part, area, sourceLanguage);
                    Map<Integer, CategoryEntry> mapTarget =
                            fillMap(part, area, targetLanguage);
                    
                    model.removeAllElements();
                    
                    for (Map.Entry<Integer, CategoryEntry> e:
                            mapSource.entrySet()) {
                        CategoryEntry targetEntry = mapTarget.get(e.getKey());
                        List<ItemTextModule> listTextModules =
                                new ArrayList<ItemTextModule>();
                                                    
                        if (targetEntry != null) {
                            for (Map.Entry<Integer, String> e2:
                                    e.getValue().map.entrySet()) {                
                                String valueTarget =
                                        targetEntry.map.get(e2.getKey());
                                
                                if (valueTarget != null) {
                                    listTextModules.add(new ItemTextModule(
                                            e2.getValue(), valueTarget,
                                            internalName));
                                }
                            }
                            
                            model.addElement(new ItemTextModuleCategory(
                                    listTextModules, e.getValue().title));
                        }
                    }
                } catch (Exception exception) {
                    exceptionHandler.handleException(
                            Titles.loadTextModuleException, exception);
                }
            }
            
            private Map<Integer, CategoryEntry> fillMap(
                    String part, String area, String language) throws
                    ParserConfigurationException, SAXException, IOException {        
                Map<Integer, CategoryEntry> map =
                        new TreeMap<Integer, CategoryEntry>();
                String path = Controller.getProperties().getProperty(
                        PropertyKeys.dirTextModules);
                InputStream file = ClassLoader.getSystemResourceAsStream(
                        path + "/textModules" + style + part
                        + area + language + ".xml");
                
                DocumentBuilderFactory factory =
                        DocumentBuilderFactory.newInstance();        
                factory.setIgnoringElementContentWhitespace(true);
                
                DocumentBuilder documentBuilder = factory.newDocumentBuilder();
                Document document = documentBuilder.parse(file);
                Node node = new DOMIterator(document, "textModule").next();
                
                for (Node entry: new DOMIterator(node, "category")) {
                    Node key = entry.getAttributes().getNamedItem("key");
                    Node title = entry.getAttributes().getNamedItem("title");
            
                    if (key != null) {
                        Map<Integer, String> textMap =
                                new TreeMap<Integer, String>();
                        
                        for (Node textEntry: new DOMIterator(entry, "entry")) {
                            Node textKey = textEntry.getAttributes()
                                    .getNamedItem("key");

                            if (textKey != null) {
                                textMap.put(
                                        new Integer(textKey.getTextContent()),
                                                textEntry.getTextContent());
                            }
                        }
                        
                        map.put(new Integer(key.getTextContent()),
                                new CategoryEntry(
                                        title.getTextContent(), textMap));
                    }
                }

                return map;
            }
            
            
            class CategoryEntry {
                String title;
                Map <Integer, String> map;

                private CategoryEntry(String title, Map<Integer, String> map) {
                    this.title = title;
                    this.map = map;
                }
            }
        };
        
        modelT = new ModelTextModulePart() {
            public void loadModel() {
                removeAllElements();
                
                try {
                    ItemTextModuleCategory cat =
                            (ItemTextModuleCategory)modelCat.getSelectedItem();
                    
                    for (ItemTextModule e: cat.getTextModules()) {
                        addElement(e);
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        
        modelC = new ModelTextModulePart() {
            public void loadModel() {
                super.load(partClosing, "", this,
                        Titles.titleTextModuleClosing);
            }
        };

        if (((Item)tsModel.getSelectedItem()).getInternalName()
                .equals(Titles.titleDocumentStyleAd)) {
            inner = new ModelTextModuleAdvertisement();
        }
        else {
            inner = new ModelTextModuleLetterEMail();
        }
        
        addListeners();
    }
    
    public static ModelTextModule getInstance(Controller controller,
            ModelLanguage lModel, ModelCollectingArea cModel,
            ModelDocumentStyle tsModel) {
        if (tmModel == null) {
            tmModel = new ModelTextModule(controller, lModel, cModel, tsModel);
        }
        
        return tmModel;
    }
    
    public void addListeners() {

        tsModel.addListDataListener(
                new ListenerSettingChanged() {
                    
                    public void changed() {
                        inner.changedDocumentStyle();
                    }
                });
        
        lModel.addListDataListener(
                new ListenerSettingChanged() {
                    
                    public void changed() {
                        inner.changedLanguage();
                    }
                });
        
        caModel.addListDataListener(
                new ListenerSettingChanged() {
                    
                    public void changed() {
                        modelSu.loadModel();
                        modelCat.loadModel();
                    }
                });
        
        modelCat.addListDataListener(new ListenerSettingChanged() {
            public void changed() {
                modelT.loadModel();
            }
        });
    }
    
    public void resetTextModules() {
        inner.resetTextModules();
    }
    
    public void resetTextModuleCategory() {
        modelCat.setSelectedItem(modelCat.getElementAt(0));
    }
    
    public void resetTextModuleText() {
        modelT.setSelectedItem(modelT.getElementAt(0));
    }
    
    public void resetTextModuleSubject() {
        modelSu.setSelectedItem(modelSu.getElementAt(0));
    }
    
    public void resetTextModuleSalutation() {
        modelSa.setSelectedItem(modelSa.getElementAt(0));
    }
    
    public void resetTextModuleClosing() {
        modelC.setSelectedItem(modelC.getElementAt(0));
    }
    
    public ItemTextModule getSelectedSentence() {
        return (ItemTextModule)modelT.getSelectedItem();
    }
    
    public Model getModelSubject() {
        return modelSu;
    }
    
    public Model getModelSalutation() {
        return modelSa;
    }
    
    public Model getModelCategory() {
        return modelCat;
    }
    
    public Model getModelText() {
        return modelT;
    }
    
    public Model getModelClosing() {
        return modelC;
    }
    
    
    /**Inner class for chosen a document type advertisement.
     * 
     * @author Antje Huber
     *
     */
    private class ModelTextModuleAdvertisement implements
            InterfaceModelTextModule {
        
        private ModelTextModuleAdvertisement() {
            style = advertisementTitle;
            modelSu.loadModel();
            modelCat.loadModel();
        }
        
        public void changedDocumentStyle() {
            if (((Item)tsModel.getSelectedItem()).getInternalName()
                    .equals(Titles.titleDocumentStyleAd)) {}
            else {
                inner = new ModelTextModuleLetterEMail();
            }
        }
        
        public void changedLanguage() {
            modelSu.loadModel();
            modelCat.loadModel();
        }
        
        public void resetTextModules() {            
            resetTextModuleSubject();
            resetTextModuleCategory();
            resetTextModuleText();
        }
    }
    
    /**Inner class for chosen a document type letter or e-mail.
     * 
     * @author Antje Huber
     *
     */
    private class ModelTextModuleLetterEMail implements
            InterfaceModelTextModule {
                
        private ModelTextModuleLetterEMail() {
            style = "";
            
            modelSu.loadModel();
            modelSa.loadModel();
            modelCat.loadModel();
            modelC.loadModel();
        }
        
        public void changedDocumentStyle() {
            if (((Item)tsModel.getSelectedItem()).getInternalName()
                    .equals(Titles.titleDocumentStyleAd)) {    
                inner = new ModelTextModuleAdvertisement();
            }
            else {}
        }
        
        public void changedLanguage() {        
            modelSu.loadModel();
            modelSa.loadModel();
            modelCat.loadModel();
            modelC.loadModel();
        }
        
        public void resetTextModules() {
            resetTextModuleSubject();
            resetTextModuleSalutation();
            resetTextModuleClosing();
            resetTextModuleCategory();
            resetTextModuleText();
        }
    }
    
    
    /**Interface which provides methods depending of the status.
     * 
     * @author Antje Huber
     *
     */
    private interface InterfaceModelTextModule {
        public void changedDocumentStyle();
        public void changedLanguage();
        public void resetTextModules();
    }
    
    
    /**Abstract class for text modules models for subject etc.
     * 
     * @author Antje Huber
     *
     */ 
    private abstract class ModelTextModulePart extends Model {
        
        private void load(
                String part, String area, Model model, String internalName) {
            try {
                String sourceLanguage =
                        ((ItemLanguagePair)lModel.getSelectedItem())
                        .getSourceLanguage();
                String targetLanguage =
                        ((ItemLanguagePair)lModel.getSelectedItem())
                        .getTargetLanguage();
                
                Map<Integer, String> mapSource =
                    fillMap(part, area, sourceLanguage);
                Map<Integer, String> mapTarget =
                    fillMap(part, area, targetLanguage);
                
                model.removeAllElements();
                
                for (Map.Entry<Integer, String> e: mapSource.entrySet()) {                
                    String valueTarget = mapTarget.get(e.getKey());
                    
                    if (valueTarget != null) {
                        model.addElement(new ItemTextModule(
                                e.getValue(), valueTarget, internalName));
                    }
                }
                
            } catch (Exception exception) {
                exceptionHandler.handleException(
                        Titles.loadTextModuleException, exception);
            }
        }
        
        private Map<Integer, String> fillMap(
                String part, String area, String language) throws
                ParserConfigurationException, SAXException, IOException {        
            Map<Integer, String> map = new TreeMap<Integer, String>();
            String path = Controller.getProperties().getProperty(
                    PropertyKeys.dirTextModules);
            InputStream file = ClassLoader.getSystemResourceAsStream(
                    path + "/textModules" + style + part + area
                    + language + ".xml");
            
            DocumentBuilderFactory factory =
                    DocumentBuilderFactory.newInstance();        
            factory.setIgnoringElementContentWhitespace(true);
            
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            Node node = new DOMIterator(document, "textModule").next();
            
            for (Node entry: new DOMIterator(node, "entry")) {
                Node key = entry.getAttributes().getNamedItem("key");
        
                if (key != null) {
                    map.put(new Integer(
                            key.getTextContent()), entry.getTextContent());
                }
            }

            return map;
        }
        
        public void loadModel() {}
    }
}
