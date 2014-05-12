/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agent;

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
