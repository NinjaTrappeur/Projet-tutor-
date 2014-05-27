package message;

import java.io.Serializable;

/**
 *
 * Interface des messages entre agents.
 */
public interface IMessage extends Serializable
{
    /**
     * Different type of messages exchanged between agents.
     * This enum allows to easily make <i>switch</i> on messages.
     */
    public enum Type {OFFER_REQUEST, OFFER_PACK, RESERVATION_REQUEST, CONFIRM_LETTER, OFFER};
    
    /**
     * Gets the type of a message.
     * @return Type the type of the message.
     */
    public Type getType();
}
