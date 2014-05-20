
import jade.wrapper.StaleProxyException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Launcher;
import utils.TestLauncher;

/**
 * 
 * <h1>Entry point</h1>
 * Agent-side applicaiton entry point.
 * <h2>Start-up actions</h2>
 * <ul>
 * <li>Launches the main container, using the utils.Launcher class.</li>
 * <li>Creates the 3 agents of the application: Client, TravelAgency and View.</li>
 * <li>Adds the agents to the container.</li>
 * <li>Starts the agents using the controllers returned by the container when adding agents to it.</li>
 * </ul>
 * 
 * <h2>Command line options</h2>
 * <ul>
 * <li>-noclient: do not start the agent Client.</li>
 * <li>-remoteMode: start the TravelAgency agent in remote mode, which means it will get its services from a web service.</li>
 * <li>-h: show usage.</li>
 * </ul>
 */
public class Main
{
    public static void main(String [] args)
    {
        jade.wrapper.AgentController controller;
        
        HashMap<String, Boolean> switches = Main.parseCmdLine(args);
        
        // Create the main container
        String [] containerArgs = {"-gui"};
        jade.wrapper.AgentContainer mainContainer = Launcher.boot(containerArgs);

        // Add agents
        try
        {
            if(switches.get("localClient"))
            {
                agent.CasomClient client = new agent.CasomClient();
                controller = mainContainer.acceptNewAgent("client", client);
                controller.start();
            }
            
            agent.TravelAgency agency = new agent.TravelAgency(switches.get("remoteMode"));
            controller = mainContainer.acceptNewAgent("agency", agency);
            controller.start();
            
            agent.ClientView view = new agent.ClientView();
            controller = mainContainer.acceptNewAgent("view", view);
            controller.start();
        }
        catch (StaleProxyException ex)
        {
            Logger.getLogger(TestLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static HashMap<String, Boolean> parseCmdLine(String [] args)
    {
        HashMap<String, Boolean> switches = new HashMap();
        switches.put("localClient", true);
        switches.put("remoteMode", false);
        
        System.out.println(switches);
        
        int i = 0;
        while(i < args.length)
        {
            if(args[i].equalsIgnoreCase("-noclient"))
                switches.put("localClient", false);
            else if(args[i].equalsIgnoreCase("-remote"))
                switches.put("remoteMode", true);
            else
                usage();
            
            ++i;
        }
        
        System.out.println(switches);
        
        return switches;
    }
    
    public static void usage()
    {
        System.out.println("{executable} [-noclient] [-remoteMode] [-h]");
        System.out.println("\t-noclient: do not start the agent 'Client'.");
        System.out.println("\t-remoteMode: start the 'TravelAgency' agent in remote mode, which means it will get its services from a web service.");
        System.out.println("\t-h: show usage.");
    }
}
