/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import travelagency.ITravelAgency;

/**
 *
 * @author josuah
 */
public class RemoteAgency implements ITravelAgency
{
    TravelAgency _agency;
    
    public RemoteAgency(TravelAgency agency)
    {
        _agency = agency;
    }
    
    @Override
    public IOfferPack requestProposal(IOfferRequest offerRequest)
    {
        AgentID aid = new AgentID(_agency.getAID().getName(),
                _agency.getAID().getLocalName(), _agency.getAID().getAddressesArray()[0]);
        IOffer offer = new Offer(1,"wazaa", aid);
        
        return new OfferPack(offer);
    }
    
    @Override
    public IConfirmationLetter reserveOffer(IOffer offer)
    {
        AgentID aid = new AgentID(_agency.getAID().getName(),
                _agency.getAID().getLocalName(), _agency.getAID().getAddressesArray()[0]);
        return new ConfirmationLetter(1, "wazaa", aid);
    }
}
