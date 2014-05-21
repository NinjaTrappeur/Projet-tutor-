package shared.utils;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.ProfileException;

import jade.util.leap.Properties;

/**
 *
 * An agent container launcher.
 * The Jade.Boot class does not allow to get the reference of the newly created agent container.
 * This class allows to get a controller on an agent container created at runtime.
 * 
 * See utils.TestLauncher for an example.
 */
public class Launcher
{

    public static final String DEFAULT_FILENAME = "leap.properties";

    /**
     * Boot an agent container.
     * Does the same job as jade.Boot.main, but returns a reference on a wrapper for the agent controller.
     * @param args agent container boot arguments. -h for details.
     * @return wrapper on the created agent container.
     */
    public static jade.wrapper.AgentContainer boot(String args[])
    {
        jade.wrapper.AgentContainer container = null;
        
        try {
            // Create the Profile 
            ProfileImpl p = null;
            if (args.length > 0) 
            {
                if (args[0].startsWith("-"))
                {
                    // Settings specified as command line arguments
                    Properties pp = jade.Boot.parseCmdLineArgs(args);
                    if (pp != null)
                    {
                        p = new ProfileImpl(pp);
                    } 
                    else
                    {
                        // One of the "exit-immediately" options was specified!
                        return null;
                    }
                }
                else
                {
                    // Settings specified in a property file
                    p = new ProfileImpl(args[0]);
                }
            }
            else
            {
                // Settings specified in the default property file
                p = new ProfileImpl(DEFAULT_FILENAME);
            }

            // Start a new JADE runtime system
            Runtime.instance().setCloseVM(true);
            
            // Check whether this is the Main Container or a peripheral container
            if (p.getBooleanProperty(Profile.MAIN, true)) 
            {
                 container = Runtime.instance().createMainContainer(p);
            }
            else
            {
                container =  Runtime.instance().createAgentContainer(p);
            }
        } 
        catch (ProfileException pe)
        {
            System.err.println("Error creating the Profile [" + pe.getMessage() + "]");
            pe.printStackTrace();
            jade.Boot.printUsage();
            System.exit(-1);
        }
        catch (IllegalArgumentException iae)
        {
            System.err.println("Command line arguments format error. " + iae.getMessage());
            iae.printStackTrace();
            jade.Boot.printUsage();
            System.exit(-1);
        }
        
        return container;
    }
}
