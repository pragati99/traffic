package edu.utdallas.mavs.traffic.simulation.sim.agent.interaction.perception.sensors;

import java.io.Serializable;
import java.util.List;

import com.jme3.math.Vector3f;

import edu.utdallas.mavs.divas.core.sim.agent.interaction.perception.sensors.PhysicalSensor;
import edu.utdallas.mavs.divas.core.sim.common.event.EnvEvent;
import edu.utdallas.mavs.divas.core.sim.common.state.AgentState;
import edu.utdallas.mavs.divas.core.sim.common.state.EnvObjectState;
import edu.utdallas.mavs.traffic.simulation.sim.agent.interaction.perception.VehiclePerceptionModule;
import edu.utdallas.mavs.traffic.simulation.sim.agent.knowledge.VehicleKnowledgeModule;

public class VehicleVisualSensor extends PhysicalSensor<VehicleKnowledgeModule, VehiclePerceptionModule>
		implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new visual sensor
	 * 
	 * @param knowledgeModule
	 *            the agent's KnowledgeModule
	 * @param humanPerceptionModule
	 *            the agent's perception module
	 */
	public VehicleVisualSensor(VehicleKnowledgeModule knowledgeModule, VehiclePerceptionModule perceptionModule) {
		super(knowledgeModule, perceptionModule);
	}

	@Override
	protected void receiveAgents(List<AgentState> agents) {
		for (AgentState agent : agents) {
			if (isInCircleOfInfluence(agent.getPosition()))
				knowledgeModule.addAgent(agent);
		}

	}

	@Override
	protected void receiveEnvObjs(List<EnvObjectState> envObjs) {
		for (EnvObjectState obj : envObjs) {
			if (isInCircleOfInfluence(obj.getPosition()))
				knowledgeModule.addEnvObj(obj);
		}
	}

	@Override
	protected void receiveEvents(List<EnvEvent> arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void resolveObstructions() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setAlgorithm() {
		// TODO Auto-generated method stub
	}

	private boolean isInCircleOfInfluence(Vector3f nearAgentPosition) {
		if (knowledgeModule.getSelf().getPosition().distance(nearAgentPosition) - 10 < knowledgeModule.getSelf()
				.getVisibleDistance()) {
			return true;
		} else {
			return false;
		}
	}
}

