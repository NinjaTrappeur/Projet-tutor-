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
public interface IMessage
{
    public enum Type {CONFIRM_LETTER, OFFER, OFFER_REQUEST, OFFER_PACK};
    
    public Type getType();
}
