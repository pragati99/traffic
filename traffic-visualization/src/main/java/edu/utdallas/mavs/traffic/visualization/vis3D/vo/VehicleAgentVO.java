package edu.utdallas.mavs.traffic.visualization.vis3D.vo;

import com.jme3.animation.AnimControl;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import edu.utdallas.mavs.divas.visualization.vis3D.Visualizer3DApplication;
import edu.utdallas.mavs.divas.visualization.vis3D.utils.VisToolbox;
import edu.utdallas.mavs.divas.visualization.vis3D.vo.AgentVO;
import edu.utdallas.mavs.traffic.simulation.sim.common.state.Posture;
import edu.utdallas.mavs.traffic.simulation.sim.common.state.VehicleAgentState;

/**
 * This class describes a visualized agent
 */
public class VehicleAgentVO extends AgentVO<VehicleAgentState>
{
    /**
     * A ratio constant to correct the mismatch between the 3D models and the simulation entity
     */
    private static final float  MODEL_RATIO        = 0.12f;

    /**
     * The directory where the vehicles 3D models are stored in the DIVAs framework
     */
    private static final String VEHICLE_MODELS_DIR = "traffic/vehicle/";

    ColorRGBA                   agentMarkColor;

    private Posture             prevPosture        = null;

    static Quaternion           rot90              = new Quaternion().fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_Y);

    /**
     * Creates a new agent VO
     * 
     * @param state
     *        the agent state to be associated with this VO
     * @param cycle
     *        the simulation cycle number associated with the agent state
     */
    public VehicleAgentVO(VehicleAgentState state, long cycle)
    {
        super(state, cycle);
        AmbientLight agentLight = VisToolbox.createAmbientLight(ColorRGBA.White);
        addLight(agentLight);
    }

    @Override
    protected float getModelRatio()
    {
        return MODEL_RATIO;
    }

    @Override
    protected void setupAgentModel()
    {
        String agentPath = new String();

        agentPath = VEHICLE_MODELS_DIR + state.getModelName() + ".mesh.xml";

        agentModel = getAssetManager().loadModel(agentPath);
    }

    @Override
    protected void updateState()
    {
        rotation = new Quaternion();
        rotation.lookAt(state.getHeading(), Vector3f.UNIT_Y);
        updateState(state.getPosition(), rotation, state.getScale());

        // updates VO's visible properties
        updateVisibleProperties();
    }

    /**
     * Updates this VO's visible properties (e.g., posture, vision cone)
     */
    @Override
    protected void updateVisibleProperties()
    {
        if(prevFov != state.getFOV() || prevVisibleDistance != state.getVisibleDistance())
        {
            if(visionConeEnabled)
            {
                detachVisionCone();
                attachVisionCone();
            }
        }

        prevFov = state.getFOV();
        prevVisibleDistance = state.getVisibleDistance();
        if(prevPosture == null)
        {
            prevPosture = state.getPosture();
        }

        
        if(!prevPosture.equals(state.getPosture()))
        {
            if(Visualizer3DApplication.getVisConfig().animatedModels)
            {
                channel.setAnim(state.getPosture().toString());
                prevPosture = state.getPosture();
            }
        }
    }

    @Override
    protected void setupAnimation()
    {
        if(Visualizer3DApplication.getVisConfig().animatedModels)
        {
            control = agentModel.getControl(AnimControl.class);
            channel = control.createChannel();
            channel.setAnim(Posture.forward_fast.toString());
        }
    }

}
