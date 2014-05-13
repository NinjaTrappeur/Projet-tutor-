package webservice.message;


public class ConfirmationLetter {
    private String type;
    
    public ConfirmationLetter() {
        type = "CONFIM_LETTER";
    }
    
    public String getType(){
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
