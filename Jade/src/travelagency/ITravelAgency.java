/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package travelagency;

import message.IConfirmationLetter;
import message.IOffer;
import message.IOfferRequest;
import message.IOfferPack;

/**
 *
 * @author josuah
 */
public interface ITravelAgency
{
    public IOfferPack requestProposal(IOfferRequest offerRequest);
    public IConfirmationLetter reserveOffer(IOffer offer);
}
