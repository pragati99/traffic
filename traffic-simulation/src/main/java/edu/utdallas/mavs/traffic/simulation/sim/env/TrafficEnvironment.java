package edu.utdallas.mavs.traffic.simulation.sim.env;

import edu.utdallas.mavs.divas.core.sim.common.state.CellState;
import edu.utdallas.mavs.divas.core.sim.env.SelfOrganizingEnvironment;
import edu.utdallas.mavs.divas.core.spec.agent.AgentLoader;
import edu.utdallas.mavs.divas.mts.CommunicationModule;
import edu.utdallas.mavs.divas.mts.MTSClient;
import edu.utdallas.mavs.traffic.simulation.sim.spec.TrafficAgentLoader;

public class TrafficEnvironment extends SelfOrganizingEnvironment<TrafficCellController>
{
    private static final long serialVersionUID = 1L;

    public TrafficEnvironment(MTSClient client)
    {
        super(client);
    }

    @Override
    protected TrafficCellController createCellController(CellState cellState, CommunicationModule commModule)
    {
        return new TrafficCellController(cellState, commModule, this);
    }

    @Override
    protected AgentLoader createAgentLoader()
    {
        return new TrafficAgentLoader();
    }
}
