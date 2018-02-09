package edu.utdallas.mavs.traffic.visualization.vis3D;

import com.google.inject.Inject;

import edu.utdallas.mavs.divas.core.client.SimAdapter;
import edu.utdallas.mavs.divas.visualization.vis3D.Visualizer3DApplication;
import edu.utdallas.mavs.divas.visualization.vis3D.spectator.VisualSpectator;

/**
 * This class describes the 3D visualizer singleton class.
 * <p>
 */
public class TrafficVisualizer3DApplication extends Visualizer3DApplication<TrafficApplication>
{
    @Inject
    public TrafficVisualizer3DApplication(SimAdapter simClientAdapter, TrafficApplication app, VisualSpectator spectator)
    {
        super(simClientAdapter, app, spectator);
    }
}
