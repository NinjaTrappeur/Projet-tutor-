/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package behaviour;

import agent.CasomClient;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.IConfirmationLetter;
import agent.ClientView;

/**
 *
 * @author josuah
 */
public class FinalizeBehaviour extends jade.core.behaviours.OneShotBehaviour
{
    CasomClient _myAgent;
    IConfirmationLetter _confirmationLetter;
    
    public FinalizeBehaviour(CasomClient myAgent, IConfirmationLetter confirmationLetter)
    {
        _myAgent = myAgent;
        _confirmationLetter = confirmationLetter;
    }
    
    /**
     * Lookup View agents from the DF and send them the confirmation letter received from the travel agency.
     */
    @Override
    public void action()
    {
        AID view;
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        
        // Template for searching View agents from DF
        sd.setType(ClientView.ServiceDescription);
        template.addServices(sd);
        
        try
        {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setContentObject(_confirmationLetter);
            
            DFAgentDescription[] result = DFService.search(myAgent, template); // !!! this myAgent is different (Jade stuffs)
            
            for(int i = 0; i < result.length; ++i)
            {
                view = result[i].getName();
                msg.addReceiver(view);
            }
            
            _myAgent.send(msg);
        }
        catch (FIPAException fe)
        {
            System.err.println("FinalizeBehaviour::action : DF lookup error. "+fe.getLocalizedMessage());
            Logger.getLogger(FinalizeBehaviour.class.getName()).log(Level.SEVERE, null, fe);
        }
        catch (IOException ex)
        {
            System.err.println("FinalizeBehaviour::action : ACL message content setting error. "+ex.getLocalizedMessage());
            Logger.getLogger(FinalizeBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
