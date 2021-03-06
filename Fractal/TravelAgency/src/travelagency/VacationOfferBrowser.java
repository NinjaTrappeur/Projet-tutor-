package travelagency;

import java.util.ArrayList;
import message.IOfferPack;
import message.IOfferRequest;
import message.Offer;
import message.OfferPack;
import org.objectweb.fractal.fraclet.annotations.Component;
import org.objectweb.fractal.fraclet.annotations.Interface;
import travelagency.interfaces.IVacationOfferBrowser;

@Component(provides=@Interface(name="getProposals", signature = travelagency.interfaces.IVacationOfferBrowser.class))
public class VacationOfferBrowser implements IVacationOfferBrowser{
    
    @Override
    public IOfferPack getProposals(IOfferRequest offerRequest) {
        ArrayList<String> nameList = new ArrayList();
        nameList.add("Bruce Willis");
        nameList.add("Philip Zimmermann");
        nameList.add("Marcel Orchestre");
        nameList.add("Valentin Lacambre");
        nameList.add("Laurent Chemla");
        nameList.add("Claire Pichet");
        nameList.add("Josual Aron");
        nameList.add("Eric Tabarly");
        nameList.add("Alain Colas");
        nameList.add("Michel Desjoyeaux");
        nameList.add("François Gabart");
        nameList.add("Christophe Fourcault de Pavant");
        float minPrice = offerRequest.getLowestPrice();
        float maxPrice = offerRequest.getHighestPrice();
        int randomIndex = (int)(Math.random() * nameList.size());
        float randomPrice = (int)(Math.random() * (maxPrice-minPrice)) + minPrice;
        return new OfferPack(new Offer(randomPrice, nameList.get(randomIndex), offerRequest.getAgentID()));
    }
}
