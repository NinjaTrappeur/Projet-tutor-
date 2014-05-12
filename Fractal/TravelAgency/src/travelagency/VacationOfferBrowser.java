/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package travelagency;

import message.IOfferPack;
import message.IOfferRequest;
import message.Offer;
import message.OfferPack;
import travelagency.interfaces.IVacationOfferBrowser;
import org.objectweb.fractal.fraclet.annotations.Component;
import org.objectweb.fractal.fraclet.annotations.Interface;

@Component(provides=@Interface(name="getProposals", signature = travelagency.interfaces.IVacationOfferBrowser.class))
public class VacationOfferBrowser implements IVacationOfferBrowser{
    
    @Override
    public IOfferPack getProposals(IOfferRequest offerRequest) {
        return new OfferPack(new Offer(100, "Bruce Willis"));
    }
}
