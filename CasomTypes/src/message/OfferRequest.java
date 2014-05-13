/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package message;

import java.util.Date;

/**
 *
 * @author ninjatrappeur
 */
public class OfferRequest implements IOfferRequest {
    
    private final float _highestPrice;
    private final float _lowestPrice;
    private final String _clientName;
    private final Date _departureDate;
    private final Date _returnDate;
    private final String _placeName;
    private final float _timeGuard;
    
    @Override
    public float timeGuard() {
        return _timeGuard;
    }
    
    public OfferRequest (float hp, float lp, String name, Date dd, Date rd, String place, float tg)
    {
        _highestPrice = hp;
        _lowestPrice = lp;
        _clientName = name;
        _departureDate = dd;
        _returnDate = rd;
        _placeName = place;
        _timeGuard = tg;
    }
//    
//    public OfferRequest ()
//    {
//        this(2000, 1, "", new Date(), new Date(), "", 3);
//    }
    
    @Override
    public float highestPrice() {
        return _highestPrice;
    }
    
    @Override
    public float lowestPrice() {
        return _lowestPrice;
    }
    
    @Override
    public String clientName() {
        return _clientName;
    }
    @Override
    public Date departureDate() {
        return _departureDate;  
    }
    @Override
    public Date returnDate() {
        return _returnDate;
    }
    @Override
    public String placeName(){
        return _placeName;
    }
    @Override
    public Type getType() {
        return Type.OFFER_REQUEST;
    }
    
    @Override
    public String toString() {
        return "Offer request from " + _clientName + "between " + _departureDate.toString() +
                " and " + _returnDate + " at " + _placeName + " from " + _lowestPrice + " to " + _highestPrice
                + ".";
    }
}
