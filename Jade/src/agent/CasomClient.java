package agent;

import behaviour.*;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.IOffer;

/**
 *
 * <h1>Client agent.</h1>
 * An agent that represents the client of the casom study case.
 */
public class CasomClient extends jade.core.Agent
{
    private ArrayList<AID> _agencies; /*!< Known travel agencies agents.*/
    private ArrayList<AID> _views; /*!< Known views agents.*/
    private boolean _searchRunning; /*!< Tells whether a searching is running.*/
    private boolean _booked; /*!< Tells whether this agent has made a reservation.*/
    private IOffer _bestOffer; /*!< Tells the best offer received until now.*/
    
    public static final String ServiceDescription; /*!< */
    
    static
    {
        ServiceDescription = "Casom-Client-Agent"; /*!< Unique String allowing to register and retrieve this agent in the DF */
    }
    
    /**
     * Constructor.
     */
    public CasomClient()
    {
        super();
        
        _agencies = new ArrayList<AID>();
        _views = new ArrayList<AID>();
        
        _searchRunning = false;
        _booked = false;
        _bestOffer = null;
    }
    
    /**
     * Register to DF, locate needed agents from DF and add my behaviour.
     */
    @Override
    public void setup()
    {
        // DF registration
        try {
            _register2DF();
        } catch (FIPAException ex) {
            System.err.println("CasomClient::setup : DF registration error. "+ex);
            Logger.getLogger(CasomClient.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        // Search needed agents
        this.locatePeers();
        
        this.addBehaviour(new CasomClientAutomatonBehaviour(this));
    }
    
    /**
     * Deregister from DF.
     */
    @Override
    protected void takeDown()
    {
        // Deregister from the yellow pages
        try
        {
            DFService.deregister(this);
        }
        catch (FIPAException fe)
        {
            System.err.println("CasomClient::takeDown : DF de-registration error. "+fe);
            Logger.getLogger(CasomClient.class.getName()).log(Level.SEVERE, null, fe);
        }
        // Printout a dismissal message
        System.out.println(CasomClient.ServiceDescription+" "+getAID().getName()+" terminating.");
        super.takeDown();
    }

    /**
     * Add a description of this agent's services to the DF.
     * @throws FIPAException when an error occurs.
     */
    private void _register2DF() throws FIPAException
    {
        ServiceDescription sd = new ServiceDescription();
        sd.setType(CasomClient.ServiceDescription);
        sd.setName(this.getAID().getName());
        
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(this.getAID());
        
        dfd.addServices(sd);
        DFService.register(this, dfd);
    }
    
    /**
     * Retrives the agents (AIDs) with which this agent needs to communicate.
     * Searches for TravelAgency and ClientView agents
     */
    public void locatePeers()
    {
        DFAgentDescription agencyTemplate = new DFAgentDescription();
        ServiceDescription agencySd = new ServiceDescription();
        
        DFAgentDescription viewTemplate = new DFAgentDescription();
        ServiceDescription viewSd = new ServiceDescription();
        
        // Template for searching TravelAgency agents from DF
        agencySd.setType(TravelAgency.ServiceDescription);
        agencyTemplate.addServices(agencySd);
        
        // Template for searching View agents from DF
        viewSd.setType(ClientView.ServiceDescription);
        viewTemplate.addServices(viewSd);
        
        try
        {
            // Retrieve agencies
            DFAgentDescription[] agencyResult = DFService.search(this, agencyTemplate);
            
            for(int i = 0; i < agencyResult.length; ++i)
                _agencies.add(agencyResult[i].getName());
            
            // Retrieve Views
            DFAgentDescription[] viewResult = DFService.search(this, viewTemplate);
            
            for(int i = 0; i < viewResult.length; ++i)
                _views.add(viewResult[i].getName());
        }
        catch (FIPAException fe)
        {
            System.err.println("CasomClient::_searchDF : DF lookup error. "+fe.getLocalizedMessage());
            Logger.getLogger(CasomClient.class.getName()).log(Level.SEVERE, null, fe);
        }
    }
    
    /**
     * Set the best offer this agent encountered to the given.
     * @param offer the new best offer.
     */
    public void setBestOffer(IOffer offer)
    {
        _bestOffer = offer;
    }
    
    /**
     * Set state of the agent regarding the booking (has it book a travel or not?).
     * @param yesNo should be true if a traval has been booked.
     */
    public void setBooked(boolean yesNo)
    {
        _booked = yesNo;
    }
    
    /**
     * Set the state of the agent regarding a request for proposal.
     * @param yesNo should be true if a travel research is running (a request for proposal has been sent).
     */
    public void setSearchRuinning(boolean yesNo)
    {
        _searchRunning = yesNo;
    }
    
    /**
     * Gets the best offer this agent has encounter until now.
     * @return the known best offer sent to this agent until now.
     */
    public IOffer getBestOffer()
    {
        return _bestOffer;
    }
    
    /**
     * Gets the offer that this agent has booked.
     * @return the booked offer.
     */
    public IOffer getBookedOffer()
    {
        if(_booked)
            return _bestOffer;
        else
            return null;
    }
    
    /**
     * Gets the list of agencies that this agent knows.
     * @return the known agencies.
     */
    public ArrayList<AID> getAgencies()
    {
        if(_agencies.isEmpty())
            this.locatePeers();
        
        return _agencies;
    }
    
    /**
     * Gets the view that this agent knows.
     * @return the view we want to update.
     */
    public ArrayList<AID> getViews()
    {
        if(_views.isEmpty())
            this.locatePeers();
        
        return _views;
    }
    
    /**
     * Tells whether this agent has booked an offer or not.
     * @return true if an offer was booked (reserved).
     */
    public boolean isBooked()
    {
        return _booked;
    }
    
    /**
     * Tels whether a travel search is running.
     * @return true if a travel search is running.
     */
    public boolean isSearchRunning()
    {
        return _searchRunning;
    }
}
