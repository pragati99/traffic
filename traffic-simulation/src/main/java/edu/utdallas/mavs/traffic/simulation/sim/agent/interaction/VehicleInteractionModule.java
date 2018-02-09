package edu.utdallas.mavs.traffic.simulation.sim.agent.interaction;

import java.io.Serializable;

import edu.utdallas.mavs.divas.core.sim.agent.interaction.AbstractInteractionModule;
import edu.utdallas.mavs.divas.core.sim.agent.interaction.communication.AgentCommunicationModule;
import edu.utdallas.mavs.divas.core.sim.agent.interaction.perception.PerceptionModule;
import edu.utdallas.mavs.traffic.simulation.sim.agent.interaction.perception.VehiclePerceptionModule;

public class VehicleInteractionModule extends AbstractInteractionModule<PerceptionModule, AgentCommunicationModule> implements Serializable
{
    private static final long serialVersionUID = 1L;

    public VehicleInteractionModule(VehiclePerceptionModule perceptionModule, AgentCommunicationModule communicationModule)
    {
        super(perceptionModule, communicationModule);
    }

}