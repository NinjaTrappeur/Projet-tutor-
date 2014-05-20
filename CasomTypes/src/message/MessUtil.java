package message;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * Utilitaries that provide operation on messages.
 */
public class MessUtil
{
    /**
     * Converts the type of a message to String.
     * @param type a message type
     * @return corresponding string.
     */
    public static String MessageTypeToString(IMessage.Type type)
    {
        String str = "undefined";
        
        switch(type)
        {
            case OFFER_REQUEST:
                str = "OFFER_REQUEST";
                break;
            case OFFER_PACK:
                str = "OFFER_PACK";
                break;
            case RESERVATION_REQUEST:
                str = "RESERVATION_REQUEST";
                break;
            case CONFIRM_LETTER:
                str = "CONFIRM_LETTER";
                break;
            case OFFER:
                str = "OFFER";
                break;
        }
        
        return str;
    }
    
    /**
     * Converts a String to a message type.
     * @param typeStr String to be converted to message type.
     * @return corresponding message type.
     */
    public static IMessage.Type MessageTypeFromString(String typeStr)
    {
        IMessage.Type type = null;
        
        if(typeStr.equals("OFFER_REQUEST"))
            type = IMessage.Type.OFFER_REQUEST;
        else if(typeStr.equals("OFFER_PACK"))
            type = IMessage.Type.OFFER_PACK;
        else if(typeStr.equals("RESERVATION_REQUEST"))
            type = IMessage.Type.RESERVATION_REQUEST;
        else if(typeStr.equals("CONFIRM_LETTER"))
            type = IMessage.Type.CONFIRM_LETTER;
        else if(typeStr.equals("OFFER"))
            type = IMessage.Type.OFFER;
        
        return type;
    }
    
    /**
     * Converts XMLGregorianCalendar type (received from web service) to java.util.Date.
     * @param xmlcal date received from web service.
     * @return corresponding java.util.Date.
     */
    public static Date DateFromXMLCalendar(XMLGregorianCalendar xmlcal)
    {
        return xmlcal.toGregorianCalendar().getTime();
    }
    
    /**
     * Converts a java.util.Date to XMLGregorianCalendar, for use in web services.
     * @param date date representation in program.
     * @return corresponding XMLGregorianCalendar.
     * @throws DatatypeConfigurationException 
     */
    public static XMLGregorianCalendar DateToXMLCalendar(Date date) throws DatatypeConfigurationException
    {
        GregorianCalendar gregory = new GregorianCalendar();
        gregory.setTime(date);

        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory);
    }
}
