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
 * It internaly uses a class that implements travelagency.ITravelAgency, to provides it's services.
 * This implementor of ITravelAgency is passed to the automaton behaviour of the agent.
 */
public class TravelAgency extends jade.core.Agent
{
    private boolean _remoteMode; /*!< Tell whether we get our actual services from remote web service.*/
    private ITravelAgency _serviceProvider;
    
    public static final String ServiceDescription; /*!< Unique String allowing to register and retrieve this agent in the DF */
    
    static
    {
        ServiceDescription = "Casom-TravelAgency-Stub-Agent";
    }
    
    /**
     * Constructor.
     * @param remoteMode should be true when we want to get actual services from the remote web service.
     * @param serviceProvider an object that implements ITravalAgency, and provides the actual services for this agent.
     */
    public TravelAgency(boolean remoteMode, ITravelAgency serviceProvider)
    {
        super();
        _remoteMode = remoteMode;
        _serviceProvider = serviceProvider;
    }
    
    /**
     * Constructor.
     * The service provider for this agent is set according to the value of remoteMode:
     * <ul>
     *  <li>remoteMode = true => serviceProvider is ws.ServiceProvider.</li>
     *  <li>remoteMode = false => serviceProvider is fake.ServiceProvider.</li>
     * </ul>
     * @param remoteMode 
     */
    public TravelAgency(boolean remoteMode)
    {
        this(remoteMode, null);
        _serviceProvider = (remoteMode) ? new ws.ServiceProvider() : new fake.ServiceProvider(this);
    }
    
    /**
     * Constructor
     * The remote mode is set to false by default.
     * @param serviceProvider an object that implements ITravalAgency, and provides the actual services for this agent.
     */
    public TravelAgency(ITravelAgency serviceProvider)
    {
        this(false, serviceProvider);
    }

    /**
     * Default constructor.
     * remote mode is false by default.
     * The service provider is set to fake.ServiceProvider.
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
        
        this.addBehaviour(new TravelAgencyAutomatonBehaviour(this, _serviceProvider));
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
