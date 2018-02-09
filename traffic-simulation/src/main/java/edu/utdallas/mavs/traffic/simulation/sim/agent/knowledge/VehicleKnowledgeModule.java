package edu.utdallas.mavs.traffic.simulation.sim.agent.knowledge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import com.jme3.math.Vector3f;

import edu.utdallas.mavs.divas.core.sim.agent.interaction.perception.data.CombinedReasonedData;
import edu.utdallas.mavs.divas.core.sim.agent.knowledge.AbstractKnowledgeModule;
import edu.utdallas.mavs.divas.core.sim.agent.knowledge.external.EventKnowledge;
import edu.utdallas.mavs.divas.core.sim.agent.knowledge.external.EventPropertyKnowledge;
import edu.utdallas.mavs.divas.core.sim.agent.knowledge.internal.Goal;
import edu.utdallas.mavs.divas.core.sim.common.state.AgentState;
import edu.utdallas.mavs.divas.core.sim.common.state.EnvObjectState;
import edu.utdallas.mavs.traffic.simulation.sim.common.state.VehicleAgentState;

public class VehicleKnowledgeModule extends AbstractKnowledgeModule<VehicleAgentState>
{

    private static final long                            serialVersionUID    = 1L;
    public static final int                              CAR_OFFSET          = 5;
    private UndirectedGraph<EnvObjectState, DefaultEdge> streetGraph;
    private EnvObjectState                               currentStreetTile;
    private EnvObjectState                               currentGoalTile;
    public float                                         desiredSpeed        = 5;
    private Vector3f                                     heading;
    private Vector3f                                     previousHeading;
    private Vector3f                                     previousVertexPos;
    private Vector3f                                     currentGoalPos;
    private int                                          currentGoalDegree;
    private Map<Integer, EnvObjectState>                 streetTiles         = new HashMap<Integer, EnvObjectState>();
    private Map<Integer, AgentState>                     agents              = new HashMap<Integer, AgentState>();
    private float                                        xMargin             = 0;
    private float                                        zMargin             = 0;
    private Goal                                         finalGoal           = null;
    private int                                          waitTime            = 0;


    public VehicleKnowledgeModule(VehicleAgentState state)
    {
        super(state);        
        streetGraph = new SimpleGraph<EnvObjectState, DefaultEdge>(DefaultEdge.class);
    }

    public void addStreetTile(EnvObjectState street)
    {
        streetGraph.addVertex(street);
        for(EnvObjectState st : streetGraph.vertexSet())
        {
            if(((street.getPosition().getX() == st.getPosition().getX()) && (Math.abs(street.getPosition().getZ() - st.getPosition().getZ()) <= 2 * street.getScale().getZ()))

            || (street.getPosition().getZ() == st.getPosition().getZ()) && (Math.abs(street.getPosition().getX() - st.getPosition().getX()) <= 2 * street.getScale().getX()))
            {
                if((!streetGraph.containsEdge(street, st)) && (street.getID() != st.getID()))
                {
                    streetGraph.addEdge(street, st);
                }
            }
        }
    }

