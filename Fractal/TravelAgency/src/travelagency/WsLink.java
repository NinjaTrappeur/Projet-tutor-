package travelagency;

import jade.core.AID;
import javax.jws.WebParam;
import javax.jws.WebService;
import message.IOffer;
import message.Offer;
import message.OfferPack;
import message.OfferRequest;


@WebService
public class WsLink {
    private Client _client;
    
    public WsLink(Client client) {
        _client = client;
    }
    
    public WsLink(){
        
    }
    
    public webservice.message.OfferPack getOffers(@WebParam(name = "offerRequest")webservice.message.OfferRequest offerRequest){
        OfferRequest offerRequestM = 
                new OfferRequest(offerRequest.getHighestPrice(),
                        offerRequest.getLowestPrice(), 
                        offerRequest.getClientName(),
                        offerRequest.getDepartureDate(),
                        offerRequest.getReturnDate(),
                        offerRequest.getPlaceName(), 
                        offerRequest.getTimeGuard());
        OfferPack pack = _client.getOffers(offerRequestM);
        IOffer offer = pack.lowestPrice();
        return new webservice.message.OfferPack(new webservice.message.Offer(offer.price(),
                offer.companyName(),"Composant"));
    }
    
    public webservice.message.ConfirmationLetter makeReservation(@WebParam(name = "offer")webservice.message.Offer offer) {
        //TODO, traduire AID.
        Offer offerM = new Offer(offer.getPrice(), offer.getName(), new AID());
        _client.makeReservation(offerM);
        return new webservice.message.ConfirmationLetter();
    }
}
