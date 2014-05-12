/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package travelagency;

import message.IOfferPack;
import message.IOfferRequest;
import messages.OfferPack;
import travelagency.interfaces.IVacationOfferBrowser;

@Component(provides=@Interface(name="vacationOfferBrowser", signature = travelagency.interfaces.IVacationOfferBrowser.class))
public class VacationOfferBrowser implements IVacationOfferBrowser{
    
    @Override
    public IOfferPack getProposals(IOfferRequest offerRequest) {
        return new OfferPack();
    }
}
