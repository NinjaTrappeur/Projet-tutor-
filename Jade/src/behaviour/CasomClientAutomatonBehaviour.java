package behaviour;

import agent.CasomClient;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.IConfirmationLetter;
import message.IMessage;
import message.IOffer;
import message.IOfferPack;
import message.IOfferRequest;
import message.ReservationRequest;

/**
 *
 * <h1>Client agent automaton.</h1>
 * A behaviour that represent the automaton for the 'client' agent.
 */
public class CasomClientAutomatonBehaviour extends jade.core.behaviours.CyclicBehaviour
{
    private final CasomClient _myAgent; /*!< The agent to wich this behaviour is added. In contrary of the 'myAgent' property from the Behaviour class, allow to call specific extended methods on the agent. */
    private IOfferRequest _offerRequest; /*!< The last received offer request.*/
    private IConfirmationLetter _confirmationLetter; /*!< The last received confirmation letter.*/
    
    private TimeGuardManager _timeGuard; /*!< For time guards management.*/
    
    private boolean _searchRunning; /*!< Tells whether a request for proposal has been sent and not yet satisfied.*/
    private boolean _offersRequested; /*!< Tells whether any request for proposal has been sent.*/
    private boolean _waitingOffers; /*!< Tell if the agent is waiting for proposals.*/
    private boolean _firstEvaluation; /*!< Tell is any offer evaluation has already been done since the last request for proposal has been sent.*/
    private boolean _reservationRequested; /*!< Tell if the agent asked an agency to reserve a vacation.*/
    private boolean _booked; /*!< Tells if a vacation has been booked.*/
    
    /**
     * Constructor.
     * @param myAgent the agent this behaviour is added to.
     */
    public CasomClientAutomatonBehaviour(CasomClient myAgent)
    {
        _offerRequest = null;
        
        _searchRunning = false;
        _offersRequested = false;
        _waitingOffers = false;
        _firstEvaluation = true;
        _reservationRequested = false;
        _booked = false;
        
        _myAgent = myAgent;
        _timeGuard = new TimeGuardManager();
    }
    
    /**
     * Reset the status of the behaviour to default.
     */
    private void _resetStatus()
    {
        _searchRunning = false;
        _offersRequested = false;
        _waitingOffers = false;
        _firstEvaluation = true;
        _reservationRequested = false;
        _booked = false;
    }
    
