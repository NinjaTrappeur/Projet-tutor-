package ws;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import message.MessUtil;
import travelagency.ConfirmationLetter;
import remote.IServiceProvider;
import travelagency.OfferPack;

/**
 *
 * <h1>Ws service provider.</h1>
 * A service provider from remote web service.
 * This is actually a web service client, that transforms the data received from the remote web service into appropriate data for the application.
 * The type (JavaBeans) use for the network are defined in the CasomTypes project.
 */
public class ServiceProvider implements IServiceProvider
{
    /**
     * Responds to a request for proposal.
     * @param offerRequest the request for proposal message.
     * @return IOfferPack a set of vacation offers.
     */
    @Override
    public message.IOfferPack requestProposal(message.IOfferRequest offerRequest)
    {
        message.IOfferPack offerPack = null;
        try
        {
            travelagency.OfferRequest wsOfferRequest = _wsFromMessage(offerRequest);
            System.out.println("WsTravelAgency::requestProposal : agent id "+wsOfferRequest.getAgentId().getName()+wsOfferRequest.getAgentId().getAdresse());
            travelagency.OfferPack wsOfferPack = this.getOffers(wsOfferRequest);
            
            offerPack = _messageFromWs(wsOfferPack);
        }
        catch (DatatypeConfigurationException ex)
        {
            System.err.println("WsTravelAgency::requestProposal : webservice data error. "+ex);
            Logger.getLogger(ServiceProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("WsTravelAgency::requestProposal : returnning offerPack "+offerPack);
        return offerPack;
    }
    
    /**
     * Responds to a reservation request.
     * @param offer the offer to be booked.
     * @return IConfirmationLetter a confirmation letter for the reservation.
     */
    @Override
    public message.IConfirmationLetter reserveOffer(message.IOffer offer)
    {
        travelagency.Offer wsOffer = _wsFromMessage(offer);
        travelagency.ConfirmationLetter wsConfirmLetter = this.makeReservation(wsOffer);
        
        System.out.println("WsTravelAgency::reserveOffer : returning confirmation letter "+_messageFromWs(wsConfirmLetter));
        return _messageFromWs(wsConfirmLetter);
    }

    /********* Web services consommation **********/
    /**
     * A web service client method.
     * Get offers from the web service by providing it an offer request.
     * The parameters an return types need to be converted from/to appropriate types.
     * @param offerRequest an offer request, formatted as a JavaBean (type from CasomTypes, webservice.message).
     * @return an offerPack JavaBean.
     */
    private OfferPack getOffers(travelagency.OfferRequest offerRequest)
    {
        travelagency.WsLinkService service = new travelagency.WsLinkService();
        travelagency.WsLink port = service.getWsLinkPort();
        return port.getOffers(offerRequest);
    }

    /**
     * A web service client method.
     * Get confirmation letter from the web service by providing it a reservation request.
     * The parameters an return types need to be converted from/to appropriate types.
     * @param offer the offer to be booked, formatted as a JavaBean (type from CasomTypes, webservice.message).
     * @return an confirmation letter JavaBean.
     */
    private ConfirmationLetter makeReservation(travelagency.Offer offer)
    {
        travelagency.WsLinkService service = new travelagency.WsLinkService();
        travelagency.WsLink port = service.getWsLinkPort();
        return port.makeReservation(offer);
    }
    /********* End Web services consommation **********/
    
    /**
     * Converts an offer request from the agent side defined type to a JavaBean.
     * @param offerRequest agent side defined offer request.
     * @return JavaBean-like offer request.
     * @throws DatatypeConfigurationException on web service data types configuration error.
     */
    private travelagency.OfferRequest _wsFromMessage(message.IOfferRequest offerRequest) throws DatatypeConfigurationException
    {
        System.out.println("WsTravelAgency::_wsFromMessage : in");
        travelagency.OfferRequest wsOfferRequest = new travelagency.OfferRequest();
        
        wsOfferRequest.setClientName(offerRequest.getClientName());
        wsOfferRequest.setDepartureDate(MessUtil.DateToXMLCalendar(offerRequest.getDepartureDate()));
        wsOfferRequest.setReturnDate(MessUtil.DateToXMLCalendar(offerRequest.getReturnDate()));
        wsOfferRequest.setHighestPrice(offerRequest.getHighestPrice());
        wsOfferRequest.setLowestPrice(offerRequest.getLowestPrice());
        wsOfferRequest.setPlaceName(offerRequest.getPlaceName());
        wsOfferRequest.setTimeGuard(offerRequest.getTimeGuard());
        wsOfferRequest.setType(MessUtil.MessageTypeToString(offerRequest.getType()));

        travelagency.AgentID aid = new travelagency.AgentID();
        aid.setName(offerRequest.getAgentID().getName());
        aid.setLocalName(offerRequest.getAgentID().getLocalName());
        aid.setAdresse(offerRequest.getAgentID().getAddress());
        
        wsOfferRequest.setAgentId(aid);
        
        System.out.println("WsTravelAgency::_wsFromMessage : out");
        
        return wsOfferRequest;
    }
    
    /**
     * Converts an offer from the agent side define type to a JavaBean.
     * @param offer agent side defined offer.
     * @return JavaBean-like offer.
     */
    private travelagency.Offer _wsFromMessage(message.IOffer offer)
    {
        travelagency.Offer wsOffer = new travelagency.Offer();
        
        travelagency.AgentID id = new travelagency.AgentID();
        id.setName(offer.getAgencyID().getName());
        id.setLocalName(offer.getAgencyID().getLocalName());
        id.setAdresse(offer.getAgencyID().getAddress());
        
        wsOffer.setAgency(id);
        wsOffer.setCompanyName(offer.getCompanyName());
        wsOffer.setPrice(offer.getPrice());
        wsOffer.setType(MessUtil.MessageTypeToString(offer.getType()));
        
        return wsOffer;
    }
    
    /**
     * Converts an offer pack from JavaBean to the agent side defined type.
     * @param wsOfferPack JavaBean offer pack.
     * @return IOfferPack agent side defined offer pack.
     */
    private message.IOfferPack _messageFromWs(travelagency.OfferPack wsOfferPack)
    {
        float price;
        String name;
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
    
    /**
     * Converts a confirmation letter from JavaBean to the agent side defined type.
     * @param wsConfirmLetter JavaBean confirmation letter.
     * @return IConfirmationLetter agent side defined confirmation letter.
     */
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
