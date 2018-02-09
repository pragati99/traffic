package edu.utdallas.mavs.traffic.simulation.sim.agent.interaction.communication;

import java.io.Serializable;

import com.jme3.math.Vector3f;

import edu.utdallas.mavs.divas.core.sim.agent.interaction.communication.AgentMessage;

public class V2VMessage extends AgentMessage implements Serializable
{
    private static final long serialVersionUID = 1L;

    /**
     * The vehicle's speed 
     */
    Vector3f speed;
    
    /**
     * The vehicle's heading
     */
    Vector3f heading;
    
    /**
     * The vehicle's position
     */
    Vector3f position;
    
    /**
     * @param sourceID
     * @param destinationID
     * @param message
     * @param speed the source vehilce's speed
     * @param heading the source vehilce's heading
     * @param position the source vehilce's position
     */
    public V2VMessage(int sourceID, int destinationID, String message, Vector3f speed, Vector3f heading, Vector3f position)
    {
        super(sourceID, destinationID, message);
        this.speed = speed;
        this.heading = heading;
        this.position = position;
    }

    /**
     * @return the the source vehilce's position
     */
    public Vector3f getSpeed()
    {
        return speed;
    }

    /**
     * @return the source vehilce's heading
     */
    public Vector3f getHeading()
    {
        return heading;
    }

	/**
	 * @return the source vehilce's position
	 */
	public Vector3f getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Vector3f position) {
		this.position = position;
	}
    
    
    

}
