package message;

import java.util.Date;

/**
 *
 * A message to request a proposal from a travel agency.
 */
public interface IOfferRequest extends IMessage
{
    /**
     * Get the name of the user.
     * @return user name.
     */
    public String getClientName();
    
    /**
     * Gets the departure wished date.
     * @return departure date.
     */
    public Date getDepartureDate();
    
    /**
     * Get the return wished date.
     * @return return date.
     */
    public Date getReturnDate();
    
    /**
     * Get the destination name.
     * @return destination name.
     */
    public String getPlaceName();
    
    /**
     * Get the minimum wished price.
     * @return minimum price for an offer.
     */
    public float getLowestPrice();
    
    /**
     * Gets the maximum wished price.
     * @return maximum price for an offer.
     */
    public float getHighestPrice();
    
    /**
     * Gets the time to wait for offers.
     * @return duration to wait for offers.
     */
    public long getTimeGuard();
    
    /**
     * Gets the AID of the client that request offers.
     * @return AID of demander.
     */
    public AgentID getAgentID();

    /**
     * Defines the name of the user.
     * @param name cleint name.
     */
    public void setClientName(String name);
    
    /**
     * Defines the departure date
     * @param dd departure date.
     */
    public void setDepartureDate(Date dd);
    
    /**
     * Defnies the date of the return.
     * @param rd return date.
     */
    public void setReturnDate(Date rd);
    
    /***
     * Defines the destination name.
     * @param place destination name.
     */
    public void setPlaceName(String place);
    
    /**
     * Defines the lowest allowed price for an offer.
     * @param lp lowest price.
     */
    public void setLowestPrice(float lp);
    
    /**
     * Defines the highest allowed price for an offer.
     * @param hp highest price.
     */
    public void setHighestPrice(float hp);
    
    /**
     * Defines the time to wait for offers.
     * @param timeguard time to wait for offers.
     */
    public void setTimeGuard(long timeguard);
    
    /**
     * Defines the AID of the agent that represents the user (client).
     * @param agentId client's AID.
     */
    public void setAgentID(AgentID agentId);
}
