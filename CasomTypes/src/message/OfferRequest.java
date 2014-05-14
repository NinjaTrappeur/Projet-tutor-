/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package message;

import jade.core.AID;
import java.util.Date;

/**
 *
 * @author ninjatrappeur
 */
public class OfferRequest implements IOfferRequest {
    
    private float _highestPrice;
    private float _lowestPrice;
    private String _clientName;
    private Date _departureDate;
    private Date _returnDate;
    private String _placeName;
    private long _timeGuard;
    private AID _agentID;
    
    @Override
    public long getTimeGuard() {
        return _timeGuard;
    }
    
    public OfferRequest (float hp, float lp, String name, Date dd, Date rd, String place, long tg,
            AID agentID)
    {
        _highestPrice = hp;
        _lowestPrice = lp;
        _clientName = name;
        _departureDate = dd;
        _returnDate = rd;
        _placeName = place;
        _timeGuard = tg;
        _agentID = agentID;
        
    }
    
    @Override
    public float getHighestPrice() {
        return _highestPrice;
    }
    
    @Override
    public float getLowestPrice() {
        return _lowestPrice;
    }
    
    @Override
    public String getClientName() {
        return _clientName;
    }
    @Override
    public Date getDepartureDate() {
        return _departureDate;  
    }
    @Override
    public Date getReturnDate() {
        return _returnDate;
    }
    @Override
    public String getPlaceName(){
        return _placeName;
    }
    @Override
    public Type getType() {
        return Type.OFFER_REQUEST;
    }

    /**
     * @param _highestPrice the _highestPrice to set
     */
    @Override
    public void setHighestPrice(float _highestPrice) {
        this._highestPrice = _highestPrice;
    }

    /**
     * @param _lowestPrice the _lowestPrice to set
     */
    @Override
    public void setLowestPrice(float _lowestPrice) {
        this._lowestPrice = _lowestPrice;
    }

    /**
     * @param _clientName the _clientName to set
     */
    @Override
    public void setClientName(String _clientName) {
        this._clientName = _clientName;
    }

    /**
     * @param _departureDate the _departureDate to set
     */
    @Override
    public void setDepartureDate(Date _departureDate) {
        this._departureDate = _departureDate;
    }

    /**
     * @param _returnDate the _returnDate to set
     */
    @Override
    public void setReturnDate(Date _returnDate) {
        this._returnDate = _returnDate;
    }

    /**
     * @param _placeName the _placeName to set
     */
    @Override
    public void setPlaceName(String _placeName) {
        this._placeName = _placeName;
    }

    /**
     * @param _timeGuard the _timeGuard to set
     */
    @Override
    public void setTimeGuard(long _timeGuard) {
        this._timeGuard = _timeGuard;
    }
    
    @Override
    public String toString() {
        return "Offer request from " + _clientName + "between " + _departureDate.toString() +
                " and " + _returnDate + " at " + _placeName + " from " + _lowestPrice + " to " + _highestPrice
                + ".";
    }

    /**
     * @return the _agentID
     */
    public AID getAgentID() {
        return _agentID;
    }

    /**
     * @param _agentID the _agentID to set
     */
    public void setAgentID(AID _agentID) {
        this._agentID = _agentID;
    }
}
