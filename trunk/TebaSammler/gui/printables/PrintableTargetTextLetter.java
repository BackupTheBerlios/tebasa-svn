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


package gui.printables;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

import javax.swing.JComponent;

/**Printable for printing a letter.
 * 
 * @author Antje Huber
 *
 */
public class PrintableTargetTextLetter implements Printable {
    
    private JComponent addresser;
    private JComponent addressee;
    private JComponent date;
    private JComponent text;
    
    public PrintableTargetTextLetter(JComponent addressee, JComponent addresser,
            JComponent date, JComponent text) {
        this.addressee = addressee;
        this.addresser = addresser;
        this.date = date;
        this.text = text;        
    }        
    
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {                
        if (pageIndex >= 1) {
            return Printable.NO_SUCH_PAGE;
        }
        
        printAddresser(g, pageFormat);
        printAddressee(g, pageFormat);
        printDate(g, pageFormat);
        printText(g, pageFormat);
        return Printable.PAGE_EXISTS; 
    }
            
    private void printAddresser(Graphics g, PageFormat pageFormat) {        
        Point point  = new Point((int)pageFormat.getImageableX()
                + (int)pageFormat.getImageableWidth() - addresser.getWidth(),
                (int)pageFormat.getImageableY());
        
        g.translate(point.x, point.y);
        addresser.print(g);
        g.translate(-point.x, -point.y);
    }
        
    private void printAddressee(Graphics g, PageFormat pageFormat) {
        Point point = new Point((int)pageFormat.getImageableX(), 139);

        g.translate(point.x, point.y);
        addressee.print(g);
        g.translate(-point.x, -point.y);
    }
    
    private void printDate(Graphics g, PageFormat pageFormat) {
        Point point = new Point((int)pageFormat.getImageableX()
                + (int)pageFormat.getImageableWidth() - date.getWidth(), 227);
        
        g.translate(point.x, point.y);
        date.print(g);
        g.translate(-point.x, -point.y);
    }
    
    private void printText(Graphics g, PageFormat pageFormat) {
        Point point = new Point((int)pageFormat.getImageableX(), 283);
        Dimension dimensionOld = text.getSize();
        Dimension dimensionNew = new Dimension(
                (int)pageFormat.getImageableWidth(),
                (int)pageFormat.getImageableHeight() - 283);
        
        g.translate(point.x, point.y);
        text.setSize(dimensionNew);
        text.print(g);
        text.setSize(dimensionOld);
        g.translate(-point.x, -point.y);
    }
}
