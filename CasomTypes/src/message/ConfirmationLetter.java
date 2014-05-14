/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package message;

/**
 *
 * @author ninjatrappeur
 */
public class ConfirmationLetter implements IConfirmationLetter
{
    protected float _price;
    protected String _companyName;
    protected String _agencyID;
    
    public ConfirmationLetter(Offer offer)
    {
        _price = offer.getPrice();
        _companyName = offer.getCompanyName();
        _agencyID = offer.getAgencyID();
    }
    
    public ConfirmationLetter(float price, String name, String agency)
    {
        _price = price;
        _companyName = name;
        _agencyID = agency;
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
    public String getAgencyID()
    {
        return _agencyID;
    }
    
    @Override
    public void setAgencyID(String agency)
    {
        _agencyID = agency;
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
