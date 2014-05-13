/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
 *
 * @author josuah
 */
public class ClientView extends jade.core.Agent
{
    ClientViewForm _ui;
    AID _casomClient;
    
    public final static String ServiceDescription;
    
    static
    {
        ServiceDescription = "Casom-View-Agent";
    }
    
    public ClientView()
    {
        super();
        
        _ui = new ClientViewForm();
        
        _ui.searchButton().setActionCommand(ClientViewForm.SearchActionCommand);
        _ui.searchButton().addActionListener(new ClientViewListener(this));
        
        _casomClient = null;
        
        // DF registration
        try {
            _register2DF();
        } catch (FIPAException ex) {
            System.err.println("CasomClient::setup : DF registration error. "+ex);
            Logger.getLogger(ClientView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void setup()
    {
        _searchDF();
        
        _ui.setVisible(true);
        
        this.addBehaviour(new OutputUpdaterBehaviour(this));
    }
    
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
    }
    
    private void _register2DF() throws FIPAException
    {
        ServiceDescription sd = new ServiceDescription();
        sd.setType(ClientView.ServiceDescription);
        sd.setName(this.getName());
        
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(this.getAID());
        
        dfd.addServices(sd);
        DFService.register(this, dfd);
    }
    
    private void _searchDF()
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
    
    public ClientViewForm getForm()
    {
        return _ui;
    }
    
    public AID getCasomClientID()
    {
        return _casomClient;
    }
}
