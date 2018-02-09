package edu.utdallas.mavs.traffic.simulation.host;

import edu.utdallas.mavs.divas.core.host.Host;
import edu.utdallas.mavs.divas.mts.MTSClient;
import edu.utdallas.mavs.traffic.simulation.sim.TrafficSimulation;

/**
 * This class describes a Host for simulations of traffic scenarios.
 */
public class TrafficHost extends Host {
	@Override
	protected void createSimulation(MTSClient client) {
		simulation = new TrafficSimulation(client);
	}
}