package message;

import java.util.ArrayList;

/**
 * A message to send a pack of offers to a client.
 */
public interface IOfferPack extends IMessage
{
    /**
     * Adds an offer to the pack.
     * @param offer the offer to be added.
     */
    public void addOffer(IOffer offer);
    
    /**
     * Gets the offer the best matches the search criteria.
     * @return an offer.
     */
    public IOffer getBestOffer();
    
    /**
     * Gets all the offers of the pack.
     * @return arraylist of offers.
     */
    public ArrayList<IOffer> getAllOffers();

    /**
     * Defines the offer that best matches the search criteria.
     * @param offer offer that will be considered best match.
     */
    public void setBestOffer(IOffer offer);
    
    /**
     * Defines the offers of the pack.
     * @param offers all offers.
     */
    public void setAllOffers(ArrayList<IOffer> offers);
}
