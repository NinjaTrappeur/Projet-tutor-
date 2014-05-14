/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package behaviour;

import agent.CasomClient;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.IMessage;
import message.IOffer;
import message.IOfferPack;
import message.IOfferRequest;
import message.ReservationRequest;

/**
 *
 * @author josuah
 */
public class WaitOffersBehaviour extends SimpleBehaviour
{
    private CasomClient _myAgent;
    private IOfferRequest _offerRequest;
    private final float _startTime;
    
    public WaitOffersBehaviour(CasomClient myAgent, IOfferRequest offerRequest)
    {
        _myAgent = myAgent;
        _startTime = 0.001F * System.currentTimeMillis();
        _offerRequest = offerRequest;
    }
    
    @Override
    public boolean done()
    {
        if(_myAgent.isSearchRunning())
        {
            boolean done = 0.001F * System.currentTimeMillis() >= _startTime + _offerRequest.getTimeGuard();

            if(done && _myAgent.getBestOffer() != null)
            {
                try
                {
                    ACLMessage msg = new ACLMessage(ACLMessage.AGREE);
                    msg.addReceiver(new AID(_myAgent.getBestOffer().getAgencyID(), AID.ISGUID));
                    msg.setContentObject(new ReservationRequest(_myAgent.getBestOffer()));
                    
                    _myAgent.send(msg);
                    
                    System.out.println("WaitOffersBehaviour::done : sending reservation request.");
                }
                catch (IOException ex)
                {
                    System.err.println("WaitOffersBehaviour::done : acl content setting error. "+ex.getLocalizedMessage());
                    Logger.getLogger(WaitOffersBehaviour.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            return done;
        }
        
        else
            return true;
    }
    
    @Override
    public void action()
    {
        this.block(); // wait that myAgent receives message
        if(_myAgent.isSearchRunning())
        {
            ACLMessage msg = _myAgent.receive();
            System.out.println("WaitOffersBehaviour::action : message received.");
            if(msg != null)
            {
                try
                {
                    Object content = msg.getContentObject();
                    if(content instanceof IMessage)
                    {
                        if(((IMessage)content).getType() == IMessage.Type.OFFER_PACK)
                        {
                            System.out.println("WaitOffersBehaviour::action : offer pack received.");
                            IOffer offer = ((IOfferPack)content).getBestOffer();
                            if(offer != null)
                            {
                                if(offer.getPrice() < _myAgent.getBestOffer().getPrice())
                                    _myAgent.setBestOffer(offer);
                            }
                        }
                        else
                        {
                            _myAgent.putBack(msg); // the message is for another behaviour
                            System.out.println("WaitOffersBehaviour::action : not for me, message put back.");
                        }
                    }
                }
                catch (UnreadableException ex)
                {
                    Logger.getLogger(WaitOffersBehaviour.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
