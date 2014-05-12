/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package travelagency.interfaces;

import message.IConfirmationLetter;
import message.IOffer;

/**
 *
 * @author ninjatrappeur
 */
public interface IVacationReservationManager {
    public IConfirmationLetter reserveOffer(IOffer offer);
}
