/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package behaviour;

import agent.TravelAgency;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.IConfirmationLetter;
import message.IMessage;
import message.IOfferPack;
import message.IOfferRequest;
import message.IReservationRequest;
import travelagency.ITravelAgency;

/**
 *
 * @author josuah
 */
public class TravelAgencyAutomatonBehaviour extends jade.core.behaviours.CyclicBehaviour
{
    agent.TravelAgency _myAgent;
    ITravelAgency _remoteAgencyStub;
    IOfferRequest _offerRequest;
    IReservationRequest _reservationRequest;
    ACLMessage _receivedMsg;
    
    public TravelAgencyAutomatonBehaviour(agent.TravelAgency myAgent)
    {
        _myAgent = myAgent;
        _remoteAgencyStub = new fake.RemoteAgency(_myAgent);
//        _remoteAgencyStub = new service.WsTravelAgency();
        
        _offerRequest = null;
        _reservationRequest = null;
        _receivedMsg = null;
    }
    
    @Override
    public void action()
    {
        this.block(); // wait that myAgent receives message
        _receivedMsg = _myAgent.receive();
        System.out.println("TravelAgencyAutomatonBehaviour::action : message received from "+_receivedMsg);
        if(_receivedMsg != null)
        {
            try
            {
                Object content = _receivedMsg.getContentObject();
                if(content instanceof IMessage)
                {
                    switch(((IMessage)content).getType())
                    {
                        case OFFER_REQUEST : 
                            System.out.println("TravelAgencyAutomatonBehaviour::action : offer request received. "+((message.OfferRequest)content));
                            _offerRequest = (IOfferRequest)content;
                            _respondOfferRequest();
                            break;
                        case RESERVATION_REQUEST :
                            System.out.println("TravelAgencyAutomatonBehaviour::action : reservation request received. "+((message.ReservationRequest)content));
                            _reservationRequest = (IReservationRequest)content;
                            _respondReservationRequest();
                            break;
                    }
                }
                else
                {
                    if(content == null)
                        System.err.println("TravelAgencyAutomatonBehaviour::action : message content is null.");
                    else
                        System.err.println("TravelAgencyAutomatonBehaviour::action : message content is of classe "+content.getClass().getName());
                }
            }
            catch (UnreadableException ex)
            {
                Logger.getLogger(TravelAgency.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void _respondOfferRequest()
    {
        System.out.println("TravelAgencyAutomatonBehaviour::_respondOfferRequest : inside");
        if(_offerRequest != null)
        {
            System.out.println("before");
            
            System.out.println("TravelAgencyAutomatonBehaviour::_respondOfferRequest : MAking AID. Name: "+_myAgent.getAID().getName());
            _offerRequest.getAgentID().setName(_myAgent.getAID().getName());
            _offerRequest.getAgentID().setLocalName(_myAgent.getAID().getLocalName());
            String address = (_myAgent.getAID().getAddressesArray().length > 0) ? _myAgent.getAID().getAddressesArray()[0] : "";
            _offerRequest.getAgentID().setAdresse(address);
            
            IOfferPack offerPack = _remoteAgencyStub.requestProposal(_offerRequest);
            System.out.println("after");
            
            System.out.println("TravelAgencyAutomatonBehaviour::_respondOfferRequest : received offer pack: \n\t"+offerPack.toString());
            System.out.println("TravelAgencyAutomatonBehaviour::_respondOfferRequest : agent id "+offerPack.getBestOffer().getAgencyID().toString());

            try
            {
                ACLMessage msg = _receivedMsg.createReply();
                msg.setContentObject(offerPack);
                myAgent.send(msg);
                System.err.println("TravelAgencyAutomatonBehaviour::_respondOfferRequest : offer sent to "+msg.getAllReceiver().next());
            }
            catch (IOException ex)
            {
                System.err.println("TravelAgencyAutomatonBehaviour::_respondOfferRequest : acl message sending error. "+ex);
                Logger.getLogger(StubOfferResponseBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            System.err.println("TravelAgencyAutomatonBehaviour::_respondOfferRequest : offfer request is null");
        }
    }
    
    private void _respondReservationRequest()
    {
        if(_reservationRequest != null)
        {
            IConfirmationLetter confirmationLetter = _remoteAgencyStub.reserveOffer(_reservationRequest.getOffer());
            
            System.out.println("TravelAgencyAutomatonBehaviour::_respondReservationRequest : received confirmation letter: \n\t"+confirmationLetter);

            try
            {
                ACLMessage msg = _receivedMsg.createReply();
                msg.setContentObject(confirmationLetter);
                myAgent.send(msg);
                System.err.println("TravelAgencyAutomatonBehaviour::_respondReservationRequest : confirm letter sent to "+msg.getAllReceiver().next());
            }
            catch (IOException ex)
            {
                System.err.println("TravelAgencyAutomatonBehaviour::_respondReservationRequest : acl message sending error. "+ex);
                Logger.getLogger(StubOfferResponseBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            System.err.println("TravelAgencyAutomatonBehaviour::_respondReservationRequest : reservation request is null");
        }
    }
}
