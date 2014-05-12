/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agent;

import behaviour.*;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import message.IMessage;
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
    ArrayList<AID> _agencies;
    private boolean _quit;
    private boolean _searchRunning;
    private IOffer _bestOffer;
    
    public CasomClient()
    {
        _quit = false;
        _searchRunning = false;
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
                if(content instanceof IMessage)
                
                switch(((IMessage)content).getType())
                {
                    case QUIT_REQUEST: // Received quit order from view agent
                        _quit = true;
                        _searchRunning = false;
                        break;
                    case OFFER_REQUEST : // New offer request from view (user demand)
                        this.addBehaviour(new RequestOfferBehaviour(this, (IOfferRequest)content));
                        this.addBehaviour(new WaitOffersBehaviour(this, (IOfferRequest)content));
                        break;
                    case OFFER_PACK :
                        break;
                    case CONFIRM_LETTER : 
                        break;
                }
            }
        }
    }
    
    public void setBestOffer(IOffer offer)
    {
        _bestOffer = offer;
    }
    
    public IOffer getBestOffer()
    {
        return _bestOffer;
    }
    
    public ArrayList<AID> getAgencies()
    {
        return _agencies;
    }
}
