package edu.utdallas.mavs.traffic.simulation.sim.common.stimulus;

import com.jme3.math.Vector3f;
import edu.utdallas.mavs.divas.core.sim.common.stimulus.AgentStimulus;

public class ChangeHeadingStimulus extends AgentStimulus
{
    private float             x;
    private float             y;
    private float             z;
    public ChangeHeadingStimulus(int id, Vector3f heading)
    {
        this(id, heading.x, heading.y, heading.z);
    }

    
    public ChangeHeadingStimulus(int id, float x, float y, float z)
    {
        super(id);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX()
    {
        return x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public float getZ()
    {
        return z;
    }

    public void setZ(float z)
    {
        this.z = z;
    }

    public Vector3f getHeading()
    {
        return new Vector3f(x, y, z);
    }
}