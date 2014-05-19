/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.ProfileException;

import jade.util.leap.Properties;

/**
 *
 * @author josuah
 * 
 * The launching code would look like the following:
 * 
 * See TestLauncher for an example.
 */
public class Launcher
{

    public static final String DEFAULT_FILENAME = "leap.properties";

    /**
     * Fires up the <b><em>JADE</em></b> system. This method initializes the
     * Profile Manager and then starts the bootstrap process for the
     * <B><em>JADE</em></b>
     * agent platform.
     * @param args
     * @return 
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
			//#PJAVA_EXCLUDE_BEGIN
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
            //System.err.println("Usage: java jade.Launcher <filename>");
            System.exit(-1);
        }
        catch (IllegalArgumentException iae)
        {
            System.err.println("Command line arguments format error. " + iae.getMessage());
            iae.printStackTrace();
            jade.Boot.printUsage();
            //System.err.println("Usage: java jade.Launcher <filename>");
            System.exit(-1);
        }
        
        return container;
    }
}
