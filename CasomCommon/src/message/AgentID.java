package message;

import java.io.Serializable;

/**
 *
 * Represents an AID as a JavaBean.
 */
public class AgentID implements Serializable
{
    private String name; /*!< The name of the agent (prefix:address).*/
    private String localName; /*!< The local name of the agent. */
    private String address; /*!< The address of the agent (AID.getAllAddresses). */
    
    /**
     * Constructor
     * @param iname the name of the agent.
     * @param ilocalName the localname of the agent.
     * @param iallAdresses  the address of the agent.
     */
    public AgentID(String iname, String ilocalName, String iallAdresses)
    {
        name = iname;
        localName = ilocalName;
        address = iallAdresses;
    }

    public AgentID()
    {
        this("", "", "");
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param allSdresses the address to set
     */
    public void setAddress(String allSdresses) {
        this.address = allSdresses;
    }
    
    @Override
    public String toString () {
        return name + localName + address;
    }
}
