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
public class ConfirmationLetter implements IConfirmationLetter
{
    protected float _price;
    protected String _companyName;
    protected AID _agency;
    
    public ConfirmationLetter(Offer offer)
    {
        _price = offer.getPrice();
        _companyName = offer.getCompanyName();
        _agency = offer.getAgency();
    }
    
    public ConfirmationLetter(float price, String name, AID agency)
    {
        _price = price;
        _companyName = name;
        _agency = agency;
    }
    
    @Override
    public Type getType()
    {
        return Type.CONFIRM_LETTER;
    }
    
    @Override
    public float getPrice() {
        return _price;
    }
    
    @Override
    public String getCompanyName() {
        return _companyName;
    }
    
    @Override
    public AID getAgency()
    {
        return _agency;
    }
    
    @Override
    public void setAgency(AID agency)
    {
        _agency = agency;
    }

    /**
     * @param price the price to set
     */
    @Override
    public void setPrice(float price) {
        this._price = price;
    }

    /**
     * @param name the companyName to set
     */
    @Override
    public void setCompanyName(String name) {
        this._companyName = name;
    }
    
    @Override
    public String toString()
    {
        return "Confirmation letter from " + _companyName + " :" + _price + "€.";
    }
}
