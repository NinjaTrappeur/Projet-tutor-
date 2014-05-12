/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package behaviour;

import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.IOfferPack;
import message.IOfferRequest;
import travelagency.ITravelAgency;

/**
 *
 * @author josuah
 */
public class StubOfferResponseBehaviour extends jade.core.behaviours.OneShotBehaviour
{
    ITravelAgency _remoteAgency;
    IOfferRequest _offerRequest;
    ACLMessage _receivedMsg;
    
    public StubOfferResponseBehaviour(ACLMessage msg, IOfferRequest offerRequest)
    {
        _offerRequest = offerRequest;
        _receivedMsg = msg;
        
        _remoteAgency = null; // Create web service client
    }

    @Override
    public void action()
    {
        IOfferPack offerPack = _remoteAgency.requestProposal(_offerRequest);
        
        try
        {
            ACLMessage msg = _receivedMsg.createReply();
            msg.setContentObject(offerPack);
            myAgent.send(msg);
        }
        catch (IOException ex)
        {
            Logger.getLogger(StubOfferResponseBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
