
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
        Boolean localClient=true, remoteMode=false;
        
        parseCmdLine(args, localClient, remoteMode);
        
        String [] containerArgs = {"-gui"};

        jade.wrapper.AgentContainer mainContainer = Launcher.boot(containerArgs);

        try
        {
            agent.CasomClient client = new agent.CasomClient();
            agent.ClientView view = new agent.ClientView();
            agent.TravelAgency agency = new agent.TravelAgency(remoteMode);
            
            // Add agents
            if(localClient)
            {
                controller = mainContainer.acceptNewAgent("client", client);
                controller.start();
            }
            
            controller = mainContainer.acceptNewAgent("agency", agency);
            controller.start();
            
            controller = mainContainer.acceptNewAgent("view", view);
            controller.start();
        }
        catch (StaleProxyException ex)
        {
            Logger.getLogger(TestLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void parseCmdLine(String [] args, Boolean localClient, Boolean remoteAgency)
    {
        int i = 0;
        while(i < args.length)
        {
            switch(args[i])
            {
                case "-client":
                    localClient = true;
                    break;
                case "-noclient":
                    localClient = false;
                    break;
                case "-remote":
                    remoteAgency = true;
                    break;
            }
        }
    }
}
