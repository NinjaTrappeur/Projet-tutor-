/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import jade.wrapper.StaleProxyException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josuah
 */
public class TestLauncher
{
    public static void main(String [] args)
    {
        String [] containerArgs = {"-container", "-host", "10.12.22.15", "-port", "1099"};

        jade.wrapper.AgentContainer container = Launcher.boot(containerArgs);

        try
        {
            agent.CasomClient client = new agent.CasomClient();
            container.acceptNewAgent("client", client);
        }
        catch (StaleProxyException ex)
        {
            Logger.getLogger(TestLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
