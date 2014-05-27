

package travelagency;

import jade.wrapper.StaleProxyException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.ConfirmationLetter;
import message.IConfirmationLetter;
import message.IOffer;
import message.IOfferPack;
import message.IOfferRequest;
import message.OfferPack;
import org.objectweb.fractal.fraclet.annotations.Component;
import org.objectweb.fractal.fraclet.annotations.Interface;
import org.objectweb.fractal.fraclet.annotations.Requires;
import remote.IServiceProvider;
import travelagency.interfaces.IVacationOfferBrowser;
import travelagency.interfaces.IVacationReservationManager;
import shared.utils.Launcher;


/**
* Hybrid client component: component that encapsulates a jade Agent.
* 
* This component will register himself as a jade agent to a jade platform which
* is already running. The address and the port of this platform are entered by 
* the user in a interactive way. Note that a jade platform need to be already created
* to use this component.
* 
*/
@Component(provides=@Interface(name="r", signature = java.lang.Runnable.class))
public class HybridClient implements Runnable, IServiceProvider{
    
    @Requires(name="getOffer")
    private IVacationOfferBrowser _getOfferInterface; /*!< Reference to the vacation browser component.*/
    
    @Requires(name="makeReservation")
    private IVacationReservationManager _makeReservationInterface; /*!< Reference to the vacation reservation component.*/
    
    
    /**
     * This function will be called by the ADL launcher. 
     * 
     * The goal of this function is to create a jade agent and register him to 
     * a jade platform. The host and the port of this platform are asked to the
     * user using System.in.
     */
    @Override
    public void run() {
        
        Scanner sc = new Scanner(System.in);
        String host;
        String port;
        
        System.out.println("Please specify the jade platform host (without port)");
        host = sc.nextLine();
        System.out.println("Please specify the jade platform port.");
        port = sc.nextLine();
        String [] containerArgs = {"-container", "-host", host, "-port", port};

        jade.wrapper.AgentContainer container = Launcher.boot(containerArgs);

        try
        {
            shared.agent.HybridAgent client = new shared.agent.HybridAgent(this);
            
            container.acceptNewAgent("travelAgency", client).start();
        }
        catch (StaleProxyException ex)
        {
            Logger.getLogger(HybridClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Return the offer avalaible for a request using the vacation browser component.
     * 
     * @param request Offer request.
     * @return List of offers that match with the request given in parameter.
     */
    @Override
    public OfferPack requestProposal(IOfferRequest request){
        IOfferPack offerPack = _getOfferInterface.getProposals(request);
        if(!(offerPack instanceof OfferPack))
            throw(new ClassCastException("Client.getOffers: invalid cast: result is not OfferPack."));
        else
            return (OfferPack) offerPack;
    }
    
    /**
     * Make a reservation for an Offer using the vacation reservation component.
     * 
     * @param offer Offer that will be reserved by the client.
     * @return Confirmation of the reservation.
     */
    @Override
    public ConfirmationLetter reserveOffer(IOffer offer){
        IConfirmationLetter confirmationLetter = _makeReservationInterface.reserveOffer(offer);
        if(!(confirmationLetter instanceof ConfirmationLetter))
            throw(new ClassCastException("Client.makeReservation: invalid cast: result is not ConfirmationLetter."));
        else
            return (ConfirmationLetter) confirmationLetter;    
    }
    
    
}
