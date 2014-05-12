/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package messages;

import java.util.Date;
import message.IOfferRequest;

/**
 *
 * @author ninjatrappeur
 */
public class OfferRequest implements IOfferRequest {
    @Override
    public int highestPrice() {
        return 300;
    }
    
    @Override
    public int lowestPrice() {
        return 100;
    }
    
    @Override
    public String clientName() {
        return "Bill Murray";
    }
    @Override
    public Date departureDate() {
        return new Date();  
    }
    @Override
    public Date returnDate() {
        return new Date();
    }
    @Override
    public String placeName(){
        return "Palavas les flots";
    }
    @Override
    public Type getType() {
        return Type.OFFER_REQUEST;
    }
}
