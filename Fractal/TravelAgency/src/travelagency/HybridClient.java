

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

@Component(provides=@Interface(name="r", signature = java.lang.Runnable.class))
public class HybridClient implements Runnable, IServiceProvider{
    
    @Requires(name="getOffer")
    private IVacationOfferBrowser _getOfferInterface;
    
    @Requires(name="makeReservation")
    private IVacationReservationManager _makeReservationInterface;
    
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
    
    @Override
    public OfferPack requestProposal(IOfferRequest request){
        IOfferPack offerPack = _getOfferInterface.getProposals(request);
        if(!(offerPack instanceof OfferPack))
            throw(new ClassCastException("Client.getOffers: invalid cast: result is not OfferPack."));
        else
            return (OfferPack) offerPack;
    }
    
    @Override
    public ConfirmationLetter reserveOffer(IOffer offer){
        IConfirmationLetter confirmationLetter = _makeReservationInterface.reserveOffer(offer);
        if(!(confirmationLetter instanceof ConfirmationLetter))
            throw(new ClassCastException("Client.makeReservation: invalid cast: result is not ConfirmationLetter."));
        else
            return (ConfirmationLetter) confirmationLetter;    
    }
    
    
}
