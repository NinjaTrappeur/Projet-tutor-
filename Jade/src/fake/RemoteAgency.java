/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fake;

import message.ConfirmationLetter;
import message.IConfirmationLetter;
import message.IOffer;
import message.IOfferPack;
import message.IOfferRequest;
import message.Offer;
import message.OfferPack;
import travelagency.ITravelAgency;

/**
 *
 * @author josuah
 */
public class RemoteAgency implements ITravelAgency
{
    @Override
    public IOfferPack requestProposal(IOfferRequest offerRequest)
    {
        return new OfferPack(new Offer(0,"wazaa"));
    }
    
    @Override
    public IConfirmationLetter reserveOffer(IOffer offer)
    {
        return new ConfirmationLetter();
    }
}
