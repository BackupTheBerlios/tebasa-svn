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


package model.listenerNotifier;

import controller.Notifier;

/**Notifier for changes concerning the addresser.
 * 
 * @author Antje Huber
 *
 */
public class NotifierAddresserChanged extends Notifier {
    
    public void fireStatusChanged() {
        for (Object e: list) {
            ((ListenerAddresserChanged)e).addresserChanged();
        }
    }
}
