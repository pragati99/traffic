package edu.utdallas.mavs.traffic.simulation.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import edu.utdallas.mavs.traffic.simulation.host.TrafficHost;

public class SimulationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(TrafficHost.class).in(Singleton.class);
	}
}