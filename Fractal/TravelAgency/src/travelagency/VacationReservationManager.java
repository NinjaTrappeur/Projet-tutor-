package travelagency;

import message.ConfirmationLetter;
import message.IConfirmationLetter;
import message.IOffer;
import org.objectweb.fractal.fraclet.annotations.Component;
import org.objectweb.fractal.fraclet.annotations.Interface;
import travelagency.interfaces.IVacationReservationManager;

@Component(provides=@Interface(name="reserveOffer", signature = travelagency.interfaces.IVacationReservationManager.class))
public class VacationReservationManager implements IVacationReservationManager{
        @Override
        public IConfirmationLetter reserveOffer(IOffer offer) {
            System.out.println("Validation de l'offre: " + offer.toString());
            return new ConfirmationLetter();
        }

}
