package travelagency;

import java.util.Date;
import message.IConfirmationLetter;
import message.IOffer;
import message.IOfferPack;
import message.IOfferRequest;
import message.Offer;
import message.OfferRequest;
import org.objectweb.fractal.fraclet.annotations.Component;
import org.objectweb.fractal.fraclet.annotations.Interface;
import org.objectweb.fractal.fraclet.annotations.Requires;
import travelagency.interfaces.IVacationOfferBrowser;
import travelagency.interfaces.IVacationReservationManager;



@Component(provides=@Interface(name="r", signature = java.lang.Runnable.class))
public class Client implements Runnable{
    
    @Requires(name="getOffer")
    private IVacationOfferBrowser _getOfferInterface;
    
    @Requires(name="makeReservation")
    private IVacationReservationManager _makeReservationInterface;
    
    @Override
    public void run() {
        System.out.println("Émmission d'une requête d'offres.");
        IOfferPack offer = _getOfferInterface.getProposals(new OfferRequest(100, 200, "toto", new Date(), new Date(), "", 0));
        System.out.println("Résultat: " + offer.toString());
        System.out.print("Émmision d'une requête de validation.");
        IConfirmationLetter letter = _makeReservationInterface.reserveOffer(new Offer(0,""));
        System.out.println("Résultat: " + letter.toString());
    }
    
    public IOfferPack getOffers(IOfferRequest request){
        return _getOfferInterface.getProposals(request);
    }
    
    public IConfirmationLetter makeReservation(IOffer offer){
        return _makeReservationInterface.reserveOffer(offer);
    }
}
