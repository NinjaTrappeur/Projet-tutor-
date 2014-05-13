/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservice.message;

import java.io.Serializable;

/**
 *
 * @author ninjatrappeur
 */
public class Offer implements Serializable{
    private float price;
    private String name;
    private String agency;
    private String type;
    
    public Offer(float cprice, String cname, String cagency) 
    {
        super();
        price = cprice;
        name = cname;
        agency = cagency;
        type = "OFFER";
    }
    
    public Offer()
    {
        this(1, "", "");
    }
    
    public void setPrice(float cprice) 
    {
        price = cprice;
    }
    
    public float getPrice()
    {
        return price;
    }
    
    public void setName(String cname) 
    {
        name = cname;
    }
    public String getName() 
    {
        return name;
    }
    
    public void setType(String ctype) 
    {
        type = ctype;
    }
    public String getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return "Offer from " + name + " :" + price + "€.";
    }
    public void setAgency(String cagency)
    {
        agency = cagency;
    }
    
    public String getAgency()
    {
        return agency;
    }
}
