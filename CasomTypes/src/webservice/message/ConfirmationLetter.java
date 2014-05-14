package webservice.message;

public class ConfirmationLetter extends Offer
{
    public ConfirmationLetter(float cprice, String cname, String cagency)
    {
        super(cprice, cname, cagency);
        type = "CONFIM_LETTER";
    }
    public ConfirmationLetter() {
        
    }
}
