package edu.utdallas.mavs.traffic.simulation.sim.agent.planning;

import java.io.Serializable;

import edu.utdallas.mavs.divas.core.sim.agent.knowledge.internal.Goal;
import edu.utdallas.mavs.divas.core.sim.agent.planning.AbstractPlanningModule;
import edu.utdallas.mavs.divas.core.sim.agent.planning.Plan;
import edu.utdallas.mavs.divas.core.sim.common.stimulus.Stimuli;
import edu.utdallas.mavs.traffic.simulation.sim.agent.knowledge.VehicleKnowledgeModule;
import edu.utdallas.mavs.traffic.simulation.sim.agent.tasks.VehicleTaskModule;

public class VehiclePlanningModule extends AbstractPlanningModule<VehicleKnowledgeModule, VehicleTaskModule> implements Serializable
{
    private static final long    serialVersionUID = 1L;

    private VehiclePlanGenerator planGenerator;
    private VehiclePlanExecutor  planExecutor;

    public VehiclePlanningModule(VehiclePlanGenerator planGenerator, VehicleKnowledgeModule knowledgeModule, VehiclePlanExecutor planExecutor)
    {
        super(knowledgeModule);
        this.planGenerator = planGenerator;
        this.planExecutor = planExecutor;
    }

    @Override
    public void addGoal(Goal arg0)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public Stimuli plan()
    {
        Stimuli sm = new Stimuli();
        Plan plan = planGenerator.plan();
        knowledgeModule.getSelf().setCurrentTasks(plan.getTasks());
        sm = planExecutor.executePlan(plan);
        return sm;
    }
}
