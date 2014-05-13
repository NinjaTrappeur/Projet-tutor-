

package message;

import javax.jws.WebService;

@WebService
public interface IOfferPack extends IMessage
{
    public IOffer lowestPrice();
}
