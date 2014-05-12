/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package behaviour;

import jade.core.behaviours.CyclicBehaviour;
import message.IOfferRequest;

/**
 *
 * @author josuah
 */
public class InitBehaviour extends CyclicBehaviour
{
    IOfferRequest _offerRequest;
    
    public void setOfferRequest(IOfferRequest offerRequest)
    {
        _offerRequest = offerRequest;
    }
    
    @Override
    public void action()
    {
        if(_offerRequest != null)
        {
            
            
        }
    }
}
