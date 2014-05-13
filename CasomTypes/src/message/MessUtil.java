/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package message;

import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author josuah
 */
public class MessUtil
{
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
    
    public static IMessage.Type MessageTypeFromString(String typeStr)
    {
        IMessage.Type type = null;
        
        switch(typeStr)
        {
            case "OFFER_REQUEST":
                type = IMessage.Type.OFFER_REQUEST;
                break;
            case "OFFER_PACK":
                type = IMessage.Type.OFFER_PACK;
                break;
            case "RESERVATION_REQUEST":
                type = IMessage.Type.RESERVATION_REQUEST;
                break;
            case "CONFIRM_LETTER":
                type = IMessage.Type.CONFIRM_LETTER;
                break;
            case "OFFER":
                type = IMessage.Type.OFFER;
                break;
        }
        
        return type;
    }
    
    public static Date DateFromXMLCalendar(XMLGregorianCalendar xmlcal)
    {
        return xmlcal.toGregorianCalendar().getTime();
    }
    
    public static XMLGregorianCalendar DateToXMLCalendar(Date date) throws DatatypeConfigurationException
    {
        GregorianCalendar gregory = new GregorianCalendar();
        gregory.setTime(date);

        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregory);
    }
}
