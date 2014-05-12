/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package travelagency;

import message.IConfirmationLetter;
import message.IOffer;
import messages.ConfirmationLetter;
import travelagency.interfaces.IVacationReservationManager;


@Component(provides=@Interface(name="vacationReservationManager", signature = travelagency.interfaces.IVacationReservationManager.class))
public class VacationReservationManager implements IVacationReservationManager{
        @Override
        public IConfirmationLetter reserveOffer(IOffer offer) {
            return new ConfirmationLetter();
        }

}
