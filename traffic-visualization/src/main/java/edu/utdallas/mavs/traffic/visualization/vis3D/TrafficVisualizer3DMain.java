package edu.utdallas.mavs.traffic.visualization.vis3D;

import org.apache.log4j.PropertyConfigurator;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;

import edu.utdallas.mavs.divas.visualization.guice.CommunicationModuleProvider;
import edu.utdallas.mavs.traffic.visualization.guice.TrafficVisualizerModule;

/**
 * 3D visualization system for the traffic simulation.
 * <p>
 * This 3D visualizer is built on a client-server architecture, providing visualization of traffic simulations based on JMonkey 3.
 */
public class TrafficVisualizer3DMain
{
    /**
     * The 3D visualizer entry point. Configures and initializes the visualizer.
     * 
     * @param args to be passed to the visualizer. Currently no args are processed.
     */
    public static void main(String args[])
    {
        // disable java logging (used by jme)
        java.util.logging.Logger.getLogger("").setLevel(java.util.logging.Level.WARNING);

        // configure logging properties
        PropertyConfigurator.configure("log4j.properties");

        // Sets the simulation host IP and port properties
        CommunicationModuleProvider.setConnectionProperties(args[0].split(" ")[0], args[0].split(" ")[1]);

        final Injector injector = Guice.createInjector(Stage.PRODUCTION, new TrafficVisualizerModule());
        final TrafficVisualizer3DApplication visualizerApplication = injector.getInstance(TrafficVisualizer3DApplication.class);

        visualizerApplication.start();
    }
}
