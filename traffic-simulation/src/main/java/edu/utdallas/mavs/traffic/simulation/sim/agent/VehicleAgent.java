package edu.utdallas.mavs.traffic.simulation.sim.agent;

import java.io.Serializable;

import edu.utdallas.mavs.divas.core.sim.agent.AbstractAgent;
import edu.utdallas.mavs.divas.core.sim.agent.interaction.communication.SimpleAgentCommunicationModule;
import edu.utdallas.mavs.divas.core.sim.common.stimulus.Stimuli;
import edu.utdallas.mavs.traffic.simulation.sim.agent.interaction.VehicleInteractionModule;
import edu.utdallas.mavs.traffic.simulation.sim.agent.interaction.perception.VehiclePerceptionModule;
import edu.utdallas.mavs.traffic.simulation.sim.agent.knowledge.VehicleKnowledgeModule;
import edu.utdallas.mavs.traffic.simulation.sim.agent.planning.VehiclePlanExecutor;
import edu.utdallas.mavs.traffic.simulation.sim.agent.planning.VehiclePlanGenerator;
import edu.utdallas.mavs.traffic.simulation.sim.agent.planning.VehiclePlanningModule;
import edu.utdallas.mavs.traffic.simulation.sim.agent.tasks.VehicleTaskModule;
import edu.utdallas.mavs.traffic.simulation.sim.common.state.VehicleAgentState;

public class VehicleAgent extends
		AbstractAgent<VehicleAgentState, VehicleKnowledgeModule, VehicleInteractionModule, VehiclePlanningModule, VehicleTaskModule>
		implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public VehicleAgent(VehicleAgentState state) {
		super(state);
	}

	@Override
	protected VehicleInteractionModule createInteractionModule(VehicleKnowledgeModule knowledgeModule) {
		return new VehicleInteractionModule(new VehiclePerceptionModule(knowledgeModule),
				new SimpleAgentCommunicationModule(knowledgeModule.getId()));
	}

	@Override
	protected VehicleKnowledgeModule createKnowledgeModule(VehicleAgentState state) {
		return new VehicleKnowledgeModule(state);
	}

	@Override
	protected VehiclePlanningModule createPlanningModule(VehicleKnowledgeModule knowledgeModule,
			VehicleTaskModule taskModule, VehicleInteractionModule interactionModule) {
		VehiclePlanGenerator planGenerator = new VehiclePlanGenerator(knowledgeModule, taskModule);
		VehiclePlanExecutor planExecutor = new VehiclePlanExecutor(knowledgeModule);
		return new VehiclePlanningModule(planGenerator, knowledgeModule, planExecutor);
	}

	@Override
	protected VehicleTaskModule createTaskModule(VehicleKnowledgeModule knowledgeModule) {
		return new VehicleTaskModule(knowledgeModule);
	}

	@Override
	protected Stimuli generateStimuli() {
		Stimuli stimuli = null;

		if (agentDriver != null) {
			agentDriver.close();
			agentDriver = null;
		}
		stimuli = plan(); // generate stimuli

		return stimuli;
	}

	private Stimuli plan() {
		try {
			return planningModule.plan();
		} catch (Exception e) {
			return new Stimuli();
		}
	}

}
