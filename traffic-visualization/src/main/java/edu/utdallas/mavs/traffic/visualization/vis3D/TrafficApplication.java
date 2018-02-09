package edu.utdallas.mavs.traffic.visualization.vis3D;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;

import edu.utdallas.mavs.divas.visualization.vis3D.BaseApplication;
import edu.utdallas.mavs.divas.visualization.vis3D.appstate.SimulatingAppState;
import edu.utdallas.mavs.divas.visualization.vis3D.engine.CursorManager;
import edu.utdallas.mavs.divas.visualization.vis3D.vo.AgentVO;
import edu.utdallas.mavs.traffic.simulation.sim.common.state.VehicleAgentState;
import edu.utdallas.mavs.traffic.visualization.vis3D.appstate.TrafficEnvironmentAppState;
import edu.utdallas.mavs.traffic.visualization.vis3D.appstate.TrafficSimulatingAppState;
import edu.utdallas.mavs.traffic.visualization.vis3D.engine.TrafficCursorManager;

/**
 * This class describes the visualizer application for the evacuation simulation
 */
public class TrafficApplication extends BaseApplication<TrafficSimulatingAppState, TrafficEnvironmentAppState>
{
    @Override
    protected TrafficSimulatingAppState createSimulationAppState()
    {
        return new TrafficSimulatingAppState();
    }

    @Override
    protected TrafficEnvironmentAppState createEnvironmentAppState()
    {
        return new TrafficEnvironmentAppState();
    }

    @Override
    protected CursorManager createCursorManager(AssetManager assetManager)
    {
        return new TrafficCursorManager(assetManager);
    }

    @Override
    protected void closeNiftyWindows()
    {
        super.closeNiftyWindows();

        AgentVO<?> selectedAgent = SimulatingAppState.getContextSelectionPicker().getSelectedAgent();

        if(selectedAgent != null && selectedAgent.isCamModeOn())
        {
            selectedAgent.setCamMode(false);
            attachFreeCamera();
        }
    }

    @Override
    protected void updateCamera()
    {
        AgentVO<?> selectedAgent = SimulatingAppState.getContextSelectionPicker().getSelectedAgent();

        if(selectedAgent != null && selectedAgent.isCamModeOn())
        {
            getStateManager().getState(SimulatingAppState.class);
            if(selectedAgent.getState() instanceof VehicleAgentState)
            {
                VehicleAgentState agent = (VehicleAgentState) selectedAgent.getState();
                cam.setLocation(selectedAgent.getLocalTranslation().add(0, agent.getVisionHeight(), 0).add(agent.getHeading().normalize().mult(.1f)));
                cam.lookAtDirection(agent.getHeading(), Vector3f.UNIT_Y);
            }
        }
    }
}