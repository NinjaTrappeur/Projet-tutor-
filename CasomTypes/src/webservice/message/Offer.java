/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservice.message;

import java.io.Serializable;
import message.AgentID;

/**
 *
 * @author ninjatrappeur
 */
public class Offer implements Serializable{
    protected float price;
    protected String companyName;
    protected AgentID agency;
    protected String type;
    
    public Offer(float cprice, String cname, AgentID cagency) 
    {
        super();
        price = cprice;
        companyName = cname;
        agency = cagency;
        type = "OFFER";
    }
    
    public Offer()
    {
        this(1, "", new AgentID());
    }
    
    public void setPrice(float cprice) 
    {
        price = cprice;
    }
    
    public float getPrice()
    {
        return price;
    }
    
    public void setCompanyName(String cname) 
    {
        companyName = cname;
    }
    public String getCompanyName() 
    {
        return companyName;
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
        return "Offer from " + companyName + " :" + price + "€.";
    }
    public void setAgency(AgentID cagency)
    {
        agency = cagency;
    }
    
    public AgentID getAgency()
    {
        return agency;
    }
}
