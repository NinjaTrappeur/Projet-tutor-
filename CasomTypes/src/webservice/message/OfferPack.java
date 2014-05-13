package webservice.message;


public class OfferPack {
    private Offer bestOffer;
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
        return "Lowest offer: " + getBestOffer().toString();
    }

    /**
     * @param bestOffer the bestOffer to set
     */
    public void setBestOffer(Offer bestOffer) {
        this.bestOffer = bestOffer;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
