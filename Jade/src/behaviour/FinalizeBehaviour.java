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
import message.IConfirmationLetter;

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
        try
        {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setContentObject(_confirmationLetter);
            
            for(AID view : _myAgent.getViews())
                msg.addReceiver(view);
            
            _myAgent.send(msg);
        }
        catch (IOException ex)
        {
            System.err.println("FinalizeBehaviour::action : ACL message content setting error. "+ex.getLocalizedMessage());
            Logger.getLogger(FinalizeBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
