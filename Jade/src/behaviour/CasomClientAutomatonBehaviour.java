/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package behaviour;

import agent.CasomClient;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.IConfirmationLetter;
import message.IMessage;
import message.IOfferRequest;

/**
 *
 * @author josuah
 */
public class CasomClientAutomatonBehaviour extends jade.core.behaviours.CyclicBehaviour
{
    private final CasomClient _myAgent;
    private IOfferRequest _offerRequest;
    private IConfirmationLetter _confirmationLetter;
    
    private boolean _searchRunning;
    private boolean _offersRequested;
    private boolean _offersReceived;
    private boolean _reservationRequested;
    private boolean _booked;
    
    public CasomClientAutomatonBehaviour(CasomClient myAgent)
    {
        _offerRequest = null;
        
        _searchRunning = false;
        _offersRequested = false;
        _offersReceived = false;
        _reservationRequested = false;
        _booked = false;
        
        _myAgent = myAgent;
    }
    
    @Override
    public void action()
    {
        this.block(); // wait that myAgent receives message
        ACLMessage msg = _myAgent.receive();
        if(msg != null)
        {
            System.out.println("CasomClientAutomatonBehaviour::action : message received.");
            Object content;

            try
            {
                content = msg.getContentObject();
                if(content instanceof IMessage)
                {
                    switch(((IMessage)content).getType())
                    {
                        case OFFER_REQUEST : // New offer request from view (user demand)
                            System.out.println("CasomClientAutomatonBehaviour::action : offer request received.");
                            _offerRequest = (IOfferRequest)content;
                            
                            _myAgent.addBehaviour(new WaitOffersBehaviour(_myAgent, _offerRequest));
                            _requestAgenciesOffers();
                            break;
                        case CONFIRM_LETTER : 
                            System.out.println("CasomClientAutomatonBehaviour::action : confirm letter received.");
                            _confirmationLetter = (IConfirmationLetter)content;
                            _myAgent.setBooked(true);
                            _informUser();
                            break;
                        default:
                            System.out.println("CasomClientAutomatonBehaviour::action : default -- putting back message.");
                            _myAgent.putBack(msg); //It's not for this behaviour
                    }
                }
                else
                {
                    if(content == null)
                        System.err.println("CasomClientAutomatonBehaviour::action : offer request content is null.");
                    else
                        System.err.println("CasomClientAutomatonBehaviour::action : offer request content is of classe "+content.getClass().getName());
                }
            }
            catch (UnreadableException ex)
            {
                System.err.println("CasomClientAutomatonBehaviour::action : acl content object fetching error. "+ex);
                Logger.getLogger(CasomClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void _requestAgenciesOffers()
    {
        if(!_myAgent.getAgencies().isEmpty())
        {
            try
            {
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.setContentObject(_offerRequest);

                for(AID agency : _myAgent.getAgencies())
                    msg.addReceiver(agency);

                _myAgent.send(msg);
                
                System.out.println("CasomClientAutomatonBehaviour::_requestAgenciesOffers : offer request sent to "+_myAgent.getAgencies());
            }
            catch (IOException ex)
            {
                System.err.println("CasomClientAutomatonBehaviour::_requestAgenciesOffers : acl message sending error. "+ex.getLocalizedMessage());
                Logger.getLogger(RequestOfferBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            System.err.println("CasomClientAutomatonBehaviour::_requestAgenciesOffers : no registered agency.");
        }
    }
    
    private void _informUser()
    {
        try
        {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setContentObject(_confirmationLetter);
            
            for(AID view : _myAgent.getViews())
                msg.addReceiver(view);
            
            _myAgent.send(msg);
            
            System.out.println("CasomClientAutomatonBehaviour::_informUser : confirm letter sent to "+_myAgent.getViews());
        }
        catch (IOException ex)
        {
            System.err.println("CasomClientAutomatonBehaviour::_informUser : ACL message content setting error. "+ex.getLocalizedMessage());
            Logger.getLogger(FinalizeBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
