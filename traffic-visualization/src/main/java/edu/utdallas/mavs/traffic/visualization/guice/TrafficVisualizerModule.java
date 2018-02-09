package edu.utdallas.mavs.traffic.visualization.guice;

import com.google.inject.Singleton;

import edu.utdallas.mavs.divas.core.client.SimAdapter;
import edu.utdallas.mavs.divas.core.client.SimCommander;
import edu.utdallas.mavs.divas.core.client.SimFacade;
import edu.utdallas.mavs.divas.mts.CommunicationModule;
import edu.utdallas.mavs.divas.visualization.guice.CommunicationModuleProvider;
import edu.utdallas.mavs.divas.visualization.guice.VisualizerModule;
import edu.utdallas.mavs.divas.visualization.vis3D.spectator.PlayGround;
import edu.utdallas.mavs.traffic.visualization.vis3D.TrafficApplication;
import edu.utdallas.mavs.traffic.visualization.vis3D.TrafficVisualizer3DApplication;
import edu.utdallas.mavs.traffic.visualization.vis3D.spectator.TrafficPlayground;

/**
 * This class implements this module's dependency injection container.
 */
public class TrafficVisualizerModule extends VisualizerModule
{
    @Override
    protected void configure()
    {
        // super.configure();
        bind(SimFacade.class).to(SimCommander.class).in(Singleton.class);
        bind(SimAdapter.class).in(Singleton.class);
        bind(CommunicationModule.class).toProvider(CommunicationModuleProvider.class).in(Singleton.class);

        bind(TrafficVisualizer3DApplication.class).in(Singleton.class);
        bind(TrafficApplication.class).in(Singleton.class);
        bind(PlayGround.class).to(TrafficPlayground.class).in(Singleton.class);
    }
}