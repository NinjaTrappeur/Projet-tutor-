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
        System.out.println("Reçu requête getOffers.");
        OfferRequest offerRequestM = 
                new OfferRequest(offerRequest.getHighestPrice(),
                        offerRequest.getLowestPrice(), 
                        offerRequest.getClientName(),
                        offerRequest.getDepartureDate(),
                        offerRequest.getReturnDate(),
                        offerRequest.getPlaceName(), 
                        offerRequest.getTimeGuard(),
                        new AID(offerRequest.getAgentId(), AID.ISLOCALNAME));
        OfferPack pack = _client.getOffers(offerRequestM);
        IOffer offer = pack.getBestOffer();
        
        webservice.message.Offer wsOffer = new webservice.message.Offer(offer.getPrice(), 
                offer.getCompanyName(), 
                offer.getAgencyID().getLocalName());
        
        webservice.message.Offer [] offers = new webservice.message.Offer[1];
        offers[0] = wsOffer;
        
        return new webservice.message.OfferPack(offers,0);
    }
    
    public webservice.message.ConfirmationLetter makeReservation(@WebParam(name = "offer")webservice.message.Offer offer) {
        System.out.println("Reçu requête makeReservaion.");
        Offer offerM = new Offer(offer.getPrice(), offer.getCompanyName(), new AID());
        _client.makeReservation(offerM);
        return new webservice.message.ConfirmationLetter(offer.getPrice(), offer.getCompanyName(), offer.getAgency());
    }
}
