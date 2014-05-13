package travelagency;

import javax.jws.WebParam;
import javax.jws.WebService;
import message.ConfirmationLetter;
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
    
    public OfferPack getOffers(@WebParam(name = "offerRequest")OfferRequest offerRequest){
        return getClient().getOffers(offerRequest);
    }
    
    public ConfirmationLetter makeReservation(@WebParam(name = "offer")Offer offer) {
        return getClient().makeReservation(offer);
    }

    /**
     * @return the _client
     */
    public Client getClient() {
        return _client;
    }

    /**
     * @param _client the _client to set
     */
    public void setClient(Client _client) {
        this._client = _client;
    }
}
