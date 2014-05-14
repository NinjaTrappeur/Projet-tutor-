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
import message.IOffer;
import message.IOfferPack;
import message.IOfferRequest;
import message.ReservationRequest;

/**
 *
 * @author josuah
 */
public class CasomClientAutomatonBehaviour extends jade.core.behaviours.CyclicBehaviour
{
    private final CasomClient _myAgent;
    private IOfferRequest _offerRequest;
    private IConfirmationLetter _confirmationLetter;
    
    private TimeGuardManager _timeGuard;
    
    private boolean _searchRunning;
    private boolean _offersRequested;
    private boolean _waitingOffers;
    private boolean _firstEvaluation;
    private boolean _reservationRequested;
    private boolean _booked;
    
    public CasomClientAutomatonBehaviour(CasomClient myAgent)
    {
        _offerRequest = null;
        
        _searchRunning = false;
        _offersRequested = false;
        _waitingOffers = false;
        _firstEvaluation = true;
        _reservationRequested = false;
        _booked = false;
        
        _myAgent = myAgent;
        _timeGuard = new TimeGuardManager();
    }
    
    private void _resetStatus()
    {
        _searchRunning = false;
        _offersRequested = false;
        _waitingOffers = false;
        _firstEvaluation = true;
        _reservationRequested = false;
        _booked = false;
    }
    
    @Override
    public void action()
    {
        if(_waitingOffers)
        {
            long remainingTimeGuard = _timeGuard.remainingTime();
            if(remainingTimeGuard <= 0)
            {
                _waitingOffers = false;
                _requestReservation();
            }
            else
                this.block(remainingTimeGuard);
        }
        else
            this.block();
        
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
                            _resetStatus();
                            _requestAgenciesOffers();
                            _timeGuard.initialize((long)_offerRequest.getTimeGuard());
                            break;
                        case OFFER_PACK: 
                            System.out.println("CasomClientAutomatonBehaviour::action : offer pack received.");
                            long remainingTimeGuard = _timeGuard.remainingTime();
                            if(remainingTimeGuard > 0)
                            {
                                System.out.println("CasomClientAutomatonBehaviour::action : evaluating offer pack");
                                _evaluateOffer((IOfferPack)content);
                                
                                System.out.println("CasomClientAutomatonBehaviour::action : remaining time guard "+remainingTimeGuard);
                            }
                            else
                                _waitingOffers = false;
                            break;
                        case CONFIRM_LETTER : 
                            System.out.println("CasomClientAutomatonBehaviour::action : confirm letter received.");
                            if(_reservationRequested)
                            {
                                _confirmationLetter = (IConfirmationLetter)content;
                                _myAgent.setBooked(true);
                                _informUser();
                                System.out.println("CasomClientAutomatonBehaviour::action : confirm letter forwarded.");
                            }
                            break;
                        default:
                            System.err.println("CasomClientAutomatonBehaviour::action : unexpected message content, of class "+content.getClass().getName());
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
                
                _offersRequested = true;
                _waitingOffers = true;
                
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
    
    private void _evaluateOffer(IOfferPack offerPack)
    {
        IOffer offer = offerPack.getBestOffer();
        
        if(_firstEvaluation)
        {
            _firstEvaluation = false;
            _myAgent.setBestOffer(offer);
        }
        
        if(offer != null && _myAgent.getBestOffer() != null)
        {
            if(offer.getPrice() < _myAgent.getBestOffer().getPrice())
            {
                _myAgent.setBestOffer(offer);
                System.out.println("CasomClientAutomatonBehaviour::_evaluateOffer : offer is the better.");
            }
            else
                System.out.println("CasomClientAutomatonBehaviour::_evaluateOffer : offer is not the better.");
        }
        else
            System.out.println("CasomClientAutomatonBehaviour::_evaluateOffer : myAgent.getBestOffer() is null.");
    }
    
    private void _requestReservation()
    {
        if(_myAgent.getBestOffer() != null)
        {
            try
            {
                ACLMessage msg = new ACLMessage(ACLMessage.AGREE);
                
                AID receiver = new AID();
                receiver.setName(_myAgent.getBestOffer().getAgencyID().getName());
                receiver.setName(_myAgent.getBestOffer().getAgencyID().getLocalName());
                receiver.addAddresses(_myAgent.getBestOffer().getAgencyID().getAdresse());
                
                msg.addReceiver(receiver);
                msg.setContentObject(new ReservationRequest(_myAgent.getBestOffer()));

                _myAgent.send(msg);
                _reservationRequested = true;
                
                System.out.println("CasomClientAutomatonBehaviour::_requestReservation : reservation request sent to "+receiver);
            }
            catch (IOException ex)
            {
                System.err.println("CasomClientAutomatonBehaviour::_requestReservation : acl content setting error. "+ex.getLocalizedMessage());
                Logger.getLogger(WaitOffersBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
            System.err.println("CasomClientAutomatonBehaviour::_requestReservation, Warning : myAgent.getBestOffer() is null");
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
            
            _booked = true;
            
            System.out.println("CasomClientAutomatonBehaviour::_informUser : confirm letter sent to "+_myAgent.getViews());
        }
        catch (IOException ex)
        {
            System.err.println("CasomClientAutomatonBehaviour::_informUser : ACL message content setting error. "+ex.getLocalizedMessage());
            Logger.getLogger(FinalizeBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
