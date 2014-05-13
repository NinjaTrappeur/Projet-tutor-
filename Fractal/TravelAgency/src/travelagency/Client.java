package travelagency;

import travelagency.interfaces.IVacationOfferBrowser;
import travelagency.interfaces.IVacationReservationManager;
import org.objectweb.fractal.fraclet.annotations.Component;
import org.objectweb.fractal.fraclet.annotations.Interface;
import org.objectweb.fractal.fraclet.annotations.Requires;



@Component(provides=@Interface(name="r", signature = java.lang.Runnable.class))
public class Client implements Runnable{
    
    @Requires(name="getOffer")
    private IVacationOfferBrowser getOfferInterface;
    
    @Requires(name="makeReservation")
    private IVacationReservationManager makeReservationInterface;
    
    @Override
    public void run() {
        System.out.println("Architecture OK.");
    }
}
