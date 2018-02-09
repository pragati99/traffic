package edu.utdallas.mavs.traffic.simulation.sim.agent.interaction.perception;

import java.io.Serializable;

import edu.utdallas.mavs.divas.core.sim.agent.interaction.perception.AbstractPerceptionModule;
import edu.utdallas.mavs.traffic.simulation.sim.agent.interaction.perception.sensors.VehicleVisualSensor;
import edu.utdallas.mavs.traffic.simulation.sim.agent.knowledge.VehicleKnowledgeModule;

public class VehiclePerceptionModule extends AbstractPerceptionModule<VehicleKnowledgeModule> implements Serializable
{
    private static final long     serialVersionUID = 1L;

    /**
     * The agent's vision sensor
     */
    protected VehicleVisualSensor visualSensor;

    public VehiclePerceptionModule(VehicleKnowledgeModule knowledgeModule)
    {
        super(knowledgeModule);
        visualSensor = new VehicleVisualSensor(knowledgeModule, this);
        addSense(visualSensor);
    }

    @Override
    protected void updateSensors()
    {

    }

}
