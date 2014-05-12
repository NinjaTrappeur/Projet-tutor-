/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package message;

import jade.util.leap.Serializable;

/**
 *
 * @author josuah
 */
public interface IMessage extends Serializable
{
    public enum Type {QUIT_REQUEST, OFFER_REQUEST, OFFER_PACK, RESERVATION_REQUEST, CONFIRM_LETTER, OFFER};
    
    public Type getType();
}
