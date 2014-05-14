/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package message;

import java.util.Date;

/**
 *
 * @author josuah
 */
public interface IOfferRequest extends IMessage
{
    public String getClientName();
    public Date getDepartureDate();
    public Date getReturnDate();
    public String getPlaceName();
    public float getLowestPrice();
    public float getHighestPrice();
    public long getTimeGuard();
    public String getAgentID();

    public void setClientName(String name);
    public void setDepartureDate(Date dd);
    public void setReturnDate(Date rd);
    public void setPlaceName(String place);
    public void setLowestPrice(float lp);
    public void setHighestPrice(float hp);
    public void setTimeGuard(long timeguard);
    public void setAgentID(String agentId);
}
