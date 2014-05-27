package travelagency.interfaces;

import message.IOfferPack;
import message.IOfferRequest;

/**
 * Interface implemented by the VacationOfferBrowser component.
 */
public interface IVacationOfferBrowser {
    public IOfferPack getProposals(IOfferRequest request);
}
