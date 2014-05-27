package travelagency.interfaces;

import message.IConfirmationLetter;
import message.IOffer;

/**
 * Interface implemented by the VacationReservationManager component.
 */
public interface IVacationReservationManager {
    public IConfirmationLetter reserveOffer(IOffer offer);
}
