package webservice.message;

public class ReservationRequest {
        private Offer offer;
        private String type;
    
    public ReservationRequest(Offer coffer)
    {
        offer = coffer;
        type = "RESERVATION_REQUEST";
    }
    
    public ReservationRequest() {
        
    }

    public String getType()
    {
        return type;
    }
    
    public Offer getOffer()
    {
        return offer;
    }

    /**
     * @param offer the offer to set
     */
    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
