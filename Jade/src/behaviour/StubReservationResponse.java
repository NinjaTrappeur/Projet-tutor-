/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package behaviour;

import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.IConfirmationLetter;
import message.IReservationRequest;
import travelagency.ITravelAgency;

/**
 *
 * @author josuah
 */
public class StubReservationResponse extends jade.core.behaviours.OneShotBehaviour
{
    ITravelAgency _remoteAgency;
    IReservationRequest _reservationRequest;
    ACLMessage _receivedMsg;
    
    public StubReservationResponse(ACLMessage msg, IReservationRequest reservationRequest)
    {
        _reservationRequest = reservationRequest;
        _receivedMsg = msg;
        
        _remoteAgency = null;
    }
    
    @Override
    public void action()
    {
        IConfirmationLetter confirmationLetter = _remoteAgency.reserveOffer(_reservationRequest.getOffer());
        
        try
        {
            ACLMessage msg = _receivedMsg.createReply();
            msg.setContentObject(confirmationLetter);
            myAgent.send(msg);
        }
        catch (IOException ex)
        {
            Logger.getLogger(StubOfferResponseBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
