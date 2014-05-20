
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
            agent.CasomClient client = new agent.CasomClient();
            controller = mainContainer.acceptNewAgent("client", client);
            controller.start();
            
            agent.ClientView view = new agent.ClientView();
            controller = mainContainer.acceptNewAgent("view", view);
            controller.start();
            
            if(switches.get("local-agency"))
            {
                agent.TravelAgency agency = new agent.TravelAgency(switches.get("remote-mode"));
                controller = mainContainer.acceptNewAgent("agency", agency);
                controller.start();
            }
        }
        catch (StaleProxyException ex)
        {
            Logger.getLogger(TestLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static HashMap<String, Boolean> parseCmdLine(String [] args)
    {
        HashMap<String, Boolean> switches = new HashMap();
        switches.put("local-agency", true);
        switches.put("remote-mode", false);
        
        System.out.println(switches);
        
        int i = 0;
        while(i < args.length)
        {
            if(args[i].equalsIgnoreCase("--no-agency"))
                switches.put("local-agency", false);
            else if(args[i].equalsIgnoreCase("--remote-mode"))
                switches.put("remote-mode", true);
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
        System.out.println("\t--no-agency: do not start the agent 'TravelAgency' agent.");
        System.out.println("\t--remote-mode: start the 'TravelAgency' agent in remote mode, which means it will get its services from a web service.");
        System.out.println("\t-h: show usage.");
    }
}
