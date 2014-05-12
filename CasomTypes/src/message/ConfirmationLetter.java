/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package message;

import message.IConfirmationLetter;

/**
 *
 * @author ninjatrappeur
 */
public class ConfirmationLetter implements IConfirmationLetter{
    
    @Override
    public Type getType() {
        return Type.CONFIRM_LETTER;
    }
    
    @Override
    public String toString() {
        return "Confirmation Letter.";
    }
}
