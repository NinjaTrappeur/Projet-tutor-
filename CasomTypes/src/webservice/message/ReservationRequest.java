package webservice.message;

public class ReservationRequest {
        Offer offer;
        String type;
    
    public ReservationRequest(Offer coffer)
    {
        offer = coffer;
        type = "RESERVATION_REQUEST";
    }

    public String getType()
    {
        return type;
    }
    
    public Offer getOffer()
    {
        return offer;
    }
}
