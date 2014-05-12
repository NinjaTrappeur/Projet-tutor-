/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package behaviour;

import agent.CasomClient;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.IOfferRequest;

/**
 *
 * @author josuah
 */
public class RequestOfferBehaviour extends jade.core.behaviours.OneShotBehaviour
{
    private CasomClient _myAgent;
    private IOfferRequest _offerRequest;
    
    public RequestOfferBehaviour(CasomClient myAgent, IOfferRequest offerRequest)
    {
        _myAgent = myAgent;
    }
    
    @Override
    public void action()
    {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        
        for(AID agent : _myAgent.getAgencies())
            msg.addReceiver(agent);
        try
        {
            msg.setContentObject(_offerRequest);
        }
        catch (IOException ex) {
            System.err.println("RequestOfferBehaviour::action : message sending error. "+ex.getLocalizedMessage());
            Logger.getLogger(RequestOfferBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        _myAgent.send(msg);
    }
}
