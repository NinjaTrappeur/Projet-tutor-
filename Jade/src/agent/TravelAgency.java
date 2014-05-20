package agent;

import behaviour.TravelAgencyAutomatonBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.logging.Level;
import java.util.logging.Logger;
import travelagency.ITravelAgency;




/**
 * <h1>Travel agency agent.</h1>
 * An agent that represents the travel agency.
 */
public class TravelAgency extends jade.core.Agent
{
    private boolean _remoteMode; /*!< Tell whether we get our actual services from remote web service.*/
    
    public static final String ServiceDescription; /*!< Unique String allowing to register and retrieve this agent in the DF */
    
    static
    {
        ServiceDescription = "Casom-TravelAgency-Stub-Agent";
    }
    
    /**
     * Constructor.
     * @param remoteMode should be true when we want to get actual services from the remote web service.
     */
    public TravelAgency(boolean remoteMode)
    {
        super();
        _remoteMode = remoteMode;
    }
    
    /**
     * Default constructor.
     * remote mode is false by default.
     */
    public TravelAgency()
    {
        this(false);
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
            System.err.println("TravelAgency::setup : DF registration error. "+ex);
            Logger.getLogger(TravelAgency.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ITravelAgency remoteAgencyStub = (_remoteMode) ? new ws.ServiceProvider() : new fake.ServiceProvider(this);
        this.addBehaviour(new TravelAgencyAutomatonBehaviour(this, remoteAgencyStub));
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
            System.err.println("TravelAgency::takeDown : DF de-registration error. "+fe);
            Logger.getLogger(TravelAgency.class.getName()).log(Level.SEVERE, null, fe);
        }
        // Printout a dismissal message
        System.out.println(TravelAgency.ServiceDescription+" "+getAID().getName()+" terminating.");
        super.takeDown();
    }
    
    /**
     * Add a description of this agent's services to the DF.
     * @throws FIPAException when an error occurs.
     */
    private void _register2DF() throws FIPAException
    {
        ServiceDescription sd = new ServiceDescription();
        sd.setType(TravelAgency.ServiceDescription);
        sd.setName(this.getAID().getName());
        
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(this.getAID());
        
        dfd.addServices(sd);
        DFService.register(this, dfd);
    }
    
    /**
     * Tells whether this agent is working in remote mode, ie get the actual services from a remote web service.
     * @return 
     */
    public boolean isRemoteMode()
    {
        return _remoteMode;
    }
}
