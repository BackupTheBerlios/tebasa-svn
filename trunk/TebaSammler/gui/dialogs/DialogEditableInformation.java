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


package gui.dialogs;

import gui.listenerNotifier.NotifierTextEditable;

import javax.swing.JOptionPane;

import controller.Controller;
import controller.Titles;
import controller.commands.Command;

/**Class which creates a JOptionPane with information about the editable state
 * of the panelTargetText. In case of "Yes" the command will be executed and
 * editable in panelTargetText will be set as false.
 * 
 * @author Antje Huber
 *
 */
public class DialogEditableInformation {
        
    public DialogEditableInformation(Controller controller, Titles titles,
            Command command, NotifierTextEditable editableNotifier) {
        Object options[] = {
                titles.getString(Titles.buttonTitleDialogEditableYes),
                titles.getString(Titles.buttonTitleDialogEditableNo)};
        int answer = JOptionPane.showOptionDialog(
                controller.getUI(),
                titles.getString(Titles.dialogTitleTextEditable),
                titles.getString(Titles.dialogTitleEditable),
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        
        if(answer == JOptionPane.YES_OPTION) {
            controller.commitCommand(command);
            editableNotifier.fireEditableChanged(false);
        }
   }
}
