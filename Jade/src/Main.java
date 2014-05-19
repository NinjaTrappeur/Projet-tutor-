
import jade.wrapper.StaleProxyException;
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
        String [] containerArgs = {"-gui"};

        jade.wrapper.AgentContainer mainContainer = Launcher.boot(containerArgs);

        try
        {
            agent.CasomClient client = new agent.CasomClient();
            agent.ClientView view = new agent.ClientView();
            agent.TravelAgency agency = new agent.TravelAgency();
            
            controller = mainContainer.acceptNewAgent("client", client);
            controller.start();
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
}
