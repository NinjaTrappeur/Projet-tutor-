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
public interface IOffer  extends IMessage
{
    public float getPrice();
    public String getCompanyName();
    public AgentID getAgencyID();
    
    public void setPrice(float price);
    public void setCompanyName(String comName);
    public void setAgencyID(AgentID agency);
}
