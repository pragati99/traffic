package edu.utdallas.mavs.traffic.simulation.sim.agent.tasks;

import java.util.ArrayList;
import java.util.List;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;

import edu.utdallas.mavs.divas.core.sim.agent.task.AbstractTask;
import edu.utdallas.mavs.divas.core.sim.common.stimulus.Stimuli;
import edu.utdallas.mavs.traffic.simulation.sim.common.state.Posture;
import edu.utdallas.mavs.traffic.simulation.sim.common.stimulus.ChangeHeadingStimulus;
import edu.utdallas.mavs.traffic.simulation.sim.common.stimulus.MoveStimulus;

public abstract class TurnTask extends AbstractTask
{
    private static final long serialVersionUID      = 1L;

    public static final int   INTERPOLATION_STEPS   = 5;

    /**
     * The origin position of the agent after this task is executed
     */
    protected Vector3f        startPosition;

    /**
     * The destination position of the agent after this task is executed
     */
    protected Vector3f        endPosition;

    /**
     * Path interpolated positions for turn task
     */
    protected List<Vector3f>  interpolatedPositions = new ArrayList<>();

    /**
     * Path interpolated headings for turn task
     */
    protected List<Vector3f>  interpolatedHeadings  = new ArrayList<>();
    
    /**
     * The posture associated with the TurnTask
     */
    protected Posture posture;

    protected int             currentStep           = 1;

    public TurnTask(String name, String type, long executionCycle, boolean enabled)
    {
        super(name, type, executionCycle, enabled);
    }

    @Override
    public Stimuli execute(int agentId)
    {
        Stimuli stimuli = new Stimuli();
        stimuli.add(new MoveStimulus(agentId, interpolatedPositions.get(currentStep)));
        stimuli.add(new ChangeHeadingStimulus(agentId, interpolatedHeadings.get(currentStep)));
        currentStep++;
        return stimuli;
    }

    protected void interpolatePath(Vector3f startPosition, Vector3f endPosition, float turnAngle, boolean turnLeft, List<Vector3f> interpolatedPositions, List<Vector3f> interpolatedHeadings)
    {
        // calculates the pivot of rotation
        Vector3f pivot = calculatePivotPoint(startPosition, endPosition, turnLeft);
        // interpolates intermediate positions (circular path)
        for(float i = 0; i <= FastMath.HALF_PI; i = i + FastMath.HALF_PI / INTERPOLATION_STEPS)
        {
            Vector3f normal = startPosition.subtract(pivot);
            Vector3f point = rotatedVector(normal, i, turnLeft);
            Vector3f position = pivot.add(point);
            interpolatedPositions.add(position);
            interpolatedHeadings.add(getOrtonormalVector(pivot.subtract(position), !turnLeft));
        }
        
    }

    private Vector3f calculatePivotPoint(Vector3f startPosition, Vector3f endPosition, boolean turnLeft)
    {
        Vector3f midPoint = endPosition.add(startPosition).divide(2f);
        Vector3f normal = midPoint.add(getOrtonormalVector(endPosition.subtract(startPosition), turnLeft).mult(endPosition.distance(startPosition) / 2f));
        return normal;
    }

    private Vector3f getOrtonormalVector(Vector3f v, boolean turnLeft)
    {
        return rotatedVector(v, FastMath.HALF_PI, turnLeft).normalizeLocal();
    }

    private Vector3f rotatedVector(Vector3f v, float angle, boolean turnLeft)
    {
        float rot = (turnLeft ? 1 : -1) * angle;
        float tx = v.x * FastMath.cos(rot) - v.z * FastMath.sin(rot);
        float tz = v.x * FastMath.sin(rot) + v.z * FastMath.cos(rot);
        return new Vector3f(tx, 0, tz);
    }

    /**
     * Gets the origin position to which the vehicle should be moved
     */
    public Vector3f getStartPosition()
    {
        return startPosition;
    }

    /**
     * Gets the destination position to which the vehicle should be moved
     */
    public Vector3f getEndPosition()
    {
        return endPosition;
    }

    /**
     * Gets the interpolated list of path positions
     * 
     * @return
     */
    public List<Vector3f> getInterpolatedPositions()
    {
        return interpolatedPositions;
    }

    /**
     * Gets the interpolated list of heading positions
     * 
     * @return
     */
    public List<Vector3f> getInterpolatedHeadings()
    {
        return interpolatedHeadings;
    }

    public boolean hasCompleted()
    {
        return currentStep == INTERPOLATION_STEPS + 1;
    }
}