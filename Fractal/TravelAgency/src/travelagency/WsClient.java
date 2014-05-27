package travelagency;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import javax.xml.ws.Endpoint;
import message.ConfirmationLetter;
import message.IConfirmationLetter;
import message.IOffer;
import message.IOfferPack;
import message.IOfferRequest;
import message.OfferPack;
import org.objectweb.fractal.fraclet.annotations.Component;
import org.objectweb.fractal.fraclet.annotations.Interface;
import org.objectweb.fractal.fraclet.annotations.Requires;
import remote.IServiceProvider;
import travelagency.interfaces.IVacationOfferBrowser;
import travelagency.interfaces.IVacationReservationManager;

/**
 * This component export the services given by the vacation browser and the
 * vacation reservation manager in webservices.
 * 
 */
@Component(provides=@Interface(name="r", signature = java.lang.Runnable.class))
public class WsClient implements Runnable, IServiceProvider{
    
    @Requires(name="getOffer")
    private IVacationOfferBrowser _getOfferInterface; /*!< Reference to the vacation browser component.*/
    
    @Requires(name="makeReservation")
    private IVacationReservationManager _makeReservationInterface; /*!< Reference to the vacation reservation component.*/
    
    /**
     * Entry point of the application, will be launched by the ADL launcher.
     * 
     * This function creates a webservice (WsLink) and publish him on localhost:8000/Ws.
     */
    @Override
    public void run() {
        String localIp = "http://";
        WsLink webServiceLink = new WsLink(this);
        try {
            localIp = localIp.concat(Inet4Address.getLocalHost().getHostAddress()); 
        }
        catch(UnknownHostException e){
            System.err.println(e);
        }
        localIp = localIp.concat(":8000/Ws");
        Endpoint.publish(localIp, webServiceLink);
        System.out.println("Webservice lancé sur " + localIp);
    }
    
    /**
     * Return the offer avalaible for a request using the vacation browser component.
     * 
     * @param request Offer request.
     * @return List of offers that match with the request given in parameter.
     */
    @Override
    public OfferPack requestProposal(IOfferRequest request){
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
    @Override
    public ConfirmationLetter reserveOffer(IOffer offer){
        IConfirmationLetter confirmationLetter = _makeReservationInterface.reserveOffer(offer);
        if(!(confirmationLetter instanceof ConfirmationLetter))
            throw(new ClassCastException("Client.makeReservation: invalid cast: result is not ConfirmationLetter."));
        else
            return (ConfirmationLetter) confirmationLetter;    
    }
}
