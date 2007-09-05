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


package gui.panels;

import gui.Button;
import gui.GUI;
import gui.Mnemonics;
import gui.GUI.Status;
import gui.dialogs.DialogChooseAddresses;
import gui.listenerNotifier.ListenerGUIState;
import gui.listenerNotifier.ListenerSettingChanged;
import gui.listenerNotifier.ListenerTextEditable;
import gui.listenerNotifier.NotifierTextEditable;
import gui.printables.PrintableTargetTextAdEMail;
import gui.printables.PrintableTargetTextLetter;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.Models;
import model.items.Item;
import model.items.ItemTextModule;
import model.listenerNotifier.ListenerAddresseeChanged;
import model.listenerNotifier.ListenerAddresserChanged;
import model.models.ModelAddress;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import controller.Controller;
import controller.DOMIterator;
import controller.DocumentOperations;
import controller.EMail;
import controller.Titles;
import controller.commands.CommandSetItemModelTextStyle;
import controller.factories.DocumentBuilder;

/**Class for the panel which contains the components for the target text.
 * The components on the panel differ from the document type. For this there
 * are inner classes which match the document type.
 * 
 * @author Antje Huber
 *
 */
public class PanelTargetText extends JPanel implements ListenerAddresseeChanged,
        ListenerAddresserChanged, ListenerTextEditable, ListenerGUIState,
        DocumentOperations, EMail {
    
    private enum nodeNames {saved, style, subject,
        fileEditorPane, addresser, addressee, date};
    
    private IPanelTargetText inner;
    private DocumentBuilder builder;
    
    private JTextPane subjectArea;
    private JEditorPane textArea;
    private JPanel panelButtons;
    
    private GridBagConstraints gbcPanelButtons;
    private SimpleAttributeSet attributesAlignLeft;
    private ModelAddress aModel;
    
    private Controller controller;
    private Titles titles;
    private Models models;
    private ComboBoxModel ctmModel;
    private NotifierTextEditable editableNotifier;
    private Mnemonics mnemonics;
    
    public PanelTargetText(Controller controller, Titles titles, Models models,
            ComboBoxModel ctmModel, NotifierTextEditable editableNotifier,
            Mnemonics mnemonics) {
        this.controller = controller;
        this.titles = titles;
        this.models = models;
        this.ctmModel = ctmModel;
        this.mnemonics = mnemonics;
        this.editableNotifier = editableNotifier;
        
        try {
            setBorder(controller.getTitledBorder(Titles.borderTitleTargetText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        setLayout(new GridBagLayout());
        
        attributesAlignLeft = new SimpleAttributeSet();
        StyleConstants.setFontSize(attributesAlignLeft, 12);
        StyleConstants.setFontFamily(attributesAlignLeft, "sans");
        
        textArea = new JEditorPane();
        textArea.setEditable(false);
        textArea.setContentType("text/rtf");
        
        build();
        
        //Listener
        try {
            ctmModel.addListDataListener(
                    new ChosenTextModulesModelChangedListener());
            
            models.get(Titles.titleLanguageM).addListDataListener(
                    new ListenerSettingChanged() {
                        public void changed() {
                            builder.languageChanged();
                            inner.languageChanged();
                        }
                    });
            
            models.get(Titles.titleDocumentStyleM).addListDataListener(
                    new ListenerSettingChanged() {
                        
                        public void changed() {
                            removeAll();
                            build();
                            validate();
                        }
                    });
            
            aModel = (ModelAddress)models.get(Titles.titleAddressM); 
            aModel.registerForAddresseeChangedNotifier(this);
            aModel.registerForAddresserChangedNotifier(this);
            
            editableNotifier.add(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**This method builds a part of the components on the panel, decides which
     * status will be used and creates an object of class DocumentBuilder. The
     * other parts of the components will be created depending on the status
     * (call inner.build()).
     * */
    private void build() {
        
        gbcPanelButtons = new GridBagConstraints();
        gbcPanelButtons.fill = GridBagConstraints.BOTH;
        gbcPanelButtons.anchor = GridBagConstraints.CENTER;
        gbcPanelButtons.gridwidth = GridBagConstraints.REMAINDER;
        
        GridBagConstraints gbcButtonEdit = new GridBagConstraints();
        gbcButtonEdit.weightx = 2;
        gbcButtonEdit.anchor = GridBagConstraints.WEST;
        gbcButtonEdit.insets.set(15, 60, 15, 10);
        
        GridBagConstraints gbcButtonAddress = new GridBagConstraints();
        gbcButtonAddress.weightx = 1;
        gbcButtonAddress.anchor = GridBagConstraints.EAST;
        gbcButtonAddress.insets.set(15, 10, 15, 40);
        
        try {
            String textStyle = ((Item)models.get(Titles.titleDocumentStyleM)
                    .getSelectedItem()).getInternalName();
            
            if (textStyle.equals(Titles.titleDocumentStyleLetter)) {
                inner = new PanelTargetTextLetter();
                builder = new DocumentBuilder(controller, titles, models,
                        ctmModel, Titles.titleDocumentStyleLetter);
            }
            else if (textStyle.equals(Titles.titleDocumentStyleAd) ||
                    textStyle.equals(Titles.titleDocumentStyleEMail)) {            
                inner = new PanelTargetTextAdEMail();
                
                if (textStyle.equals(Titles.titleDocumentStyleAd)) {
                    builder = new DocumentBuilder(controller, titles, models,
                            ctmModel, Titles.titleDocumentStyleAd);
                }
                else if (textStyle.equals(Titles.titleDocumentStyleEMail)) {
                    builder = new DocumentBuilder(controller, titles, models,
                            ctmModel, Titles.titleDocumentStyleEMail);
                }
            }
            
            panelButtons = new JPanel(new GridBagLayout());
            panelButtons.setBorder(
                    BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    
            JButton buttonEdit = new Button(titles.getString(
                    Titles.buttonTitleEditTargetText), mnemonics,
                    new ActionListenerEdit(editableNotifier));
            panelButtons.add(buttonEdit, gbcButtonEdit);
            
            JButton buttonAddress = new Button(titles.getString(
                    Titles.buttonTitleAddressTargetText), mnemonics,
                    new ActionListenerAddress(controller, titles, models));
            panelButtons.add(buttonAddress, gbcButtonAddress);
            
            inner.build();
            inner.setHeaderNew();
            setTextNew();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**Adds the always added components for textArea and buttons.*/
    private void addCommons() {
        GridBagConstraints gbcText = new GridBagConstraints();
        gbcText.anchor = GridBagConstraints.CENTER;
        gbcText.gridwidth = GridBagConstraints.REMAINDER;
        gbcText.fill = GridBagConstraints.BOTH;
        gbcText.weightx = 3;
        gbcText.weighty = 3;
        
        add(new JScrollPane(textArea), gbcText);        
        add(panelButtons, gbcPanelButtons);
    }
    
    /**Sets the text of a JEditorPane.
     * 
     * @param content
     * @param pane
     * @param attributes
     * @throws BadLocationException
     */
    private void setAreaText(String content, JEditorPane pane,
            AttributeSet attributes) throws BadLocationException {
        StyledDocument document = (StyledDocument)pane.getDocument();

        document.remove(0, document.getLength());
        document.insertString(0, content, attributes);
        document.setParagraphAttributes(
                0, document.getLength()-1, attributes, false);            
    }
    
    /**Returns the text of a JEditorPane.*/
    private String getAreaText(JEditorPane pane) throws BadLocationException {
        javax.swing.text.Document document = pane.getDocument();
        
        return document.getText(0, document.getLength());
    }
    
    /**Sets the text of the field for document text.
     * 
     * @param content
     */
    private void setText(String content) {
        try {
            setAreaText(content, textArea, attributesAlignLeft);
        } catch (BadLocationException e) {
            controller.handleException(Titles.setTextException, e);
        }
    }
    
    /**Sets the text of the field for document text in case of GUI status new.*/
    private void setTextNew() {
        setText(builder.buildText(ctmModel));
    }
    
    private String getSubjectFromModel() {        
        for (int i = 0; i < ctmModel.getSize(); i++) {            
            ItemTextModule textModule =
                (ItemTextModule)ctmModel.getElementAt(i);
            
            if (textModule.getInternalName()
                    .equals(Titles.titleTextModuleSubject)) {
                return textModule.getTargetString();
            }
        }
        
        return "";
    }
    
    public void guiStatusChanged(Status status) {
        if (status != null && status.equals(GUI.Status.NEW)) {            
            inner.setHeaderNew();            
            setTextNew();
        }
    }
    
    public void addresseeChanged() {
        builder.addresseeChanged();
        inner.addresseeChanged();
    }
    
    public void addresserChanged() {
        builder.addresserChanged();
        inner.addresserChanged();
        
        if (controller.getUI().getStatus().equals(GUI.Status.NEW)) {
            setTextNew();
        }
    }
    
    /**Method for saving the document in file.*/
    public void save(FileOutputStream out) {        
        try {            
            DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();            
            factory.setIgnoringElementContentWhitespace(true);
            
            javax.xml.parsers.DocumentBuilder documentBuilder =
                factory.newDocumentBuilder();
            
            Document document = documentBuilder.newDocument();
            
            fillDocument(document);

            TransformerFactory tFactory = TransformerFactory.newInstance();
            tFactory.setAttribute("indent-number", new Integer(4));
            
            Transformer transformer = tFactory.newTransformer();
            
            transformer.setOutputProperty("indent", "yes");
            
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(out);
            
            transformer.transform(source, result);
        } catch (Exception e) {
            controller.handleException(Titles.saveTextException, e);
        }
    }
    
    private void fillDocument(Document document) {
        try {
            Element root = document.createElement(nodeNames.saved.toString());
            
            //Part style
            Element elementStyle =
                document.createElement(nodeNames.style.toString());
            elementStyle.appendChild(
                    document.createTextNode(((Item)controller.getModels().get(
                            Titles.titleDocumentStyleM)
                                    .getSelectedItem()).getInternalName()));
            root.appendChild(elementStyle);
            
            //Part others
            inner.fillDocument(document, root);
            
            //Part file
            Element elementText =
                document.createElement(nodeNames.fileEditorPane.toString());        
            
            elementText.appendChild(
                    document.createTextNode(getAreaText(textArea)));
            
            root.appendChild(elementText);
            
            document.appendChild(root);
        } catch (Exception e) {
            controller.handleException(Titles.getTextException, e);            
        }
    }
    
    /**Method needed for showing text of an opened document.*/
    public void open(File file) {
        try {
            DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);
            
            javax.xml.parsers.DocumentBuilder documentBuilder =
                factory.newDocumentBuilder();            
            Document document = documentBuilder.parse(file);            
            Node node = new DOMIterator(
                    document, nodeNames.saved.toString()).next();
            
            for (Node entry: new DOMIterator(node, null)) {                    
                if (entry.getNodeName().equals(nodeNames.style.toString())) {
                    controller.commitCommand(new CommandSetItemModelTextStyle(
                            new Item(entry.getTextContent(), titles)));
                    inner.openText(entry.getTextContent());
                }
                else if (entry.getNodeName().equals(
                        nodeNames.fileEditorPane.toString())) {
                    setAreaText(entry.getTextContent(), textArea,
                            attributesAlignLeft);
                }
                else {
                    inner.setHeaderOpen(
                            entry.getNodeName(), entry.getTextContent());
                }
            }            
        } catch (Exception e) {
            controller.handleException(Titles.openTextException, e);
        }
    }
    
    /**Method for printing document text.*/
    public void print() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = printerJob.defaultPage();
        Paper paper = pageFormat.getPaper();
        
        paper.setImageableArea(68, 28, paper.getWidth() - 68 - 56 ,
                paper.getHeight() - 28 - 56);
        pageFormat.setPaper(paper);
        
        if (printerJob.printDialog()) {
            inner.setPrintable(printerJob, pageFormat);
            
            try {
                printerJob.print();
            } catch (PrinterException e) {
                controller.handleException(Titles.printTextException, e);
            }
        }
    }
    
    public String getRecipient() {
        try {
            return aModel.getAddressee().getElementsString()
                .get(Titles.titleEmailAddressA);
        }
        catch (Exception e) {
            e.printStackTrace();
            
            return "";
        }
    }
    
    public String getSubject() {
        return inner.getSubject();
    }
    
    public String getText() {
        try {
            return getAreaText(textArea);
        } catch (BadLocationException e) {
            controller.handleException(Titles.getTextException, e);
            
            return "";
        }
    }
    
    public void editableChanged(boolean editable) {
        textArea.setEditable(editable);
        textArea.requestFocusInWindow();        
        textArea.setCaretPosition(0);
        
        inner.editableChanged(editable);
    }
    
    
    /**Inner class for status letter. So an object of it will be created in
     * case of a letter as choosed document style.
     * 
     * @author Antje Huber
     *
     */
    private class PanelTargetTextLetter implements IPanelTargetText {
                
        private JTextPane addresserArea;
        private JTextPane addresseeArea;
        private JTextPane dateArea;
        
        private SimpleAttributeSet attributesAlignRight;
        
        public void build() {            
            removeAll();
                        
            GridBagConstraints gbcAddressee = new GridBagConstraints();
            gbcAddressee.weightx = 1.5;
            gbcAddressee.weighty = 0.7;
            gbcAddressee.fill = GridBagConstraints.BOTH;
            
            GridBagConstraints gbcAddresser = new GridBagConstraints();
            gbcAddresser.gridwidth = GridBagConstraints.REMAINDER;
            gbcAddresser.weightx = 1.0;
            gbcAddresser.weighty = 0.7;
            gbcAddresser.fill = GridBagConstraints.BOTH;
            
            GridBagConstraints gbcDate = new GridBagConstraints();
            gbcDate.gridwidth = GridBagConstraints.REMAINDER;
            gbcDate.weightx = 0.1;
            gbcDate.weighty = 0.05;
            gbcDate.fill = GridBagConstraints.BOTH;
            
            if (attributesAlignRight == null) {
                attributesAlignRight = new SimpleAttributeSet();
                
                StyleConstants.setFontSize(attributesAlignRight, 12);                
                StyleConstants.setFontFamily(attributesAlignRight, "sans");
                StyleConstants.setAlignment(
                        attributesAlignRight, StyleConstants.ALIGN_RIGHT);
            }
            
            if (addresseeArea == null) {
                addresseeArea = new JTextPane();
            }            
            add(new JScrollPane(addresseeArea), gbcAddressee);
            
            if (addresserArea == null) {
                addresserArea = new JTextPane();
            }
            add(new JScrollPane(addresserArea), gbcAddresser);
            
            if (dateArea == null) {
                dateArea = new JTextPane();
            }
            add(new JScrollPane(dateArea), gbcDate);
            
            addCommons();
        }
        
        public void setHeaderNew() {
            setAddressee(builder.buildAddressee());
            setAddresser(builder.buildAddresser());
            setDate(builder.buildDate());
        }
        
        public void setHeaderOpen(String name, String content) {
            if (name.equals(nodeNames.addressee.toString())) {
                setAddressee(content);
            }
            else if (name.equals(nodeNames.addresser.toString())) {
                setAddresser(content);
            }
            else if (name.equals(nodeNames.date.toString())) {
                setDate(content);
            }
        }
        
        public void fillDocument(Document document, Element root) {
            //Part addressee
            Element elementAddressee =
                document.createElement(nodeNames.addressee.toString());
            elementAddressee.appendChild(
                    document.createTextNode(getAddressee()));
            root.appendChild(elementAddressee);
            
            //Part addresser
            Element elementAddresser =
                document.createElement(nodeNames.addresser.toString());
            elementAddresser.appendChild(
                    document.createTextNode(getAddresser()));
            root.appendChild(elementAddresser);
            
            //Part date
            Element elementDate =
                document.createElement(nodeNames.date.toString());
            elementDate.appendChild(document.createTextNode(getDate()));
            root.appendChild(elementDate);
        }
        
        public void openText(String style) {
            if (style.equals(Titles.titleDocumentStyleAd)
                    || style.equals(Titles.titleDocumentStyleEMail)) {
                inner = new PanelTargetTextAdEMail();
                
                inner.build();
            }
        }
        
        private void setAddressee(String s) {
            try {
                setAreaText(s, addresseeArea, attributesAlignLeft);
            } catch (BadLocationException e) {
                controller.handleException(Titles.setAddresseeException, e);
            }
        }
        
        private void setAddresser(String s) { 
            try {
                setAreaText(s, addresserArea, attributesAlignRight);
            } catch (BadLocationException e) {
                controller.handleException(Titles.setAddresserException, e);
            }
        }
        
        private void setDate(String s) {
            try {
                setAreaText(s, dateArea, attributesAlignRight);
            } catch (BadLocationException e) {
                controller.handleException(Titles.setDateException, e);
            }
        }
        
        private String getAddressee() {
            try {
                return getAreaText(addresseeArea);
            } catch (BadLocationException e) {
                controller.handleException(Titles.getAddresseeException, e);
                
                return "";
            }
        }
        
        private String getAddresser() {
            try {
                return getAreaText(addresserArea);
            } catch (BadLocationException e) {
                controller.handleException(Titles.getAddresserException, e);
                
                return "";
            }
        }
        
        private String getDate() {
            try {
                return getAreaText(dateArea);
            } catch (BadLocationException e) {
                controller.handleException(Titles.getDateException, e);
                
                return "";
            }
        }
        
        public void setSubject(String content) {}
        
        public String getSubject() {
            return "";
        }
        
        public void setPrintable(PrinterJob printerJob, PageFormat pageFormat) {       
            printerJob.setPrintable(new PrintableTargetTextLetter(addresseeArea,
                    addresserArea, dateArea, textArea), pageFormat);
        }
        
        public void languageChanged() {
            setDate(builder.buildDate());
        }
        
        public void addresseeChanged() {
            try {
                setAreaText(builder.buildAddressee(), addresseeArea,
                        attributesAlignLeft);
            } catch (BadLocationException e) {
                controller.handleException(Titles.setAddresseeException, e);
            }
        }
        
        public void addresserChanged() {
            try {
                setAreaText(builder.buildAddresser(), addresserArea,
                        attributesAlignRight);
                setAreaText(builder.buildDate(), dateArea,
                        attributesAlignRight);
            } catch (BadLocationException e) {
                controller.handleException(Titles.setAddresserException, e);
            }
        }
        
        public void editableChanged(boolean editable) {
            
        }
    }
    
    
    /**Inner class for status advertisement or e-mail.
     * So an object of it will be created in case of an advertisment or
     * e-mail as choosed document style.
     * 
     * @author Antje Huber
     *
     */
    private class PanelTargetTextAdEMail implements IPanelTargetText {
        
        public void build() {            
            removeAll();
            
            setText("");

            GridBagConstraints gbcSubject = new GridBagConstraints();
            gbcSubject.gridwidth = GridBagConstraints.REMAINDER;
            gbcSubject.weightx = 0.1;
            gbcSubject.weighty = 0.025;
            gbcSubject.fill = GridBagConstraints.BOTH;
            
            if (subjectArea == null) {
                subjectArea = new JTextPane();
                
                subjectArea.setEditable(false);                
            }           
            add(new JScrollPane(subjectArea), gbcSubject);
            
            addCommons();
        }
        
        public void setHeaderNew() {
            setSubject(getSubjectFromModel());
        }
        
        public void setHeaderOpen(String name, String content) {
            if (name.equals(nodeNames.subject.toString())) {
                setSubject(content);
            }
        }
        
        public void fillDocument(Document document, Element root) {
            //Part subject
            Element elementSubject =
                document.createElement(nodeNames.subject.toString());
            elementSubject.appendChild(
                    document.createTextNode(getSubject()));
            root.appendChild(elementSubject);
        }
        
        public void openText(String style) {
            if (style.equals(Titles.titleDocumentStyleLetter)) {
                inner = new PanelTargetTextLetter();
                
                inner.build();
            }
        }
        
        public void setSubject(String content) {
            try {
                setAreaText(content, subjectArea, attributesAlignLeft);
            } catch (BadLocationException e) {
                controller.handleException(Titles.setSubjectException, e);
            }
        }
        
        public String getSubject() {
            try {
                return getAreaText(subjectArea);
            } catch (BadLocationException e) {
                controller.handleException(Titles.getSubjectException, e);
                return "";
            }
        }
        
        public void setPrintable(PrinterJob printerJob, PageFormat pageFormat) {
            printerJob.setPrintable(new PrintableTargetTextAdEMail(
                    subjectArea, textArea), pageFormat);            
        }
        
        public void languageChanged() {}
        
        public void addresseeChanged() {}
        
        public void addresserChanged() {}
        
        public void editableChanged(boolean editable) {
            subjectArea.setEditable(editable);
        }
    }
    
    
    /**Interface for different status of the panel. (State Pattern)
     * 
     * @author Antje Huber
     *
     */
    private interface IPanelTargetText {
        public void build();
        public void setHeaderNew();
        public void setHeaderOpen(String name, String content);
        public void setSubject(String content);
        public String getSubject();
        public void fillDocument(Document document, Element root);
        public void openText(String style);
        public void setPrintable(PrinterJob printerJob, PageFormat pageFormat);
        public void languageChanged();
        public void addresseeChanged();
        public void addresserChanged();
        public void editableChanged(boolean editable);
    }
    
    
    /**ListDataListener for observing model ChosenTextModules. In case of
     * changes the text of the document will be changed also.
     * 
     * @author Antje Huber
     *
     */
    private class ChosenTextModulesModelChangedListener implements
            ListDataListener {
        
        public void intervalAdded(final ListDataEvent e) {
            changed();
        }
        
        public void intervalRemoved(final ListDataEvent e) {
            changed();
        }
        
        public void contentsChanged(ListDataEvent e) {}
        
        private void changed() {
            inner.setSubject(getSubjectFromModel());
            
            setTextNew();
        }
    }
    
    
    /**Action Listener for ButtonEdit. If it is pressed the text can be edited
     * by the user.
     * 
     * @author Antje Huber
     *
     */
    private class ActionListenerEdit implements ActionListener {
        
        NotifierTextEditable editableNotifier;
        
        private ActionListenerEdit(NotifierTextEditable editableNotifier) {
            this.editableNotifier = editableNotifier;
        }        
        
        public void actionPerformed(ActionEvent e) {
            editableNotifier.fireEditableChanged(true);
        }   
    }
    
    
    /**ActionListener for button change addressee or addresser. If it is
     * pressed a dialog for changing pop up.
     * 
     * @author Antje Huber
     *
     */
    private class ActionListenerAddress implements ActionListener {
        
        private Controller controller;
        private Titles titles;
        private Models models;
        
        private ActionListenerAddress(
                Controller controller, Titles titles, Models models) {
            this.controller = controller;
            this.titles = titles;
            this.models = models;
        }
        
        public void actionPerformed(ActionEvent e) {
            new DialogChooseAddresses(
                    controller, titles, models, controller.getUI());            
        }
    }
}
