/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package behaviour;

import agent.CasomClient;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.IMessage;
import message.IOffer;
import message.IOfferPack;
import message.IOfferRequest;

/**
 *
 * @author josuah
 */
public class WaitOffersBehaviour extends Behaviour
{
    CasomClient _myAgent;
    IOfferRequest _offerRequest;
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
        return 0.001F * System.currentTimeMillis() >= _startTime + _offerRequest.timeGuard();
    }
    
    @Override
    public void action()
    {
        ACLMessage msg = _myAgent.receive();
        try {
            Object content = msg.getContentObject();
            if(content instanceof IMessage)
            {
                if(((IMessage)content).getType() == IMessage.Type.OFFER_PACK)
                {
                    IOffer offer = ((IOfferPack)content).lowestPrice();
                    if(offer.price() < _myAgent.getBestOffer().price())
                        _myAgent.setBestOffer(offer);
                }
            }
        } catch (UnreadableException ex) {
            Logger.getLogger(WaitOffersBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
