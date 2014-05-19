
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
        String [] containerArgs = {"-gui", "-agents", "client:agent.CasomClient;agency:agent.TravelAgency;view:agent.ClientView"};

        Launcher.boot(containerArgs);
    }
}
