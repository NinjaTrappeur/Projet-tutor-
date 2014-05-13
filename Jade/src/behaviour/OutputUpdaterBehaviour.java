/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package behaviour;

import agent.ClientView;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.ConfirmationLetter;
import message.IMessage;

/**
 *
 * @author josuah
 */
public class OutputUpdaterBehaviour extends jade.core.behaviours.CyclicBehaviour
{
    private final ClientView _myAgent;
    
    public OutputUpdaterBehaviour(ClientView myAgent)
    {
        _myAgent = myAgent;
    }
    
    @Override
    public void action()
    {
        ACLMessage msg = myAgent.receive();
        if(msg != null)
        {
            try
            {
                Object content = msg.getContentObject();
                
                if(content instanceof IMessage)
                {
                    if(((IMessage)content).getType() == IMessage.Type.CONFIRM_LETTER)
                    {
                        _myAgent.getForm().output().setText("  -- "+((ConfirmationLetter)content));
                    }
                }
            }
            catch (UnreadableException ex)
            {
                Logger.getLogger(OutputUpdaterBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
