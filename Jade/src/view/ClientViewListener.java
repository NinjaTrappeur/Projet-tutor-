/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import agent.ClientView;
import jade.lang.acl.ACLMessage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.AgentID;
import message.IOfferRequest;
import message.OfferRequest;

/**
 *
 * @author josuah
 */
public class ClientViewListener implements ActionListener
{
    ClientView _viewAgent;
    public ClientViewListener(ClientView view)
    {
        _viewAgent = view;
    }
    
    @Override
    public void actionPerformed(ActionEvent ev)
    {
        float hp, lp;
        long tg;
        String name, place;
        Date dd, rd;
        
        System.out.println("ClientViewListener::actionPerformed : inside actionPerformed");
        
        if(ev.getActionCommand().equals(ClientViewForm.SearchActionCommand))
        {
            System.out.println("ClientViewListener::actionPerformed : action command is ClientViewForm.SearchActionCommand.");
        
            hp = (float)_viewAgent.getForm().maxPrice().getValue();
            lp = (float)_viewAgent.getForm().minPrice().getValue();
            name = _viewAgent.getForm().clientName().getText();
            
            Calendar cal = Calendar.getInstance();
            
            cal.set(Calendar.YEAR, (int)_viewAgent.getForm().departureYear().getValue());
            cal.set(Calendar.MONTH, (int)_viewAgent.getForm().departureMonth().getSelectedIndex());
            cal.set(Calendar.DAY_OF_MONTH, (int)_viewAgent.getForm().departureDay().getValue());
            dd = cal.getTime();
            
            cal.set(Calendar.YEAR, (int)_viewAgent.getForm().returnYear().getValue());
            cal.set(Calendar.MONTH, (int)_viewAgent.getForm().returnMonth().getSelectedIndex());
            cal.set(Calendar.DAY_OF_MONTH, (int)_viewAgent.getForm().returnDay().getValue());
            rd = cal.getTime();
            
            place = (String)_viewAgent.getForm().arrivalCity().getText();
            tg = 30;
            
            IOfferRequest offerRequest = new OfferRequest(hp, lp, name, dd, rd, place, tg,
                    new AgentID(
                            _viewAgent.getAID().getName(),
                            _viewAgent.getAID().getLocalName(),
                            _viewAgent.getAID().getAddressesArray()[0]));
            
            if(_viewAgent.getCasomClientID() != null)
            {
                try
                {
                    ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                    msg.addReceiver(_viewAgent.getCasomClientID());
                    msg.setContentObject(offerRequest);
                    _viewAgent.send(msg);
                    System.out.println("ClientViewListener::actionPerformed : offerRequest sent to "+_viewAgent.getCasomClientID().getName());
                }
                catch (IOException ex)
                {
                    System.err.println("ClientViewListener::actionPerformed : acl message sending error. "+ex);
                    Logger.getLogger(ClientViewListener.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
                System.err.println("ClientViewListener::actionPerformed, Grave: _viewAgent.getCasomClientID() is null.");
        }
    }
}
