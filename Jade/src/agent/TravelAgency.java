/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agent;

import behaviour.StubOfferResponseBehaviour;
import behaviour.StubReservationResponse;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.IMessage;
import message.IOfferRequest;
import message.IReservationRequest;

/**
 *
 * @author josuah
 */
public class TravelAgency extends jade.core.Agent
{
    public static final String ServiceDescription;
    
    static
    {
        ServiceDescription = "Casom-TravelAgency-Stub-Agent";
    }
    
//    public TravelAgency()
//    {
//        
//    }
    
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
        
        ACLMessage msg = this.receive();
        if(msg != null)
        {
            try
            {
                Object content = msg.getContentObject();
                if(content instanceof IMessage)
                {
                    switch(((IMessage)content).getType())
                    {
                        case OFFER_REQUEST : 
                            this.addBehaviour(new StubOfferResponseBehaviour(msg, (IOfferRequest)content));
                            break;
                        case RESERVATION_REQUEST :
                            this.addBehaviour(new StubReservationResponse(msg, (IReservationRequest)content));
                            break;
                    }
                }
            }
            catch (UnreadableException ex)
            {
                Logger.getLogger(TravelAgency.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void _register2DF() throws FIPAException
    {
        ServiceDescription sd = new ServiceDescription();
        sd.setType(TravelAgency.ServiceDescription);
        sd.setName(this.getName());
        
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(this.getAID());
        
        dfd.addServices(sd);
        DFService.register(this, dfd);
    }
}
