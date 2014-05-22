package shared.agent;

import shared.behaviour.HybridAutomatonBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.logging.Level;
import java.util.logging.Logger;
import remote.IServiceProvider;

/**
 *
 * 
 */
public class HybridAgent extends jade.core.Agent
{
    private IServiceProvider _serviceProvider; /*!< The object that actually provides the services for this agent (used in the automaton behaviour).*/
    
    public static final String ServiceDescription; /*!< Unique String allowing to register and retrieve this agent in the DF */
    
    static
    {
        ServiceDescription = "Casom-TravelAgency-Stub-Agent";
    }
    
    /**
     * Constructor.
     * @param serviceProvider an object that implements ITravalAgency, and provides the actual services for this agent.
     */
    public HybridAgent(IServiceProvider serviceProvider)
    {
        super();
        _serviceProvider = serviceProvider;
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
            Logger.getLogger(HybridAgent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.addBehaviour(new HybridAutomatonBehaviour(this, _serviceProvider));
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
            Logger.getLogger(HybridAgent.class.getName()).log(Level.SEVERE, null, fe);
        }
        // Printout a dismissal message
        System.out.println(HybridAgent.ServiceDescription+" "+getAID().getName()+" terminating.");
        super.takeDown();
    }
    
    /**
     * Add a description of this agent's services to the DF.
     * @throws FIPAException when an error occurs.
     */
    private void _register2DF() throws FIPAException
    {
        ServiceDescription sd = new ServiceDescription();
        sd.setType(HybridAgent.ServiceDescription);
        sd.setName(this.getAID().getName());
        
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(this.getAID());
        
        dfd.addServices(sd);
        DFService.register(this, dfd);
    }
}