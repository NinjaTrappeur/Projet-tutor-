package message;

/**
 *
 * A message to book a vacation offer.
 */
public interface IReservationRequest extends IMessage
{
    /**
     * gets the offer that is to be booked.
     * @return the offer to be booked.
     */
    public IOffer getOffer();
}
