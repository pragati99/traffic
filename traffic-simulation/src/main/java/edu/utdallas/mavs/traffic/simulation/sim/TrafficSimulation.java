package edu.utdallas.mavs.traffic.simulation.sim;

import java.io.Serializable;

import edu.utdallas.mavs.divas.core.msg.TickMsg;
import edu.utdallas.mavs.divas.core.sim.Simulation;
import edu.utdallas.mavs.divas.mts.MTSClient;
import edu.utdallas.mavs.traffic.simulation.sim.env.TrafficEnvironment;

/**
 * This class describes a simulation for evacuation scenarios.
 */
public class TrafficSimulation extends Simulation<TrafficEnvironment> implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new instance of the simulation for traffic scenarios.
     * 
     * @param client
     *        the MTS client
     */
    public TrafficSimulation(MTSClient client)
    {
        super(client);
    }

    @Override
    protected void createEnvironment(MTSClient client)
    {
        environment = new TrafficEnvironment(client);
    }
    
    @Override
    public void tick(TickMsg tick)
    {
        super.tick(tick);
    }
}