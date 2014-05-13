package travelagency;

import javax.jws.WebService;
import message.IConfirmationLetter;
import message.IOffer;
import message.IOfferPack;
import message.IOfferRequest;

@WebService
public class WsLink {
    private final Client _client;
    
    public WsLink(Client client) {
        _client = client;
    }
    
    public IOfferPack getOffers(IOfferRequest offerRequest){
        return _client.getOffers(offerRequest);
    }
    
    public IConfirmationLetter makeReservation(IOffer offer) {
        return _client.makeReservation(offer);
    }
}
