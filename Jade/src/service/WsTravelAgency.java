/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import jade.core.AID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import message.MessUtil;
import travelagency.ConfirmationLetter;
import travelagency.ITravelAgency;
import travelagency.OfferPack;

/**
 *
 * @author josuah
 */
public class WsTravelAgency implements ITravelAgency
{
    @Override
    public message.IOfferPack requestProposal(message.IOfferRequest offerRequest)
    {
        message.IOfferPack offerPack = null;
        try
        {
            travelagency.OfferRequest wsOfferRequest = _wsFromMessage(offerRequest);
            travelagency.OfferPack wsOfferPack = this.getOffers(wsOfferRequest);
            
            offerPack = _messageFromWs(wsOfferPack);
        }
        catch (DatatypeConfigurationException ex)
        {
            System.err.println("WsTravelAgency::requestProposal : webservice data error. "+ex);
            Logger.getLogger(WsTravelAgency.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return offerPack;
    }
    
    @Override
    public message.IConfirmationLetter reserveOffer(message.IOffer offer)
    {
        travelagency.Offer wsOffer = _wsFromMessage(offer);
        travelagency.ConfirmationLetter wsConfirmLetter = this.makeReservation(wsOffer);
        
        return _messageFromWs(wsConfirmLetter);
    }

    /********* Web services consommation **********/
    private OfferPack getOffers(travelagency.OfferRequest offerRequest)
    {
        travelagency.WsLinkService service = new travelagency.WsLinkService();
        travelagency.WsLink port = service.getWsLinkPort();
        return port.getOffers(offerRequest);
    }

    private ConfirmationLetter makeReservation(travelagency.Offer offer)
    {
        travelagency.WsLinkService service = new travelagency.WsLinkService();
        travelagency.WsLink port = service.getWsLinkPort();
        return port.makeReservation(offer);
    }
    /********* End Web services consommation **********/
    
    private travelagency.OfferRequest _wsFromMessage(message.IOfferRequest offerRequest) throws DatatypeConfigurationException
    {
        travelagency.OfferRequest wsOfferRequest = new travelagency.OfferRequest();
        
        wsOfferRequest.setClientName(offerRequest.getClientName());
        wsOfferRequest.setDepartureDate(MessUtil.DateToXMLCalendar(offerRequest.getDepartureDate()));
        wsOfferRequest.setReturnDate(MessUtil.DateToXMLCalendar(offerRequest.getReturnDate()));
        wsOfferRequest.setHighestPrice(offerRequest.getHighestPrice());
        wsOfferRequest.setLowestPrice(offerRequest.getLowestPrice());
        wsOfferRequest.setPlaceName(offerRequest.getPlaceName());
        wsOfferRequest.setTimeGuard(offerRequest.getTimeGuard());
        wsOfferRequest.setType(MessUtil.MessageTypeToString(offerRequest.getType()));
        
        return wsOfferRequest;
    }
    
    private travelagency.Offer _wsFromMessage(message.IOffer offer)
    {
        travelagency.Offer wsOffer = new travelagency.Offer();
        
        travelagency.AgentID id = new travelagency.AgentID();
        id.setName(offer.getAgencyID().getName());
        id.setLocalName(offer.getAgencyID().getLocalName());
        id.setAdresse(offer.getAgencyID().getAdresse());
        
        wsOffer.setAgency(id);
        wsOffer.setCompanyName(offer.getCompanyName());
        wsOffer.setPrice(offer.getPrice());
        wsOffer.setType(MessUtil.MessageTypeToString(offer.getType()));
        
        return wsOffer;
    }
    
    private message.IOfferPack _messageFromWs(travelagency.OfferPack wsOfferPack)
    {
        float price;
        String name;
        AID agency;
        message.IOffer offer;
        
        message.IOfferPack offerPack = new message.OfferPack();
        
        Object [] allOffers = wsOfferPack.getAllOffers().toArray();
        
        for(int i=0; i < allOffers.length; ++i)
        {
            travelagency.Offer wsOffer = (travelagency.Offer) allOffers[i];
            
            price = wsOffer.getPrice();
            name = wsOffer.getCompanyName();
            
            message.AgentID aid = new message.AgentID(wsOffer.getAgency().getName(),
                    wsOffer.getAgency().getLocalName(), wsOffer.getAgency().getAdresse());
            
            offer = new message.Offer(price, name, aid);
            offerPack.addOffer(offer);
            
            if(wsOfferPack.getBestOffer() == i)
            {
                offerPack.setBestOffer(offer);
            }
        }
        
        return offerPack;
    }
    
    private message.IConfirmationLetter _messageFromWs(travelagency.ConfirmationLetter wsConfirmLetter)
    {
        float price = wsConfirmLetter.getPrice();
        String name = wsConfirmLetter.getCompanyName();

        message.AgentID aid = new message.AgentID(wsConfirmLetter.getAgency().getName(),
                wsConfirmLetter.getAgency().getLocalName(), wsConfirmLetter.getAgency().getAdresse());
            
        message.IConfirmationLetter confirmLetter = new message.ConfirmationLetter(price, name, aid);
        
        return confirmLetter;
    }
}
