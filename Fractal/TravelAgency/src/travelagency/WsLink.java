package travelagency;

import javax.jws.WebParam;
import javax.jws.WebService;
import message.IOffer;
import message.IOfferPack;
import message.Offer;
import message.OfferRequest;
import remote.IServiceProvider;

/**
 * This class is the webservice attached to the WsClient component.
 * 
 * Exports the services of the travel agency to the webservice format.
 */
@WebService
public class WsLink {
    private IServiceProvider _client; /*!< Reference to the client component.*/
    
    /**
    * Constructor.
    * @param client Client component used by this webservice.
    */
    public WsLink(IServiceProvider client) {
        _client = client;
    }
    
    /**
     * Default constructor. 
     * 
     * Not used by this application (will raise a null pointer exception) but necessary
     * to transform this class into a javabean.
     */
    public WsLink(){
        
    }
    
    /**
     * Web service export of the request proposal fractal service.
     * @param offerRequest Contains the parameters of the request.
     * @return List of Offers that matches with the request.
     */
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
        IOfferPack pack = _client.requestProposal(offerRequestM);
        IOffer offer = pack.getBestOffer();
        
        webservice.message.Offer wsOffer = new webservice.message.Offer(offer.getPrice(), 
                offer.getCompanyName(), 
                offer.getAgencyID());
        
        webservice.message.Offer [] offers = new webservice.message.Offer[1];
        offers[0] = wsOffer;
        System.out.println("Envoi de l'offer pack: " + pack.toString());
        return new webservice.message.OfferPack(offers,0);
    }
    
    
    /**
     * Web service export of the reserve offer fractal service.
     * @param offer Offer that needs to be reserved.
     * @return Confirmation of the reservation.
     */
    public webservice.message.ConfirmationLetter makeReservation(@WebParam(name = "offer")webservice.message.Offer offer) {
        Offer offerM = new Offer(offer.getPrice(), offer.getCompanyName(), offer.getAgency());
        System.out.println("Reçu requête makeReservaion: " + offerM.toString());
        _client.reserveOffer(offerM);
        System.out.println("Reservation effectuée, envoi de la confirmation.");
        return new webservice.message.ConfirmationLetter(offer.getPrice(), offer.getCompanyName(), offer.getAgency());
    }
}
