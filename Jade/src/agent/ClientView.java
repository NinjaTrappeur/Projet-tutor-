package agent;

import behaviour.OutputUpdaterBehaviour;
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.ClientViewForm;
import view.ClientViewListener;

/**
 *<h1>View Agent.</h1>
 * An agent that holds the view.
 */
public class ClientView extends jade.core.Agent
{
    ClientViewForm _ui; /*!< The Jframe form.*/
    AID _casomClient; /*!< The client we get updates from.*/
    
    public final static String ServiceDescription; /*!< Unique String allowing to register and retrieve this agent in the DF */
    
    static
    {
        ServiceDescription = "Casom-View-Agent";
    }
    
    /**
     * Constructor.
     */
    public ClientView()
    {
        super();
        
        _ui = new ClientViewForm();
        
        _ui.searchButton().setActionCommand(ClientViewForm.SearchActionCommand);
        _ui.searchButton().addActionListener(new ClientViewListener(this));
        
        _casomClient = null;
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
            Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Search needed agents
        this.locatePeers();
        
        _ui.setVisible(true);
        
        this.addBehaviour(new OutputUpdaterBehaviour(this));
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
            System.err.println("ClientView::takeDown : DF de-registration error. "+fe);
            Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, fe);
        }
        // Printout a dismissal message
        System.out.println(ClientView.ServiceDescription+" "+getAID().getName()+" terminating.");
        super.takeDown();
    }
    
    /**
     * Add a description of this agent's services to the DF.
     * @throws FIPAException when an error occurs.
     */
    private void _register2DF() throws FIPAException
    {
        ServiceDescription sd = new ServiceDescription();
        sd.setType(ClientView.ServiceDescription);
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
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        DFAgentDescription[] result;
        
        sd.setType(CasomClient.ServiceDescription);
        template.addServices(sd);
        
        try
        {
            result = DFService.search(this, template);
            if(result.length > 0)
               _casomClient  = result[0].getName();
        }
        catch (FIPAException ex)
        {
            System.err.println("ClientView::_searchDF : DF lookup error. "+ex.getLocalizedMessage());
            Logger.getLogger(ClientViewListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Gets the ClientViewFrom form managed by this agent.
     * @return ClientViewFrom  the form.
     */
    public ClientViewForm getForm()
    {
        return _ui;
    }
    
    /**
     * Get the AID of the client agent we wait updates from.
     * @return AID the AID of the bounded client agent.
     */
    public AID getCasomClientID()
    {
        if(_casomClient == null)
            this.locatePeers();
        
        return _casomClient;
    }
}
