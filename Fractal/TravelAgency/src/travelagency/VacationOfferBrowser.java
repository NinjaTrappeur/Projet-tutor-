package travelagency;

import message.IOfferPack;
import message.IOfferRequest;
import message.Offer;
import message.OfferPack;
import org.objectweb.fractal.fraclet.annotations.Component;
import org.objectweb.fractal.fraclet.annotations.Interface;
import travelagency.interfaces.IVacationOfferBrowser;
import jade.core.AID;

@Component(provides=@Interface(name="getProposals", signature = travelagency.interfaces.IVacationOfferBrowser.class))
public class VacationOfferBrowser implements IVacationOfferBrowser{
    
    @Override
    public IOfferPack getProposals(IOfferRequest offerRequest) {
        return new OfferPack(new Offer(100, "Bruce Willis", new AID()));
    }
}
