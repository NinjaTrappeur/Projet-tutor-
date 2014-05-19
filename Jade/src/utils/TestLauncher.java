/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

import jade.core.AID;
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
        String [] containerArgs = {"-cp", "lib/jade.jar:Jade/build", "-container", "-host", "10.12.22.15", "-port", "1099"};

        jade.wrapper.AgentContainer container = Launcher.boot(containerArgs);

        String [] agentArgs = new String [0];
        jade.wrapper.AgentController agent;
        try
        {
            agent = container.createNewAgent("client", "agent.CasomClient", args);
            //agent.start(); // not sure about this one
        }
        catch (StaleProxyException ex)
        {
            Logger.getLogger(TestLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
