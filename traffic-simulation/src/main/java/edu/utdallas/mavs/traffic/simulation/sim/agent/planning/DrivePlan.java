package edu.utdallas.mavs.traffic.simulation.sim.agent.planning;

import com.jme3.math.Vector3f;

import edu.utdallas.mavs.divas.core.sim.agent.planning.Plan;
import edu.utdallas.mavs.divas.core.sim.agent.task.Task;

public class DrivePlan extends Plan
{
    private static final long serialVersionUID = 1L;

    Vector3f                  nextPosition;

    public DrivePlan()
    {
        super();
    }

    /**
     * Format plan for printing
     */
    @Override
    public String toString()
    {
        String str = "";
        for(Task t : tasks)
        {
            if(t != null && tasks != null)
            {
                str += " " + t.toString();
            }
        }

        if(str == "")
        {
            str += "Idle";
        }

        return str;
    }
}
