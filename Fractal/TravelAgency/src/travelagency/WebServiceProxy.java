
package travelagency;
import java.util.Date;
import message.IConfirmationLetter;
import message.IOffer;
import message.IOfferPack;
import message.IOfferRequest;
import messages.Offer;
import messages.OfferRequest;
import org.objectweb.fractal.fraclet.annotations.Component;
import org.objectweb.fractal.fraclet.annotations.Interface;
import org.objectweb.fractal.fraclet.annotations.Requires;
import travelagency.interfaces.IVacationOfferBrowser;
import travelagency.interfaces.IVacationReservationManager;

@Component(provides=@Interface(name="r", signature = java.lang.Runnable.class))
public class WebServiceProxy implements ITravelAgency, Runnable {
    public WebServiceProxy() {
        
    }
    
    @Override
    public void run() {
        System.out.println("vacation offer browser: " + requestProposal(new OfferRequest(10,10,"ets",
        new Date(), new Date(), "hola")).toString());
        System.out.println("vacationReservation: " + reserveOffer(new Offer(10, "couco")).toString());
    }
    
    @Requires(name = "vacationOfferBrowser")
    private IVacationOfferBrowser _vacationOfferBrowser;
    
    @Requires(name = "vacationReservationManager")
    private IVacationReservationManager _vacationReservationManager;
    
    @Override
    public IOfferPack requestProposal(IOfferRequest offerRequest) {
        return _vacationOfferBrowser.getProposals(offerRequest);
    }
    
    @Override
    public IConfirmationLetter reserveOffer(IOffer offer) {
        return _vacationReservationManager.reserveOffer(offer);
    }
}
