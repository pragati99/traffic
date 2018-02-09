package edu.utdallas.mavs.traffic.simulation.sim.agent.tasks;

import java.io.Serializable;
import java.util.Set;

import com.jme3.math.Vector3f;

import edu.utdallas.mavs.divas.core.sim.agent.task.AbstractTaskModule;
import edu.utdallas.mavs.traffic.simulation.sim.agent.knowledge.VehicleKnowledgeModule;

public class VehicleTaskModule extends AbstractTaskModule<VehicleKnowledgeModule> implements Serializable
{

    private static final long        serialVersionUID = 1L;  

    private MoveForwardTask          moveForwardTask;

    private TurnLeftTask             turnLeftTask;

    private TurnRightTask            turnRightTask;

    public VehicleTaskModule(VehicleKnowledgeModule knowledgeModule)
    {
        super(knowledgeModule);        
    }

    @Override
    protected void loadTasks()
    {
        Set<String> availableTasks = knowledgeModule.getSelf().getTaskNames();
        moveForwardTask = new MoveForwardTask(availableTasks.contains(MoveForwardTask.TYPE));
        turnLeftTask = new TurnLeftTask(availableTasks.contains(TurnLeftTask.TYPE));
        turnRightTask = new TurnRightTask(availableTasks.contains(TurnRightTask.TYPE));
        tasks.put(MoveForwardTask.TYPE, moveForwardTask);
        tasks.put(TurnLeftTask.TYPE, turnLeftTask);
        tasks.put(TurnRightTask.TYPE, turnRightTask);
    }

    public MoveForwardTask createMoveForwardTask(Vector3f position)
    {
        return moveForwardTask.createTask(knowledgeModule.getTime(), knowledgeModule.getSelf().getPosition(), position);
    }

    public TurnLeftTask createTurnLeftTask(Vector3f endPosition)
    {
        return turnLeftTask.createTask(knowledgeModule.getTime(), knowledgeModule.getSelf().getPosition(), endPosition);
    }

    public TurnRightTask createTurnRightTask(Vector3f endPosition)
    {
        return turnRightTask.createTask(knowledgeModule.getTime(), knowledgeModule.getSelf().getPosition(), endPosition);
    }
    
}
