package fake;

import agent.TravelAgency;
import message.AgentID;
import message.ConfirmationLetter;
import message.IConfirmationLetter;
import message.IOffer;
import message.IOfferPack;
import message.IOfferRequest;
import message.Offer;
import message.OfferPack;
import remote.IServiceProvider;

/**
 *
 * <h1>Fake service provider.</h1>
 * A fake travel agency services provider.
 * Intended for use in the agent.TravelAgency agent, as an actual service prodiver.
 * Simulates the fonctionnalities by giving a fixed value. Useful for tests and quick tour.
 */
public class ServiceProvider implements IServiceProvider
{
    TravelAgency _agency; /*!< The travel agency agent that uses this service provider*/
    
    /**
     * Constructor
     * @param agency the agency agent that uses this service provider.
     */
    public ServiceProvider(TravelAgency agency)
    {
        _agency = agency;
    }
    
    /**
     * Responds to a request for proposal.
     * @param offerRequest the request for proposal message.
     * @return IOfferPack a set of vacation offers.
     */
    @Override
    public IOfferPack requestProposal(IOfferRequest offerRequest)
    {
        String address = (_agency.getAID().getAddressesArray().length > 0) ? _agency.getAID().getAddressesArray()[0] : "";
        AgentID aid = new AgentID(_agency.getAID().getName(),
                _agency.getAID().getLocalName(), address);
        IOffer offer = new Offer(1,"wazaa", aid);
        
        return new OfferPack(offer);
    }
    
    /**
     * Responds to a reservation request.
     * @param offer the offer to be booked.
     * @return IConfirmationLetter a confirmation letter for the reservation.
     */
    @Override
    public IConfirmationLetter reserveOffer(IOffer offer)
    {
        String address = (_agency.getAID().getAddressesArray().length > 0) ? _agency.getAID().getAddressesArray()[0] : "";
        AgentID aid = new AgentID(_agency.getAID().getName(),
                _agency.getAID().getLocalName(), address);
        return new ConfirmationLetter(1, "wazaa", aid);
    }
}