    /**
     * API 'action' method.
     * Implements the client automaton.
     * <ul>
     * <li>Waits a request for proposal (offer request) from the View agent (which represents the user).</li>
     * <li>When a request for porposal is received: forwards to agencies and wait offers (activates time guard).</li>
     * <li>When an offer is received: evaluated the offer and updates best offer.</li>
     * <li>When time guard is over: sends reservation request to the best offer's agency and wait a confirmation letter.</li>
     * <li>When the confirmation letter is received: set state to booked and forwards letter to the view.</li>
     * <ul>
     * See the project reports for more details about the automaton.
     */
    @Override
    public void action()
    {
        if(_waitingOffers)
        {
            long remainingTimeGuard = _timeGuard.remainingTime();
            if(remainingTimeGuard <= 0)
            {
                _waitingOffers = false;
                _requestReservation();
            }
            else
                this.block(remainingTimeGuard);
        }
        else
            this.block();
        
        ACLMessage msg = _myAgent.receive();
        if(msg != null)
        {
            System.out.println("CasomClientAutomatonBehaviour::action : message received from "+msg.getSender());
            Object content;

            try
            {
                content = msg.getContentObject();
                if(content instanceof IMessage)
                {
                    switch(((IMessage)content).getType())
                    {
                        case OFFER_REQUEST : // New offer request from view (user demand)
                            System.out.println("CasomClientAutomatonBehaviour::action : offer request received.");
                            _offerRequest = (IOfferRequest)content;
                            _resetStatus();
                            _requestAgenciesOffers();
                            _timeGuard.initialize((long)_offerRequest.getTimeGuard());
                            break;
                        case OFFER_PACK: 
                            System.out.println("CasomClientAutomatonBehaviour::action : offer pack received.");
                            long remainingTimeGuard = _timeGuard.remainingTime();
                            if(remainingTimeGuard > 0)
                            {
                                System.out.println("CasomClientAutomatonBehaviour::action : evaluating offer pack");
                                _evaluateOffer((IOfferPack)content);
                                
                                System.out.println("CasomClientAutomatonBehaviour::action : remaining time guard "+remainingTimeGuard);
                            }
                            else
                                _waitingOffers = false;
                            break;
                        case CONFIRM_LETTER : 
                            System.out.println("CasomClientAutomatonBehaviour::action : confirm letter received.");
                            if(_reservationRequested)
                            {
                                _confirmationLetter = (IConfirmationLetter)content;
                                _myAgent.setBooked(true);
                                _informUser();
                                System.out.println("CasomClientAutomatonBehaviour::action : confirm letter forwarded.");
                            }
                            break;
                        default:
                            System.err.println("CasomClientAutomatonBehaviour::action : unexpected message content, of class "+content.getClass().getName());
                    }
                }
                else
                {
                    if(content == null)
                        System.err.println("CasomClientAutomatonBehaviour::action : offer request content is null.");
                    else
                        System.err.println("CasomClientAutomatonBehaviour::action : offer request content is of classe "+content.getClass().getName());
                }
            }
            catch (UnreadableException ex)
            {
                System.err.println("CasomClientAutomatonBehaviour::action : acl content object fetching error. "+msg);
                System.err.println("CasomClientAutomatonBehaviour::action : acl content object fetching error. "+ex);
                Logger.getLogger(CasomClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Sends offer request to all known travel agency agents.
     */
    private void _requestAgenciesOffers()
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
                
                _offersRequested = true;
                _waitingOffers = true;
                
                System.out.println("CasomClientAutomatonBehaviour::_requestAgenciesOffers : offer request sent to "+_myAgent.getAgencies());
            }
            catch (IOException ex)
            {
                System.err.println("CasomClientAutomatonBehaviour::_requestAgenciesOffers : acl message sending error. "+ex.getLocalizedMessage());
                Logger.getLogger(CasomClientAutomatonBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            System.err.println("CasomClientAutomatonBehaviour::_requestAgenciesOffers : no registered agency.");
        }
    }
    
    /**
     * Evaluates whether an offer is the best offer or not.
     * @param offerPack the received offer pack.
     */
    private void _evaluateOffer(IOfferPack offerPack)
    {
        IOffer offer = offerPack.getBestOffer();
        
        if(_firstEvaluation)
        {
            _firstEvaluation = false;
            _myAgent.setBestOffer(offer);
        }
        
        if(offer != null && _myAgent.getBestOffer() != null)
        {
            if(offer.getPrice() < _myAgent.getBestOffer().getPrice())
            {
                _myAgent.setBestOffer(offer);
                System.out.println("CasomClientAutomatonBehaviour::_evaluateOffer : offer is the better.");
            }
            else
                System.out.println("CasomClientAutomatonBehaviour::_evaluateOffer : offer is not the better.");
        }
        else
            System.out.println("CasomClientAutomatonBehaviour::_evaluateOffer : myAgent.getBestOffer() is null.");
    }
    
    /**
     * Sends a reservation request to the agency which made the best offer.
     */
    private void _requestReservation()
    {
        if(_myAgent.getBestOffer() != null)
        {
            try
            {
                ACLMessage msg = new ACLMessage(ACLMessage.AGREE);
                
                AID receiver = new AID();
                receiver.setName(_myAgent.getBestOffer().getAgencyID().getName());
                receiver.setLocalName(_myAgent.getBestOffer().getAgencyID().getLocalName());
                receiver.addAddresses(_myAgent.getBestOffer().getAgencyID().getAddress());
                
                msg.addReceiver(receiver);
                msg.setContentObject(new ReservationRequest(_myAgent.getBestOffer()));

                _myAgent.send(msg);
                _reservationRequested = true;
                
                System.out.println("CasomClientAutomatonBehaviour::_requestReservation : reservation request sent to "+receiver);
            }
            catch (IOException ex)
            {
                System.err.println("CasomClientAutomatonBehaviour::_requestReservation : acl content setting error. "+ex.getLocalizedMessage());
                Logger.getLogger(CasomClientAutomatonBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
            System.err.println("CasomClientAutomatonBehaviour::_requestReservation, Warning : myAgent.getBestOffer() is null");
    }
    
    /**
     * Forwards the confirmation to the View agent.
     */
    private void _informUser()
    {
        try
        {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setContentObject(_confirmationLetter);
            
            for(AID view : _myAgent.getViews())
                msg.addReceiver(view);
            
            _myAgent.send(msg);
            
            _booked = true;
            
            System.out.println("CasomClientAutomatonBehaviour::_informUser : confirm letter sent to "+_myAgent.getViews());
        }
        catch (IOException ex)
        {
            System.err.println("CasomClientAutomatonBehaviour::_informUser : ACL message content setting error. "+ex.getLocalizedMessage());
            Logger.getLogger(CasomClientAutomatonBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
