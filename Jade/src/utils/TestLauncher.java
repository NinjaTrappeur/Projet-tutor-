package utils;

import jade.wrapper.StaleProxyException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Test the utils.Launcher class.
 * This class shows some argument for launching an agent container, adding agents to this container
 * and starting the agents (using the agents controllers).
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
            
            container.acceptNewAgent("client", client).start();
        }
        catch (StaleProxyException ex)
        {
            Logger.getLogger(TestLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
