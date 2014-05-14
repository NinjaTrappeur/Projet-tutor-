/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package message;

/**
 *
 * @author josuah
 */
public class AgentID
{
    private String name;
    private String localName;
    private String [] allAdresses;

    public AgentID()
    {
        name = "";
        localName = "";
        allAdresses = null;
    }
    
    public AgentID(String iname, String ilocalName, String [] iallAdresses)
    {
        name = iname;
        localName = ilocalName;
        allAdresses = iallAdresses;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the localName
     */
    public String getLocalName() {
        return localName;
    }

    /**
     * @param localName the localName to set
     */
    public void setLocalName(String localName) {
        this.localName = localName;
    }

    /**
     * @return the allAdresses
     */
    public String[] getAllSdresses() {
        return allAdresses;
    }

    /**
     * @param allSdresses the allAdresses to set
     */
    public void setAllSdresses(String[] allSdresses) {
        this.allAdresses = allSdresses;
    }
}
