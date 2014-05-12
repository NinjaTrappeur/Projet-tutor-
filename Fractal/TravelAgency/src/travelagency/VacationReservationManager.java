/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package travelagency;

import message.ConfirmationLetter;
import message.IConfirmationLetter;
import message.IOffer;
import travelagency.interfaces.IVacationReservationManager;
import org.objectweb.fractal.fraclet.annotations.Component;
import org.objectweb.fractal.fraclet.annotations.Interface;


@Component(provides=@Interface(name="vacationReservationManager", signature = travelagency.interfaces.IVacationReservationManager.class))
public class VacationReservationManager implements IVacationReservationManager{
        @Override
        public IConfirmationLetter reserveOffer(IOffer offer) {
            return new ConfirmationLetter();
        }

}
