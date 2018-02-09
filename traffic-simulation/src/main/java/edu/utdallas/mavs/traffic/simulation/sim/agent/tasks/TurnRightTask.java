package edu.utdallas.mavs.traffic.simulation.sim.agent.tasks;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;

import edu.utdallas.mavs.divas.core.sim.agent.task.Task;
import edu.utdallas.mavs.traffic.simulation.sim.common.state.Posture;

public class TurnRightTask extends TurnTask
{
    private static final long  serialVersionUID = 1L;

    /**
     * The name of this task
     */
    public static final String NAME             = "TurnRight";

    /**
     * The type of this task
     */
    public static final String TYPE             = "TurnRight";

    public TurnRightTask(boolean enabled)
    {
        this(-1, enabled);
    }

    public TurnRightTask(long executionCycle, boolean enabled)
    {
        super(NAME, TYPE, executionCycle, enabled);
    }

    @Override
    public Task createTask(long executionCycle)
    {
        return new TurnRightTask(executionCycle, enabled);
    }

    public TurnRightTask createTask(long time, Vector3f startPosition, Vector3f endPosition)
    {
        TurnRightTask task = new TurnRightTask(time, enabled);
        task.startPosition = startPosition;
        task.endPosition = endPosition;
        interpolatePath(startPosition, endPosition, FastMath.HALF_PI, true, task.interpolatedPositions, task.interpolatedHeadings);
        posture = Posture.forward_mid_right;
        return task;
    }
}