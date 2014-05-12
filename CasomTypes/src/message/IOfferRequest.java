/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package message;

import java.util.Date;

/**
 *
 * @author josuah
 */
public interface IOfferRequest extends IMessage
{
    public String clientName();
    public Date departureDate();
    public Date returnDate();
    public String placeName();
    public float lowestPrice();
    public float highestPrice();
    public float timeGuard();
}
