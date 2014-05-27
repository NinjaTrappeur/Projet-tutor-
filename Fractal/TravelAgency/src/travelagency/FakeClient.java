package travelagency;

import java.util.Date;
import message.AgentID;
import message.ConfirmationLetter;
import message.IConfirmationLetter;
import message.IOffer;
import message.IOfferPack;
import message.IOfferRequest;
import message.Offer;
import message.OfferPack;
import message.OfferRequest;
import org.objectweb.fractal.fraclet.annotations.Component;
import org.objectweb.fractal.fraclet.annotations.Interface;
import org.objectweb.fractal.fraclet.annotations.Requires;
import travelagency.interfaces.IVacationOfferBrowser;
import travelagency.interfaces.IVacationReservationManager;

/**
* Fake client component: simulates a client behaviour.
* 
*/
@Component(provides=@Interface(name="r", signature = java.lang.Runnable.class))
public class FakeClient implements Runnable{
    
    @Requires(name="getOffer")
    private IVacationOfferBrowser _getOfferInterface; /*!< Reference to the vacation browser component.*/
    
    @Requires(name="makeReservation")
    private IVacationReservationManager _makeReservationInterface; /*!< Reference to the vacation reservation component.*/
    
    /**
     * Entry point of the application: this will be called by the ADL launcher.
     * 
     * The client behaviour is simulated by this function.
     */
    @Override
    public void run() {
        System.out.println("Émmission d'une requête d'offres.");
        IOfferPack offer = _getOfferInterface.getProposals(new OfferRequest(100, 200, "toto", new Date(), new Date(), "", 0, new AgentID()));
        System.out.println("Résultat: " + offer.toString());
        System.out.print("Émmision d'une requête de validation.");
        IConfirmationLetter letter = _makeReservationInterface.reserveOffer(new Offer(0,"", new AgentID()));
        System.out.println("Résultat: " + letter.toString());
    }
    
    /**
     * Return the offer avalaible for a request using the vacation browser component.
     * 
     * @param request Offer request.
     * @return List of offers that match with the request given in parameter.
     */
    public OfferPack getOffers(IOfferRequest request){
        IOfferPack offerPack = _getOfferInterface.getProposals(request);
        if(!(offerPack instanceof OfferPack))
            throw(new ClassCastException("Client.getOffers: invalid cast: result is not OfferPack."));
        else
            return (OfferPack) offerPack;
    }
    
    /**
     * Make a reservation for an Offer using the vacation reservation component.
     * 
     * @param offer Offer that will be reserved by the client.
     * @return Confirmation of the reservation.
     */
    public ConfirmationLetter makeReservation(IOffer offer){
        IConfirmationLetter confirmationLetter = _makeReservationInterface.reserveOffer(offer);
        if(!(confirmationLetter instanceof ConfirmationLetter))
            throw(new ClassCastException("Client.makeReservation: invalid cast: result is not ConfirmationLetter."));
        else
            return (ConfirmationLetter) confirmationLetter;    
    }
}
