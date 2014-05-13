package webservice.message;


public class OfferPack {
    private final Offer bestOffer;
    private String type;
    
    public OfferPack(Offer cbestOffer) {
        bestOffer = cbestOffer;
        type = "OFFER_PACK";
    }
    
    public Offer getBestOffer(){
        return bestOffer;
    }
    
    public String getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return "Lowest offer: " + bestOffer.toString();
    }
}
