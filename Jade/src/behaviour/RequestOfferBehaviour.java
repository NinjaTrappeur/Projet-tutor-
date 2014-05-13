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
    private final CasomClient _myAgent;
    private final IOfferRequest _offerRequest;
    
    public RequestOfferBehaviour(CasomClient myAgent, IOfferRequest offerRequest)
    {
        _offerRequest = offerRequest;
        
        _myAgent = myAgent;
        _myAgent.setBooked(false);
        _myAgent.setSearchRuinning(true);
    }
    
    /**
     * Lookup travel agencies agents from DF and send them the OfferRequest message received from the View Agent.
     */
    @Override
    public void action()
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
            }
            catch (IOException ex)
            {
                System.err.println("RequestOfferBehaviour::action : acl message sending error. "+ex.getLocalizedMessage());
                Logger.getLogger(RequestOfferBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
