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
    }
    
    @Override
    public void setup()
    {
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        DFAgentDescription[] result;
        
        // DF registration
        try {
            _register2DF();
        } catch (FIPAException ex) {
            System.err.println("CasomClient::setup : DF registration error. "+ex);
            Logger.getLogger(CasomClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
            Logger.getLogger(ClientViewListener.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        _ui.setVisible(true);
        
        this.addBehaviour(new OutputUpdaterBehaviour(this));
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
    
    public ClientViewForm getForm()
    {
        return _ui;
    }
    
    public AID getCasomClientID()
    {
        return _casomClient;
    }
}
