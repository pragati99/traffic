package edu.utdallas.mavs.traffic.simulation.sim.common.state;

import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.util.List;

import com.jme3.math.Vector3f;

import edu.utdallas.mavs.divas.core.sim.agent.interaction.perception.sensors.vision.VisionAlgorithm;
import edu.utdallas.mavs.divas.core.sim.agent.task.Task;
import edu.utdallas.mavs.divas.core.sim.common.percept.VisionPerceptor;
import edu.utdallas.mavs.divas.core.sim.common.state.AgentState;

public class VehicleAgentState extends AgentState implements VisionPerceptor
{
    private static final long  serialVersionUID = 1L;


    /**
     * The visible distance the agent can perceive using its vision sense.
     */
    protected float            visibleDistance;

    /**
     * This agent's current tasks
     */
    protected List<Task>       currentTasks;
    
    /**
     * The current posture of the agent.
     */
    protected Posture         posture          = Posture.zero_position;

    @Override
    public Shape calculateVisibleRegion()
    {
        float visibleDistance = getVisibleDistance();
        Arc2D arc = new Arc2D.Float((getPosition().getX() - visibleDistance), (getPosition().getZ() - visibleDistance), (visibleDistance * 2), (visibleDistance * 2), 0, 0, Arc2D.PIE);

        Vector3f towards = getPosition().add(getHeading());

        arc.setAngleStart(new Point2D.Float(towards.getX(), towards.getZ()));
        // subtract by half the fov, this puts the heading at the center of the arc
        arc.setAngleStart(arc.getAngleStart() - getFOV() / 2);
        arc.setAngleExtent(getFOV());

        return arc;
    }

    @Override
    public float getFOV()
    {
        return 0;
    }

    @Override
    public float getVisibleDistance()
    {
        return visibleDistance;
    }

    @Override
    public void setFOV(float fov)
    {
        
    }

    @Override
    public void setVisibleDistance(float visibleDistance)
    {
        this.visibleDistance = visibleDistance;
    }

    @Override
    public void setVisionAlgorithm(VisionAlgorithm arg0)
    {
        // empty
    }

    @Override
    public float getVisionHeight()
    {
        return 0;
    }

    public List<Task> getCurrentTasks()
    {
        return currentTasks;
    }

    public void setCurrentTasks(List<Task> currentTasks)
    {
        this.currentTasks = currentTasks;
    }
    
    /**
     * Gets the posture of the agent.
     * 
     * @return The agent posture.
     */
    public Posture getPosture()
    {
        return posture;
    }

    /**
     * Changes the posture of the agent.
     * 
     * @param posture
     *        The agent posture.
     */
    public void setPosture(Posture posture)
    {
        this.posture = posture;
    }
}
