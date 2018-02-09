package edu.utdallas.mavs.traffic.simulation.sim.agent.tasks;

import com.jme3.math.Vector3f;

import edu.utdallas.mavs.divas.core.sim.agent.task.AbstractTask;
import edu.utdallas.mavs.divas.core.sim.agent.task.Task;
import edu.utdallas.mavs.divas.core.sim.common.stimulus.Stimuli;
import edu.utdallas.mavs.traffic.simulation.sim.common.state.Posture;
import edu.utdallas.mavs.traffic.simulation.sim.common.stimulus.ChangeHeadingStimulus;
import edu.utdallas.mavs.traffic.simulation.sim.common.stimulus.MoveStimulus;

public class MoveForwardTask extends AbstractTask
{
    private static final long  serialVersionUID = 1L;

    /**
     * The name of this task
     */
    public static final String NAME             = "MoveForward";

    /**
     * The type of this task
     */
    public static final String TYPE             = "MoveForward";

    /**
     * The position of the agent after this task is executed
     */
    private Vector3f           position;

    /**
     * The current position of the agent after this task is executed
     */
    private Vector3f           currentPosition;
    
    /**
     * The posture associated with the MoveForwardTask
     */
    private Posture posture;

    public MoveForwardTask(boolean enabled)
    {
        this(-1, enabled);
    }

    public MoveForwardTask(long executionCycle, boolean enabled)
    {
        super(NAME, TYPE, executionCycle, enabled);
    }

    @Override
    public Task createTask(long executionCycle)
    {
        return new MoveForwardTask(executionCycle, enabled);
    }

    public MoveForwardTask createTask(long executionCycle, Vector3f currentPosition, Vector3f position)
    {
        MoveForwardTask task = new MoveForwardTask(executionCycle, enabled);
        task.position = position;
        task.currentPosition = currentPosition;
        posture = Posture.forward_mid;
        return task;
    }

    @Override
    public Stimuli execute(int agentId)
    {
        Stimuli stimuli = new Stimuli();
        stimuli.add(new MoveStimulus(agentId, position));
        stimuli.add(new ChangeHeadingStimulus(agentId, position.subtract(currentPosition)));
        return stimuli;
    }

    public Posture getPosture()
    {
        return posture;
    }
}