    public Vector3f getNextGoal()
    {
        if(getPreviousHeading() == null)
        {
            setPreviousHeading(new Vector3f(0f, 0f, 0f));
        }

        if(previousVertexPos == null)
        {
            previousVertexPos = currentStreetTile.getPosition();
        }

        if(currentGoalTile == null)
        {
            currentGoalTile = currentStreetTile;
        }
        Iterator<EnvObjectState> iter;
        List<EnvObjectState> neighbourTiles = new ArrayList<EnvObjectState>();

        Set<DefaultEdge> edges = streetGraph.edgesOf(currentGoalTile);
        for(DefaultEdge e : edges)
        {
            EnvObjectState sourceVertex = streetGraph.getEdgeSource(e);
            EnvObjectState targetVertex = streetGraph.getEdgeTarget(e);
            EnvObjectState neighbourVertex = null;
            if(sourceVertex == currentGoalTile)
            {
                neighbourVertex = targetVertex;
            }
            else
            {
                neighbourVertex = sourceVertex;
            }

            neighbourTiles.add(neighbourVertex);
        }

        Collections.shuffle(neighbourTiles);
        iter = neighbourTiles.iterator();

        EnvObjectState vertex;
        while(iter.hasNext())
        {
            vertex = iter.next();
            Vector3f adjustedGoalPos = adjustGoal(vertex.getPosition());

            if(currentGoalPos != null)
            {
                if(((getHeading().equals(new Vector3f(0, 0, -1)) && vertex.getPosition().getZ() <= currentGoalPos.getZ())
                        || (getHeading().equals(new Vector3f(0, 0, 1)) && vertex.getPosition().getZ() >= currentGoalPos.getZ())
                        || (getHeading().equals(new Vector3f(-1, 0, 0)) && vertex.getPosition().getX() <= currentGoalPos.getX()) 
						|| (getHeading().equals(new Vector3f(1, 0, 0)) && vertex.getPosition().getX() >= currentGoalPos.getX())))
                {
                    previousVertexPos = currentGoalPos;
                    adjustHeading(adjustedGoalPos, vertex.getPosition());
                    currentGoalPos = vertex.getPosition();
                    currentGoalDegree = streetGraph.degreeOf(vertex);
                    Vector3f adjustGoalMargin = adjustGoalMargin(adjustedGoalPos, vertex.getPosition());
                    currentStreetTile = currentGoalTile;
                    currentGoalTile = vertex;
                    return adjustGoalMargin;
                }
            }
            else
            {
                getFirstTimeHeading();
                currentStreetTile = currentGoalTile;
                return adjustGoalMargin(adjustGoal(currentGoalPos), vertex.getPosition());
            }
        }

        return null;
    }

    // To make the vehicle preserve the lane it uses
    private Vector3f adjustGoalMargin(Vector3f goal, Vector3f streetTileCeter)
    {
        if(getPreviousHeading() == null)
        {
            setPreviousHeading(new Vector3f(0f, 0f, 0f));
        }
        int sign = 1;
        if(Math.abs(getHeading().getZ()) == 1 && getPreviousHeading().equals(getHeading()))
        {
            Vector3f marginGoal = new Vector3f(goal.getX(), goal.getY(), (streetTileCeter.getZ() + getHeading().getZ() * -15 + -getzMargin() * getHeading().getX() * -1));
            return marginGoal;
        }

        if(Math.abs(getHeading().getX()) == 1 && getPreviousHeading().equals(getHeading()))
        {
            Vector3f marginGoal = new Vector3f((streetTileCeter.getX() + getHeading().getX() * -15 + getxMargin() * getHeading().getZ() * -1), goal.getY(), goal.getZ());
            return marginGoal;
        }

        if(Math.abs(getHeading().getX()) == 1 && !getPreviousHeading().equals(getHeading()))
        {
            if((getPreviousHeading().getZ() == 1 && getHeading().getX() == 1) || (getPreviousHeading().getZ() == -1 && getHeading().getX() == -1))
            {
                sign = 1;
            }
            else if((getPreviousHeading().getZ() == 1 && getHeading().getX() == -1) || (getPreviousHeading().getZ() == -1 && getHeading().getX() == 1))
            {
                sign = -1;
            }
            float z = sign * (previousVertexPos.getX() - getSelf().getPosition().getX());
            Vector3f marginGoal = new Vector3f((streetTileCeter.getX() + getHeading().getX() * -10 + getxMargin() * getHeading().getZ() * -1), goal.getY(), streetTileCeter.getZ() + z);
            return marginGoal;
        }

        if(Math.abs(getHeading().getZ()) == 1 && !getPreviousHeading().equals(getHeading()))
        {
            if((getPreviousHeading().getX() == 1 && getHeading().getZ() == 1) || (getPreviousHeading().getX() == -1 && getHeading().getZ() == -1))
            {
                sign = 1;
            }
            else if((getPreviousHeading().getX() == 1 && getHeading().getZ() == -1) || (getPreviousHeading().getX() == -1 && getHeading().getZ() == 1))
            {
                sign = -1;
            }
            float x = sign * (previousVertexPos.getZ() - getSelf().getPosition().getZ());
            Vector3f marginGoal = new Vector3f(streetTileCeter.getX() + x, goal.getY(), (streetTileCeter.getZ() + getHeading().getZ() * -10 + getzMargin() * getHeading().getX() * -1));
            return marginGoal;
        }

        return null;
    }

