package message;

/**
 *
 * A message used by an agency to send an offer to a client.
 */
public interface IOffer  extends IMessage
{
    /**
     * Gets the price of the offer.
     * @return offer's price.
     */
    public float getPrice();
    
    /**
     * Get the name of the travel agency.
     * @return agency name.
     */
    public String getCompanyName();
    
    /**
     * Gets the AID the travel agency agent.
     * @return travel agency agent AID.
     */
    public AgentID getAgencyID();
    
    /**
     * Defines the price of the offer.
     * @param price offer's price.
     */
    public void setPrice(float price);
    
    /**
     * Defines the name of the travel agency.
     * @param comName agency's name.
     */
    public void setCompanyName(String comName);
    
    /**
     * Defines the AID of the travel agency agent.
     * @param agency agency's agent name.
     */
    public void setAgencyID(AgentID agency);
}
