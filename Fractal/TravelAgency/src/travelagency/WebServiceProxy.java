
package travelagency;
import message.IConfirmationLetter;
import message.IOffer;
import message.IOfferPack;
import message.IOfferRequest;
import org.objectweb.fractal.fraclet.annotations.Component;
import org.objectweb.fractal.fraclet.annotations.Interface;
import org.objectweb.fractal.fraclet.annotations.Requires;

@Component(provides=@Interface(name="r", signature = java.lang.Runnable.class))
public class WebServiceProxy implements ITravelAgency, Runnable {
    public WebServiceProxy() {
        
    }
    
    @Override
    public void run() {
        System.out.println();
    }
    
    @Requires(name = "vacationOfferBrowser")
    private _vacationOfferBrowser;
    
    @Requires(name = "vacationReservationManager")
    private _vacationReservationManager;
    
    @Override
    public IOfferPack requestProposal(IOfferRequest offerRequest) {
        return _vacationOfferBrowser.getProposals(offerRequest);
    }
    
    @Override
    public IConfirmationLetter reserveOffer(IOffer offer) {
        return _vacationReservationManager.reserveOffer(offer);
    }
}
