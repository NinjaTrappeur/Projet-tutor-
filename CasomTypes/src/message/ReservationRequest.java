/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package message;

/**
 *
 * @author josuah
 */
public class ReservationRequest implements IReservationRequest
{
    IOffer _offer;
    
    public ReservationRequest(IOffer offer)
    {
        _offer = offer;
    }

    @Override
    public Type getType()
    {
        return IMessage.Type.RESERVATION_REQUEST;
    }
    
    @Override
    public IOffer getOffer()
    {
        return _offer;
    }
}
