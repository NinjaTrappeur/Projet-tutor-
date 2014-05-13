package travelagency;

import java.util.Date;
import javax.xml.ws.Endpoint;
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



@Component(provides=@Interface(name="r", signature = java.lang.Runnable.class))
public class Client implements Runnable{
    
    @Requires(name="getOffer")
    private IVacationOfferBrowser _getOfferInterface;
    
    @Requires(name="makeReservation")
    private IVacationReservationManager _makeReservationInterface;
    
    @Override
    public void run() {
        WsLink webServiceLink = new WsLink(this);
        Endpoint.publish("http://10.8.2.129:8000/Ws", webServiceLink);
        System.out.println("Webservice lancé sur http://localhost:8000/Ws");
//        System.out.println("Émmission d'une requête d'offres.");
//        IOfferPack offer = _getOfferInterface.getProposals(new OfferRequest(100, 200, "toto", new Date(), new Date(), "", 0));
//        System.out.println("Résultat: " + offer.toString());
//        System.out.print("Émmision d'une requête de validation.");
//        IConfirmationLetter letter = _makeReservationInterface.reserveOffer(new Offer(0,""));
//        System.out.println("Résultat: " + letter.toString());
    }
    
    public OfferPack getOffers(OfferRequest request){
        IOfferPack offerPack = _getOfferInterface.getProposals(request);
        if(!(offerPack instanceof OfferPack))
            throw(new ClassCastException("Client.getOffers: invalid cast: result is not OfferPack."));
        else
            return (OfferPack) offerPack;
    }
    
    public ConfirmationLetter makeReservation(IOffer offer){
        IConfirmationLetter confirmationLetter = _makeReservationInterface.reserveOffer(offer);
        if(!(confirmationLetter instanceof ConfirmationLetter))
            throw(new ClassCastException("Client.makeReservation: invalid cast: result is not ConfirmationLetter."));
        else
            return (ConfirmationLetter) confirmationLetter;    
    }
}
