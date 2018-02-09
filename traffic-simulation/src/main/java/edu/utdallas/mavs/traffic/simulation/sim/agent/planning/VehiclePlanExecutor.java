package edu.utdallas.mavs.traffic.simulation.sim.agent.planning;

import java.io.Serializable;

import edu.utdallas.mavs.divas.core.sim.agent.planning.AbstractPlanExecutor;
import edu.utdallas.mavs.divas.core.sim.agent.planning.Plan;
import edu.utdallas.mavs.divas.core.sim.agent.task.Task;
import edu.utdallas.mavs.divas.core.sim.common.stimulus.Stimuli;
import edu.utdallas.mavs.traffic.simulation.sim.agent.knowledge.VehicleKnowledgeModule;
import edu.utdallas.mavs.traffic.simulation.sim.agent.tasks.VehicleTaskModule;

public class VehiclePlanExecutor extends AbstractPlanExecutor<VehicleKnowledgeModule, VehicleTaskModule> implements Serializable
{
    private static final long serialVersionUID = 1L;

    public VehiclePlanExecutor(VehicleKnowledgeModule knowledgeModule)
    {
        super(knowledgeModule);
    }

    @Override
    public Stimuli executePlan(Plan plan)
    {
        Stimuli sm = new Stimuli();

        for(Task task : plan.getTasks())
        {
            if(task != null && task.isEnabled())
            {
                sm.addAll(task.execute(knowledgeModule.getId()));
            }
        }

        return sm;

    }

}
