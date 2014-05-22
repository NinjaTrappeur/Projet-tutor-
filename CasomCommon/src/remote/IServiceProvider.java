package remote;

import message.IConfirmationLetter;
import message.IOffer;
import message.IOfferRequest;
import message.IOfferPack;

/**
 *
 * Interface of service provider entities.
 * Service providers are entities that are typically to be used by the travel agency agent, to actually provide it's services.
 * This can be anything, a web service, a simple object, etc.
 */
public interface IServiceProvider
{
    /**
     * Responds to a request for proposal.
     * @param offerRequest the request for proposal message.
     * @return IOfferPack a set of vacation offers.
     */
    public IOfferPack requestProposal(IOfferRequest offerRequest);
    
    /**
     * Responds to a reservation request.
     * @param offer the offer to be booked.
     * @return IConfirmationLetter a confirmation letter for the reservation.
     */
    public IConfirmationLetter reserveOffer(IOffer offer);
}
