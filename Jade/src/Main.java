
import jade.wrapper.StaleProxyException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.Launcher;
import utils.TestLauncher;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author josuah
 */
public class Main
{
    public static void main(String [] args)
    {
        jade.wrapper.AgentController controller;
        
        System.out.println("Yahoo");
        HashMap<String, Boolean> switches = Main.parseCmdLine(args);
        
        String [] containerArgs = {"-gui"};

        jade.wrapper.AgentContainer mainContainer = Launcher.boot(containerArgs);

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
            
            ++i;
        }
        
        System.out.println(switches);
        
        return switches;
    }
}
