package edu.utdallas.mavs.traffic.simulation.sim.common.stimulus;

import com.jme3.math.Vector3f;
import edu.utdallas.mavs.divas.core.sim.common.stimulus.AgentStimulus;

public class MoveStimulus extends AgentStimulus implements Comparable<MoveStimulus>
{
    private float             x;
    private float             y;
    private float             z;

    public MoveStimulus(int id, Vector3f position)
    {
        this(id, position.x, position.y, position.z);
    }

    public MoveStimulus(int id, float x, float y, float z)
    {
        super(id);

        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Get the position where the agent wishes to move.
     * 
     * @return the position where the agent wishes to move
     */
    public Vector3f getPosition()
    {
        return new Vector3f(x, y, z);
    }

    @Override
    public int compareTo(MoveStimulus o)
    {
        if(this.id > o.id)
            return 1;
        else if(this.id < o.id)
            return -1;
        else
            return 0;
    }
}
