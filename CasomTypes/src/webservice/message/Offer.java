/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webservice.message;

/**
 *
 * @author ninjatrappeur
 */
public class Offer {
    private float price;
    private String name;
    private String agency;
    private String type;
    
    public Offer(float cprice, String cname, String cagency) {
        price = cprice;
        name = cname;
        agency = cagency;
        type = "OFFER";
    }
    
    public float getPrice() {
        return price;
    }
    
    public String getName() {
        return name;
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
