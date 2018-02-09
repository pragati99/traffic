package edu.utdallas.mavs.traffic.simulation.sim.env;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.utdallas.mavs.divas.core.msg.RuntimeAgentCommandMsg;
import edu.utdallas.mavs.divas.core.sim.agent.Agent;
import edu.utdallas.mavs.divas.core.sim.common.event.EnvEvent;
import edu.utdallas.mavs.divas.core.sim.common.state.AgentControlType;
import edu.utdallas.mavs.divas.core.sim.common.state.AgentState;
import edu.utdallas.mavs.divas.core.sim.common.state.CellState;
import edu.utdallas.mavs.divas.core.sim.common.state.EnvObjectState;
import edu.utdallas.mavs.divas.core.sim.common.state.VirtualState;
import edu.utdallas.mavs.divas.core.sim.common.stimulus.AgentStimulus;
import edu.utdallas.mavs.divas.core.sim.env.AgentStateModel;
import edu.utdallas.mavs.divas.core.sim.env.SelfOrganizingCellController;
import edu.utdallas.mavs.divas.mts.CommunicationModule;
import edu.utdallas.mavs.traffic.simulation.sim.agent.VehicleAgent;
import edu.utdallas.mavs.traffic.simulation.sim.common.state.VehicleAgentState;
import edu.utdallas.mavs.traffic.simulation.sim.common.stimulus.ChangeHeadingStimulus;
import edu.utdallas.mavs.traffic.simulation.sim.common.stimulus.MoveStimulus;

public class TrafficCellController extends SelfOrganizingCellController<TrafficEnvironment>
{
    private static final long   serialVersionUID = 1L;

    private final static Logger logger           = LoggerFactory.getLogger(TrafficCellController.class);

    public TrafficCellController(CellState rootCellState, CommunicationModule comModule, TrafficEnvironment environment)
    {
        super(rootCellState, comModule, environment);
    }

    @Override
    protected List<AgentStateModel> combineAgentStimuli()
    {
        Map<Integer, AgentStateModel> agentStates = new HashMap<Integer, AgentStateModel>();

        for(AgentStimulus action : agentStimuli)
        {
            AgentState state = cellState.getAgentState(action.getId());

            if(state != null)
            {
                if(action instanceof MoveStimulus)
                {
                    AgentState myState = cellState.getAgentState(action.getId());
                    AgentStateModel agentStateModel = new AgentStateModel(myState);
                    agentStateModel.move(((MoveStimulus) action).getPosition());
                    agentStates.put(myState.getID(), agentStateModel);
                }
                else if(action instanceof ChangeHeadingStimulus)
                {
                    ChangeHeadingStimulus a = (ChangeHeadingStimulus) action;
                    state.setHeading(a.getHeading());
                }
            }
        }
        // clear the list of agent stimuli
        agentStimuli.clear();

        // resolveConflicts(agentStates);

        return new ArrayList<AgentStateModel>(agentStates.values());
    }

    @Override
    protected Agent createAgent(AgentState initialState)
    {
        Agent agent = null;
        if(initialState.getAgentType().equals("Vehicle"))
        {
            agent = new VehicleAgent((VehicleAgentState) initialState);
            logger.debug("Created " + initialState.getAgentType());
        }
        else
        {
            logger.warn("Agent of type " + initialState.getAgentType() + " does not exist");
        }
        return agent;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected TrafficCellController createChild(CellState cellState)
    {
        return new TrafficCellController(cellState, comModule, environment);
    }

    @Override
    protected void processDestructiveEvent(EnvEvent arg0)
    {
        // TODO Auto-generated method stub
    }

    @Override
    protected void applyUserCommand(RuntimeAgentCommandMsg cmd)
    {
        VehicleAgentState agent = (VehicleAgentState) cellState.getAgentState(cmd.getAgentID());
        if(agent != null)
        {
            if(cmd.getCommand() == RuntimeAgentCommandMsg.RuntimeAgentCommand.SET_CONTROL_TYPE)
                agent.setControlType(AgentControlType.valueOf(cmd.getDataAsString()));
        }
    }

    

    private boolean isRoad(EnvObjectState envObj)
    {
        return envObj.getType().equals("Streets") || envObj.getType().equals("road") || envObj.getType().equals("street") 
		|| envObj.getType().equals("road_horizntal")
                || envObj.getType().equals("Cross Walk");
    }
}
