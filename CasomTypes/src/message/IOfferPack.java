

package message;

import java.util.ArrayList;
import javax.jws.WebService;

@WebService
public interface IOfferPack extends IMessage
{
    public void addOffer(IOffer offer);
    public IOffer getBestOffer();
    public ArrayList<IOffer> getAllOffers();

    public void setBestOffer(IOffer offer);
    public void setAllOffers(ArrayList<IOffer> offers);
}
