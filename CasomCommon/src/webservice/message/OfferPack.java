package webservice.message;


public class OfferPack {
    private int bestOffer;
    private Offer [] allOffers;
    private String type;
    
    public OfferPack(Offer [] callOffers, int cbestOffer) {
        bestOffer = cbestOffer;
        allOffers = callOffers;
        type = "OFFER_PACK";
    }
    
    public OfferPack() {
        bestOffer = 0;
        allOffers = null;
        type = "OFFER_PACK";
    }
    
    public int getBestOffer(){
        return bestOffer;
    }
    
    public String getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return "Lowest offer: " + allOffers[bestOffer].toString();
    }

    /**
     * @param bestOffer the bestOffer to set
     */
    public void setBestOffer(int bestOffer) {
        this.bestOffer = bestOffer;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the allOffers
     */
    public Offer[] getAllOffers() {
        return allOffers;
    }

    /**
     * @param allOffers the allOffers to set
     */
    public void setAllOffers(Offer[] allOffers) {
        this.allOffers = allOffers;
    }
}