    // makes the goal parallel to current position
    private Vector3f adjustGoal(Vector3f goal)
    {
        Vector3f originalGoalPosition;
        if(currentGoalPos == null)
        {
            originalGoalPosition = getSelf().getPosition();
        }
        else
        {
            originalGoalPosition = currentGoalPos;
        }

        if((originalGoalPosition.getX() >= goal.getX() - 10) && (originalGoalPosition.getX() <= goal.getX() + 10))
        {
            return new Vector3f(getSelf().getPosition().getX(), goal.getY(), goal.getZ());
        }
        if((originalGoalPosition.getZ() >= goal.getZ() - 10) && (originalGoalPosition.getZ() <= goal.getZ() + 10))
        {
            return new Vector3f(goal.getX(), goal.getY(), getSelf().getPosition().getZ());
        }

        return null;
    }

    private void adjustHeading(Vector3f goal, Vector3f goalStreetTile)
    {
        setPreviousHeading(getHeading());

        if((getSelf().getPosition().getX() == goal.getX()) && (getSelf().getPosition().getZ() < goal.getZ()))
        {
            setHeading(new Vector3f(0f, 0f, 1f));
            setxMargin(Math.abs(goal.getX() - goalStreetTile.getX()));
            setzMargin(0);
        }
        else if((getSelf().getPosition().getX() == goal.getX()) && (getSelf().getPosition().getZ() > goal.getZ()))
        {
            setHeading(new Vector3f(0f, 0f, -1f));
            setxMargin(Math.abs(goal.getX() - goalStreetTile.getX()));
            setzMargin(0);
        }
        else if((getSelf().getPosition().getZ() == goal.getZ()) && (getSelf().getPosition().getX() < goal.getX()))
        {
            setHeading(new Vector3f(1f, 0f, 0f));
            setxMargin(0);
            setzMargin(Math.abs(goal.getZ() - goalStreetTile.getZ()));
        }
        else if((getSelf().getPosition().getZ() == goal.getZ()) && (getSelf().getPosition().getX() > goal.getX()))
        {
            setHeading(new Vector3f(-1f, 0f, 0f));
            setxMargin(0);
            setzMargin(Math.abs(goal.getZ() - goalStreetTile.getZ()));
        }

    }

    @Override
    public void addAgent(AgentState agent)
    {
        agents.put(agent.getID(), agent);
    }

    @Override
    public void addEnvObj(EnvObjectState envObj)
    {
        if(!streetTiles.containsKey(envObj.getID()))
        {
            if((envObj.getType().equals("Streets")) || (envObj.getType().equals("road")) || (envObj.getType().equals("street")) || (envObj.getType().equals("road_horizntal"))
                    || (envObj.getType().equals("Cross Walk")))
            {
                streetTiles.put(envObj.getID(), envObj);
                addStreetTile(envObj);

                if(envObj.contains2D(getSelf().getPosition()))
                {
                    currentStreetTile = envObj;
                }

            }
        }
    }

