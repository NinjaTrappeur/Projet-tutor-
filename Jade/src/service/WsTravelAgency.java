/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package service;

import jade.core.AID;
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
        travelagency.OfferRequest wsOfferRequest = _wsFromMessage(offerRequest);
        travelagency.OfferPack wsOfferPAck = this.getOffers(wsOfferRequest);
        
        return _messageFromWs(wsOfferPAck);
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
    
    private travelagency.OfferRequest _wsFromMessage(message.IOfferRequest offerRequest)
    {
        travelagency.OfferRequest wsOfferRequest = new travelagency.OfferRequest();
        
        wsOfferRequest.setClientName(offerRequest.getClientName());
        wsOfferRequest.setDepartureDate(null);
        wsOfferRequest.setHighestPrice(value);
        wsOfferRequest.setLowestPrice(value);
        wsOfferRequest.setPlaceName(null);
        wsOfferRequest.setReturnDate(null);
        wsOfferRequest.setTimeGuard(value);
        wsOfferRequest.setType(null);
        
        return wsOfferRequest;
    }
    
    private travelagency.Offer _wsFromMessage(message.IOffer offer)
    {
        
    }
    
    private message.IOfferPack _messageFromWs(travelagency.OfferPack wsOfferPack)
    {
        
    }
    
    private message.IConfirmationLetter _messageFromWs(travelagency.ConfirmationLetter wsConfirmLetter)
    {
        
    }
}
