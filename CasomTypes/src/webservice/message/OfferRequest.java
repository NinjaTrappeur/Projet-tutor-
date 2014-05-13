package webservice.message;

import java.util.Date;

public class OfferRequest {
    private float highestPrice;
    private float lowestPrice;
    private String clientName;
    private Date departureDate;
    private Date returnDate;
    private String placeName;
    private String type;
    private float timeGuard;
    
    public float getTimeGuard() {
        return timeGuard;
    }
    
    public OfferRequest (float hp, float lp, String name, Date dd, Date rd, String place, float tg)
    {
        highestPrice = hp;
        lowestPrice = lp;
        clientName = name;
        departureDate = dd;
        returnDate = rd;
        placeName = place;
        timeGuard = tg;
        type = "OFFER_REQUEST";
    }
//    
//    public OfferRequest ()
//    {
//        this(2000, 1, "", new Date(), new Date(), "", 3);
//    }
    
    public float getHighestPrice() {
        return highestPrice;
    }
    
    public float getLowestPrice() {
        return lowestPrice;
    }
    
    public String getClientName() {
        return clientName;
    }
    
    public Date getDepartureDate() {
        return departureDate;  
    }
    
    public Date getReturnDate() {
        return returnDate;
    }
    
    public String getPlaceName(){
        return placeName;
    }
    
    public String getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return "Offer request from " + clientName + "between " + departureDate.toString() +
                " and " + returnDate + " at " + placeName + " from " + lowestPrice + " to " + highestPrice
                + ".";
    }

    /**
     * @param highestPrice the highestPrice to set
     */
    public void setHighestPrice(float highestPrice) {
        this.highestPrice = highestPrice;
    }

    /**
     * @param lowestPrice the lowestPrice to set
     */
    public void setLowestPrice(float lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    /**
     * @param clientName the clientName to set
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * @param departureDate the departureDate to set
     */
    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * @param returnDate the returnDate to set
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * @param placeName the placeName to set
     */
    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @param timeGuard the timeGuard to set
     */
    public void setTimeGuard(float timeGuard) {
        this.timeGuard = timeGuard;
    }
}
