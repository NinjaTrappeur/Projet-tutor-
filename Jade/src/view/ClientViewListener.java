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
        float hp, lp, tg;
        String name, place;
        Date dd, rd;
        
        if(ev.getActionCommand().equals(ClientViewForm.SearchActionCommand))
        {
            hp = (float)_viewAgent.getForm().maxPrice().getValue();
            lp = (float)_viewAgent.getForm().minPrice().getValue();
            name = "Gorges";
            
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
            
            IOfferRequest offerRequest = new OfferRequest(hp, lp, name, dd, rd, place, tg);
            
            try
            {
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(_viewAgent.getCasomClientID());
                msg.setContentObject(offerRequest);
                _viewAgent.send(msg);
            }
            catch (IOException ex)
            {
                Logger.getLogger(ClientViewListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
