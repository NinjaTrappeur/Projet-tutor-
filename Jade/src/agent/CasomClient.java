/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
 * @author josuah
 */
public class CasomClient extends jade.core.Agent
{
    private ArrayList<AID> _agencies;
    private ArrayList<AID> _views;
    private boolean _searchRunning;
    private boolean _booked;
    private IOffer _bestOffer;
    
    public static final String ServiceDescription;
    
    static
    {
        ServiceDescription = "Casom-Client-Agent";
    }
    
    public CasomClient()
    {
        super();
        
        _agencies = new ArrayList<AID>();
        _views = new ArrayList<AID>();
        
        _searchRunning = false;
        _booked = false;
        _bestOffer = null;
    }
    
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
        
        message.AgentID id = new message.AgentID(this.getAID().getName(), this.getAID().getLocalName(), this.getAID().getAddressesArray()[0]);
        AID aid  = new AID();
        aid.setName(id.getName());
        aid.setLocalName(id.getLocalName());
        aid.addAddresses(id.getAdresse());
        System.out.println(this.getAID());
        System.out.println(aid);
        
        // Search needed agents
        this.locatePeers();
        
        this.addBehaviour(new CasomClientAutomatonBehaviour(this));
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
            System.err.println("CasomClient::takeDown : DF de-registration error. "+fe);
            Logger.getLogger(CasomClient.class.getName()).log(Level.SEVERE, null, fe);
        }
        // Printout a dismissal message
        System.out.println(CasomClient.ServiceDescription+" "+getAID().getName()+" terminating.");
    }

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
    
    public void setBestOffer(IOffer offer)
    {
        _bestOffer = offer;
    }
    
    public void setBooked(boolean yesNo)
    {
        _booked = yesNo;
    }
    
    public void setSearchRuinning(boolean yesNo)
    {
        _searchRunning = yesNo;
    }
    
    public IOffer getBestOffer()
    {
        return _bestOffer;
    }
    
    public IOffer getBookedOffer()
    {
        if(_booked)
            return _bestOffer;
        else
            return null;
    }
    
    public ArrayList<AID> getAgencies()
    {
        if(_agencies.isEmpty())
            this.locatePeers();
        
        return _agencies;
    }
    
    public ArrayList<AID> getViews()
    {
        if(_views.isEmpty())
            this.locatePeers();
        
        return _views;
    }
    
    public boolean isBooked()
    {
        return _booked;
    }
    
    public boolean isSearchRunning()
    {
        return _searchRunning;
    }
}
