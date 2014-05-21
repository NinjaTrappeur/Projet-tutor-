package shared.behaviour;

import shared.agent.HybridAgent;
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
import remote.IServiceProvider;


/**
 *
 * 
 */
public class HybridAutomatonBehaviour extends jade.core.behaviours.CyclicBehaviour
{
    HybridAgent _myAgent; /*!< The agent to wich this behaviour is added. In contrary of the 'myAgent' property from the Behaviour class, allow to call specific extended methods on the agent. */
    IServiceProvider _serviceProvider; /*!< An objet that actually implements the services offered by the agent to which this behaviour is added. */
    IOfferRequest _offerRequest; /*!< The last offer request the agent received. */
    IReservationRequest _reservationRequest; /*!< The last reservation request the agent received. */
    ACLMessage _receivedMsg; /*!< The last ACL message the agent received. */
    
    /**
     * Constructor.
     * @param myAgent the agent this behaviour is added to.
     * @param remoteAgencyStub the entity that actually implement the agent services (may also be a local object).
     */
    public HybridAutomatonBehaviour(HybridAgent myAgent, IServiceProvider remoteAgencyStub)
    {
        _myAgent = myAgent;
        _serviceProvider = remoteAgencyStub;
        
        _offerRequest = null;
        _reservationRequest = null;
        _receivedMsg = null;
    }
    
    /**
     * API 'action' method.
     * Implements the <i>travel agency</i> automaton.
     * <ul>
     * <li>Concurrently waits a request for proposal and request for reservation from the <i>client</i> agent.</li>
     * <li>When a request for porposal is received: uses the entity <i>_serviceProvider</i> to actually provide an offer.</li>
     * <li>When a reservation request is received: sends a confirmation letter to the client.</li>
     * <ul>
     * See the project reports for more details about the automaton.
     */
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
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Answers an offer request.
     */
    private void _respondOfferRequest()
    {
        if(_offerRequest != null)
        {
            _offerRequest.getAgentID().setName(_myAgent.getAID().getName());
            _offerRequest.getAgentID().setLocalName(_myAgent.getAID().getLocalName());
            String address = (_myAgent.getAID().getAddressesArray().length > 0) ? _myAgent.getAID().getAddressesArray()[0] : "";
            _offerRequest.getAgentID().setAddress(address);
            
            IOfferPack offerPack = _serviceProvider.requestProposal(_offerRequest);
            
            System.out.println("TravelAgencyAutomatonBehaviour::_respondOfferRequest : received offer pack: \n\t"+offerPack.toString());
            System.out.println("TravelAgencyAutomatonBehaviour::_respondOfferRequest : agent id "+offerPack.getBestOffer().getAgencyID().toString());

            try
            {
                ACLMessage msg = _receivedMsg.createReply();
                msg.setContentObject(offerPack);
                myAgent.send(msg);
                System.out.println("TravelAgencyAutomatonBehaviour::_respondOfferRequest : offer sent to "+msg.getAllReceiver().next());
            }
            catch (IOException ex)
            {
                System.err.println("TravelAgencyAutomatonBehaviour::_respondOfferRequest : acl message sending error. "+ex);
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            System.err.println("TravelAgencyAutomatonBehaviour::_respondOfferRequest : offfer request is null");
        }
    }
    
    /**
     * Answers a reservation request.
     */
    private void _respondReservationRequest()
    {
        if(_reservationRequest != null)
        {
            IConfirmationLetter confirmationLetter = _serviceProvider.reserveOffer(_reservationRequest.getOffer());
            
            System.out.println("TravelAgencyAutomatonBehaviour::_respondReservationRequest : received confirmation letter: \n\t"+confirmationLetter);

            try
            {
                ACLMessage msg = _receivedMsg.createReply();
                msg.setContentObject(confirmationLetter);
                myAgent.send(msg);
                System.out.println("TravelAgencyAutomatonBehaviour::_respondReservationRequest : confirm letter sent to "+msg.getAllReceiver().next());
            }
            catch (IOException ex)
            {
                System.err.println("TravelAgencyAutomatonBehaviour::_respondReservationRequest : acl message sending error. "+ex);
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            System.err.println("TravelAgencyAutomatonBehaviour::_respondReservationRequest : reservation request is null");
        }
    }
}
