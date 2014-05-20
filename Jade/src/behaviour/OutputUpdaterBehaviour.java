
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
 * <h1>View agent automaton.</h1>
 * A behaviour that represent the automaton for the 'view' agent.
 */
public class OutputUpdaterBehaviour extends jade.core.behaviours.CyclicBehaviour
{
    private final ClientView _myAgent; /*!< The agent to wich this behaviour is added. In contrary of the 'myAgent' property from the Behaviour class, allow to call specific extended methods on the agent. */
    
    /**
     * Constructor.
     * @param myAgent  the agent this behaviour is added to.
     */
    public OutputUpdaterBehaviour(ClientView myAgent)
    {
        _myAgent = myAgent;
    }
    
    /**
     * API 'action' method.
     * Implements the client automaton.
     * <ul>
     * <li>Waits for a message.</li>
     * <li>When a message i received: if it's a confirmation letter, then updates the view.</li>
     * <ul>
     * See the project reports for more details about the automaton.
     */
    @Override
    public void action()
    {
        this.block(); // wait that myAgent receives message
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
