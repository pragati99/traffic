package edu.utdallas.mavs.traffic.visualization.vis3D.spectator;

import edu.utdallas.mavs.divas.core.sim.common.event.EnvEvent;
import edu.utdallas.mavs.divas.core.sim.common.state.AgentState;
import edu.utdallas.mavs.divas.core.sim.common.state.EnvObjectState;
import edu.utdallas.mavs.divas.visualization.vis3D.spectator.PlayGround;
import edu.utdallas.mavs.divas.visualization.vis3D.vo.AgentVO;
import edu.utdallas.mavs.divas.visualization.vis3D.vo.EnvObjectVO;
import edu.utdallas.mavs.divas.visualization.vis3D.vo.EventVO;
import edu.utdallas.mavs.traffic.simulation.sim.common.state.VehicleAgentState;
import edu.utdallas.mavs.traffic.visualization.vis3D.vo.TrafficEnvObjectVO;
import edu.utdallas.mavs.traffic.visualization.vis3D.vo.VehicleAgentVO;

public class TrafficPlayground extends PlayGround
{

    @Override
    protected AgentVO<?> createAgentVO(AgentState state, long cycle)
    {
        AgentVO<?> vo = null;
        String agentType = state.getAgentType();

        if(agentType.equals("Vehicle"))
        {
            vo = new VehicleAgentVO((VehicleAgentState) state, cycle);
        }

        return vo;
    }

    @Override
    protected EnvObjectVO createEnvObjectVO(EnvObjectState state, long cycle)
    {
        return new TrafficEnvObjectVO(state, cycle);
    }

    @Override
    protected EventVO createEventVO(EnvEvent event, long cycle)
    {
        return null;
    }
}
