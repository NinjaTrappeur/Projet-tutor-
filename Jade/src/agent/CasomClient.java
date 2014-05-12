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
import message.IConfirmationLetter;
import message.IMessage;
import message.IOffer;
import message.IOfferRequest;

/**
 *
 * @author josuah
 */
public class CasomClient extends jade.core.Agent
{
    private ArrayList<AID> _agencies;
    private boolean _quit;
    private boolean _searchRunning;
    private boolean _booked;
    private IOffer _bestOffer;
    
    public static final String ServiceDescription;
    
    static
    {
        ServiceDescription = "Casom-Client-Agent";
    }
    
    public CasomClient()
    {
        super();
        
        _quit = false;
        _searchRunning = false;
        _booked = false;
    }
    
    @Override
    public void setup()
    {
        IOfferRequest offerRequest;
        
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
                        offerRequest = (IOfferRequest)content;
                        this.addBehaviour(new RequestOfferBehaviour(this, offerRequest));
                        this.addBehaviour(new WaitOffersBehaviour(this, offerRequest));
                        break;
                    case CONFIRM_LETTER : 
                        this.addBehaviour(new FinalizeBehaviour(this, (IConfirmationLetter)content));
                        break;
                }
            }
        }
    }
    
    
    
    public void setBestOffer(IOffer offer)
    {
        _bestOffer = offer;
    }
    
    public void setBooked(boolean yesNo)
    {
        _booked = yesNo;
    }
    
    public void setSearchRuinning(boolean yesNo)
    {
        _searchRunning = yesNo;
    }
    
    public IOffer getBestOffer()
    {
        return _bestOffer;
    }
    
    public IOffer getBookedOffer()
    {
        if(_booked)
            return _bestOffer;
        else
            return null;
    }
    
    public ArrayList<AID> getAgencies()
    {
        return _agencies;
    }
    
    public boolean isBooked()
    {
        return _booked;
    }
    
    public boolean isSearchRunning()
    {
        return _searchRunning;
    }
}
