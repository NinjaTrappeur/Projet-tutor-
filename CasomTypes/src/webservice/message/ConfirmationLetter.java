package webservice.message;

import message.AgentID;

public class ConfirmationLetter extends Offer
{
    public ConfirmationLetter(float cprice, String cname, AgentID cagency)
    {
        super(cprice, cname, cagency);
        type = "CONFIM_LETTER";
    }
    public ConfirmationLetter() {
        
    }
}
