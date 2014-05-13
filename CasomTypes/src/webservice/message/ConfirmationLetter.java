package webservice.message;


public class ConfirmationLetter {
    private String type;
    
    public ConfirmationLetter() {
        type = "CONFIM_LETTER";
    }
    
    public String getType(){
        return type;
    }
}
