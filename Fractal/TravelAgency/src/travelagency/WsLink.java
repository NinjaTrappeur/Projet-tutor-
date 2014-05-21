package travelagency;

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
                        offerRequest.getTimeGuard(),
                        offerRequest.getAgentId());
        System.out.println("Reçu requête d'offres: " + offerRequestM.toString());
        System.out.println("Agent id: " + offerRequest.getAgentId().getName() + offerRequest.getAgentId().getAddress());
        OfferPack pack = _client.requestProposal(offerRequestM);
        IOffer offer = pack.getBestOffer();
        
        webservice.message.Offer wsOffer = new webservice.message.Offer(offer.getPrice(), 
                offer.getCompanyName(), 
                offer.getAgencyID());
        
        webservice.message.Offer [] offers = new webservice.message.Offer[1];
        offers[0] = wsOffer;
        System.out.println("Envoi de l'offer pack: " + pack.toString());
        return new webservice.message.OfferPack(offers,0);
    }
    
    public webservice.message.ConfirmationLetter makeReservation(@WebParam(name = "offer")webservice.message.Offer offer) {
        Offer offerM = new Offer(offer.getPrice(), offer.getCompanyName(), offer.getAgency());
        System.out.println("Reçu requête makeReservaion: " + offerM.toString());
        _client.reserveOffer(offerM);
        System.out.println("Reservation effectuée, envoi de la confirmation.");
        return new webservice.message.ConfirmationLetter(offer.getPrice(), offer.getCompanyName(), offer.getAgency());
    }
}
