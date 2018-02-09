package edu.utdallas.mavs.traffic.simulation;

import org.apache.log4j.PropertyConfigurator;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;

import edu.utdallas.mavs.traffic.simulation.config.TrafficVisConfig;
import edu.utdallas.mavs.traffic.simulation.guice.SimulationModule;
import edu.utdallas.mavs.traffic.simulation.host.TrafficHost;

/**
 * Simulation of evacuation scenarios over DIVAs framework.
 */
public class TrafficMain
{
    /**
     * Simulation entry point.
     * 
     * @param args no arguments defined
     */
    public static void main(String[] args)
    {
        // reads configuration properties for log4j
        PropertyConfigurator.configure("log4j.properties");

        // disables java logging
        java.util.logging.Logger.getLogger("").setLevel(java.util.logging.Level.OFF);
        
        // Register custom configurations
        TrafficVisConfig.register();


        final Injector injector = Guice.createInjector(Stage.PRODUCTION, new SimulationModule());
        injector.getInstance(TrafficHost.class).start();
    }
}
