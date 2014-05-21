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



@Component(provides=@Interface(name="r", signature = java.lang.Runnable.class))
public class Client implements Runnable, IServiceProvider{
    
    @Requires(name="getOffer")
    private IVacationOfferBrowser _getOfferInterface;
    
    @Requires(name="makeReservation")
    private IVacationReservationManager _makeReservationInterface;
    
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
    
    @Override
    public OfferPack requestProposal(IOfferRequest request){
        IOfferPack offerPack = _getOfferInterface.getProposals(request);
        if(!(offerPack instanceof OfferPack))
            throw(new ClassCastException("Client.getOffers: invalid cast: result is not OfferPack."));
        else
            return (OfferPack) offerPack;
    }
   
    @Override
    public ConfirmationLetter reserveOffer(IOffer offer){
        IConfirmationLetter confirmationLetter = _makeReservationInterface.reserveOffer(offer);
        if(!(confirmationLetter instanceof ConfirmationLetter))
            throw(new ClassCastException("Client.makeReservation: invalid cast: result is not ConfirmationLetter."));
        else
            return (ConfirmationLetter) confirmationLetter;    
    }
}
