/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package message;

import jade.core.AID;

/**
 *
 * @author ninjatrappeur
 */
public class Offer implements IOffer{
    private float _price;
    private String _name;
    
    public Offer(float price, String name) {
        _price = price;
        _name = name;
    }
    
    @Override
    public float price() {
        return _price;
    }
    
    @Override
    public String companyName() {
        return _name;
    }
    @Override
    public Type getType() {
        return Type.OFFER;
    }
    
    @Override
    public String toString() {
        return "Offer from " + _name + " :" + _price + "€.";
    }
    @Override
    public void setAgency(AID agency)
    {
        
    }
    
    @Override
    public AID getAgency()
    {
        return null;
    }
}