    public void getFirstTimeHeading()
    {
        Set<DefaultEdge> edges = streetGraph.edgesOf(currentStreetTile);
        for(DefaultEdge e : edges)
        {
            EnvObjectState sourceVertex = streetGraph.getEdgeSource(e);
            EnvObjectState targetVertex = streetGraph.getEdgeTarget(e);
            EnvObjectState neighbourVertex = null;
            if(sourceVertex == currentStreetTile)
            {
                neighbourVertex = targetVertex;
            }
            else
            {
                neighbourVertex = sourceVertex;
            }

            if(neighbourVertex.getPosition().getX() > currentStreetTile.getPosition().getX() && getSelf().getPosition().getZ() > currentStreetTile.getPosition().getZ())
            {
                setHeading(new Vector3f(1, 0, 0));
                currentGoalPos = neighbourVertex.getPosition();
                currentGoalDegree = streetGraph.degreeOf(neighbourVertex);
                Vector3f laneCenteredPosition = new Vector3f(getSelf().getPosition().getX(), getSelf().getPosition().getY(), currentStreetTile.getPosition().getZ() + 5);
                getSelf().setPosition(laneCenteredPosition);
                currentGoalTile = neighbourVertex;
            }
            else if(neighbourVertex.getPosition().getX() < currentStreetTile.getPosition().getX() && getSelf().getPosition().getZ() < currentStreetTile.getPosition().getZ())
            {
                setHeading(new Vector3f(-1, 0, 0));
                currentGoalPos = neighbourVertex.getPosition();
                currentGoalDegree = streetGraph.degreeOf(neighbourVertex);
                Vector3f laneCenteredPosition = new Vector3f(getSelf().getPosition().getX(), getSelf().getPosition().getY(), currentStreetTile.getPosition().getZ() - 5);
                getSelf().setPosition(laneCenteredPosition);
                currentGoalTile = neighbourVertex;
            }
            else if(neighbourVertex.getPosition().getZ() > currentStreetTile.getPosition().getZ() && getSelf().getPosition().getX() < currentStreetTile.getPosition().getX())
            {
                setHeading(new Vector3f(0, 0, 1));
                currentGoalPos = neighbourVertex.getPosition();
                currentGoalDegree = streetGraph.degreeOf(neighbourVertex);
                Vector3f laneCenteredPosition = new Vector3f(currentStreetTile.getPosition().getX() - 5, getSelf().getPosition().getY(), getSelf().getPosition().getZ());
                getSelf().setPosition(laneCenteredPosition);
                currentGoalTile = neighbourVertex;
            }
            else if(neighbourVertex.getPosition().getZ() < currentStreetTile.getPosition().getZ() && getSelf().getPosition().getX() > currentStreetTile.getPosition().getX())
            {
                setHeading(new Vector3f(0, 0, -1));
                currentGoalPos = neighbourVertex.getPosition();
                currentGoalDegree = streetGraph.degreeOf(neighbourVertex);
                Vector3f laneCenteredPosition = new Vector3f(currentStreetTile.getPosition().getX() + 5, getSelf().getPosition().getY(), getSelf().getPosition().getZ());
                getSelf().setPosition(laneCenteredPosition);
                currentGoalTile = neighbourVertex;
            }
        }

    }

    public boolean collidesWithOtherVehicles(Vector3f nextPosition)
    {
        boolean result = false;
        Vector3f direction = nextPosition.subtract(self.getPosition()).normalizeLocal();
        for(AgentState v : agents.values())
        {
            if(v.contains2D(nextPosition.add(direction.mult(CAR_OFFSET+2))))
            {
                result = true;
                break;
            }
        }
        return result;
    }

   
    @Override
    public void addEventsThisTick(CombinedReasonedData arg0)
    {

    }

    @Override
    public void clearPerceptionKnowledge()
    {
        agents.clear();
    }

    @Override
    public EventKnowledge getEventKnowledgeByName(String arg0)
    {
        return null;
    }

    @Override
    public List<EventPropertyKnowledge> getEventKnowledgeFromType(String arg0)
    {
        return null;
    }

    @Override
    public List<CombinedReasonedData> getEventsThisTick()
    {
        return null;
    }

    public Vector3f getHeading()
    {
        return heading;
    }

    public void setHeading(Vector3f heading)
    {
        this.heading = heading;
    }

    public Vector3f getPreviousHeading()
    {
        return previousHeading;
    }

    public void setPreviousHeading(Vector3f previousHeading)
    {
        this.previousHeading = previousHeading;
    }

    public float getxMargin()
    {
        return xMargin;
    }

    public void setxMargin(float xMargin)
    {
        this.xMargin = xMargin;
    }

    public float getzMargin()
    {
        return zMargin;
    }

    public void setzMargin(float zMargin)
    {
        this.zMargin = zMargin;
    }

    public int getCurrentGoalDegree()
    {
        return currentGoalDegree;
    }

    public EnvObjectState getCurrentStreetTile()
    {
        return currentStreetTile;
    }
    
    public Goal getFinalGoal()
    {
        return finalGoal;
    }

    public void setFinalGoal(Goal finalGoal)
    {
        this.finalGoal = finalGoal;
    }
    
    public int getWaitTime()
    {
        return waitTime;
    }

    public void setWaitTime(int stopTime)
    {
        this.waitTime = stopTime;
    }
}
