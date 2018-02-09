package edu.utdallas.mavs.traffic.visualization.vis3D.appstate;

import com.jme3.math.Vector3f;

import edu.utdallas.mavs.divas.visualization.vis3D.appstate.SimulatingAppState;
import edu.utdallas.mavs.divas.visualization.vis3D.common.InputMode;
import edu.utdallas.mavs.divas.visualization.vis3D.dialog.NiftyScreen;
import edu.utdallas.mavs.traffic.visualization.vis3D.TrafficApplication;
import edu.utdallas.mavs.traffic.visualization.vis3D.dialog.vehicleModification.VehicleModificationDialog;

/**
 * This class represents the main application state of the traffic 3D visualizer.
 * 
 * It allows users to interact with the visualized simulation, by selecting visualized objects (VOs) and triggering events to the simulation. 
 * It also allows users to add agents to simulation.
 */
public class TrafficSimulatingAppState extends SimulatingAppState<TrafficApplication>
{

    @Override
    protected void triggerEvent(Vector3f position)
    {
        // specific events here
    }

    @Override
    public void setCursor(InputMode inputMode)
    {
        super.setCursor(inputMode);

        // specific modes here
    }

    @Override
    protected InputMode getMappedEventInputMode(String inputMode)
    {
        InputMode mappedInputMode = InputMode.SELECTION;

        // specific modes here

        return mappedInputMode;
    }
    
    @Override
    protected void setupContextObservers()
    {
        super.setupContextObservers();
        
        contextObservers.add(new VehicleModificationDialog(NiftyScreen.windowLayerElement));
    }
}