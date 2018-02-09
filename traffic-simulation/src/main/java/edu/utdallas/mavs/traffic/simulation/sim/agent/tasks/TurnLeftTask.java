package edu.utdallas.mavs.traffic.simulation.sim.agent.tasks;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;

import edu.utdallas.mavs.divas.core.sim.agent.task.Task;
import edu.utdallas.mavs.traffic.simulation.sim.common.state.Posture;

public class TurnLeftTask extends TurnTask
{
    private static final long  serialVersionUID = 1L;

    /**
     * The name of this task
     */
    public static final String NAME             = "TurnLeft";

    /**
     * The type of this task
     */
    public static final String TYPE             = "TurnLeft";

    public TurnLeftTask(boolean enabled)
    {
        this(-1, enabled);
    }

    public TurnLeftTask(long executionCycle, boolean enabled)
    {
        super(NAME, TYPE, executionCycle, enabled);
    }

    @Override
    public Task createTask(long executionCycle)
    {
        return new TurnLeftTask(executionCycle, enabled);
    }

    public TurnLeftTask createTask(long time, Vector3f startPosition, Vector3f endPosition)
    {
        TurnLeftTask task = new TurnLeftTask(time, enabled);
        task.startPosition = startPosition;
        task.endPosition = endPosition;
        interpolatePath(startPosition, endPosition, FastMath.HALF_PI, false, task.interpolatedPositions, task.interpolatedHeadings);
        posture = Posture.forward_mid_left;
        return task;
    }
}
