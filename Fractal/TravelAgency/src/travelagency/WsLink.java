package travelagency;

import javax.jws.WebParam;
import javax.jws.WebService;


@WebService
public class WsLink {
    private Client _client;
    
    public WsLink(Client client) {
        _client = client;
    }
    
    public WsLink(){
        
    }
    
    public webservice.message.OfferPack getOffers(@WebParam(name = "offerRequest")webservice.message.OfferRequest offerRequest){
        return new webservice.message.OfferPack(new webservice.message.Offer(0,"",""));
    }
    
    public webservice.message.ConfirmationLetter makeReservation(@WebParam(name = "offer")webservice.message.Offer offer) {
        return new webservice.message.ConfirmationLetter();
    }
}
