/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agent;

import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import message.IOffer;
import message.IOfferPack;
import message.IOfferRequest;
import travelagency.ITravelAgency;

/**
 *
 * @author josuah
 */
public class CasomClient extends jade.core.Agent
{
    private ArrayList<ITravelAgency> _agencies;
    private boolean _quit;
    
    public CasomClient()
    {
        _quit = false;
    }
    
    @Override
    public void setup()
    {
        while(!_quit)
        {
            ACLMessage msg = receive();
            if(msg != null)
            {
                Object content = msg.getContent();
            }
        }
    }
    
    private void resquestOffer(IOfferRequest offerRequest)
    {
        IOffer currentOffer;
        IOffer bestOffer = null;
        
        for(ITravelAgency agency : _agencies)
        {
            IOfferPack offers = agency.requestProposal(offerRequest);
            
            currentOffer = offers.lowestPrice();
            
            if(bestOffer == null)
                bestOffer = currentOffer;
            else if(currentOffer.price() < bestOffer.price())
            {
                bestOffer = currentOffer;
            }
        }
    }
}
