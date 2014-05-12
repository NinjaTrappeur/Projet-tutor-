/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package messages;

import message.IOffer;
import message.IOfferPack;

/**
 *
 * @author ninjatrappeur
 */
public class OfferPack implements IOfferPack {
    private final IOffer _bestOffer;
    
    public OfferPack(IOffer bestOffer) {
        _bestOffer = bestOffer;
    }
    
    @Override
    public IOffer lowestPrice(){
        return new Offer(100, "Hôtel de Rogé");
    }
    
    @Override
    public Type getType() {
        return Type.OFFER_PACK;
    }
    
    @Override
    public String toString() {
        return "Lowest offer: " + _bestOffer.toString();
    }
}
