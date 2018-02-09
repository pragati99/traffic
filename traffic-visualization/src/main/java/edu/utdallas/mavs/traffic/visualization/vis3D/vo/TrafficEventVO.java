package edu.utdallas.mavs.traffic.visualization.vis3D.vo;

import com.jme3.scene.Spatial;

import edu.utdallas.mavs.divas.core.sim.common.event.EnvEvent;
import edu.utdallas.mavs.divas.visualization.vis3D.vo.EventVO;

/**
 * This class represents a visualized event
 */
public class TrafficEventVO extends EventVO
{
    /**
     * Cosntructs a new event VO
     * 
     * @param event the event state associated with this VO
     * @param cycle the simulation cycle number associated with this event
     */
    public TrafficEventVO(EnvEvent event, long cycle)
    {
        super(event, cycle);
    }

    @Override
    protected Spatial createEventVO(EnvEvent event)
    {        
        return EventFactory.createEventVO(event);
    }
}

