/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package message;

import java.util.ArrayList;
import javax.jws.WebService;

/**
 *
 * @author ninjatrappeur
 */
@WebService
public class OfferPack implements IOfferPack
{
    private IOffer _bestOffer;
    private ArrayList<IOffer> _allOffers;
    
    public OfferPack(IOffer bestOffer)
    {
        _bestOffer = bestOffer;
        _allOffers = new ArrayList<>();
        _allOffers.add(bestOffer);
    }
    
    public OfferPack()
    {
        _allOffers = new ArrayList<>();
    }
    
    @Override
    public void addOffer(IOffer offer)
    {
        _allOffers.add(offer);
    }
    
    @Override
    public IOffer getBestOffer(){
        return _bestOffer;
    }
    
    @Override
    public ArrayList<IOffer> getAllOffers(){
        return _allOffers;
    }
    
    @Override
    public Type getType() {
        return Type.OFFER_PACK;
    }
    
    @Override
    public void setBestOffer(IOffer offer)
    {
        _bestOffer = offer;
        
        if(!_allOffers.contains(offer))
            _allOffers.add(offer);
    }
    
    @Override
    public void setAllOffers(ArrayList<IOffer> offers)
    {
        _allOffers = offers;
    }
    
    @Override
    public String toString() {
        return "Lowest offer: " + _bestOffer.toString()+"\n\tAll offers: "+_allOffers;
    }
}
