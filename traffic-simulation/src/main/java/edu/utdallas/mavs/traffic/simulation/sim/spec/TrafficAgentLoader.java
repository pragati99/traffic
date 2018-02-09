package edu.utdallas.mavs.traffic.simulation.sim.spec;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.jme3.math.Vector3f;

import edu.utdallas.mavs.divas.core.sim.common.state.AgentState;
import edu.utdallas.mavs.divas.core.spec.agent.AgentLoader;
import edu.utdallas.mavs.traffic.simulation.sim.agent.tasks.MoveForwardTask;
import edu.utdallas.mavs.traffic.simulation.sim.agent.tasks.TurnLeftTask;
import edu.utdallas.mavs.traffic.simulation.sim.agent.tasks.TurnRightTask;
import edu.utdallas.mavs.traffic.simulation.sim.common.state.VehicleAgentState;

public class TrafficAgentLoader extends AgentLoader
{

    @Override
    public AgentState createAgent(String modelName)
    {
        AgentState agentState = createVehicleAgentState(modelName);
        return agentState;
    }

    private VehicleAgentState createVehicleAgentState(String modelName)
    {
        VehicleAgentState state = new VehicleAgentState();

        int modelNumber = getModelNumber(modelName);
		state.setModelName((modelName == null || modelName.isEmpty()) ? getRandomCarModel() : modelName + (modelNumber==0? "" : modelNumber));
        state.setAgentType("Vehicle");

        state.setScale(new Vector3f(5f, 5f, 5f));
        state.setVisibleDistance(70);
        state.setFOV(70);

        Set<String> taskNames = new HashSet<String>();
        taskNames.add(MoveForwardTask.NAME);
        taskNames.add(TurnLeftTask.NAME);
        taskNames.add(TurnRightTask.NAME);
        state.setTaskNames(taskNames);

        return state;
    }

    private int getModelNumber(String modelName)
    {
        Random r = new Random();
        int modelNumber = 0;

        if(modelName.equals("car_audi_"))
        {
            modelNumber = r.nextInt(7);
        }
        else if(modelName.equals("car_bmw_"))
        {
            modelNumber = 1;
        }
        else if(modelName.equals("car_fjsuv_"))
        {
            modelNumber = r.nextInt(7);
        }
        else if(modelName.equals("car_rsc_"))
        {
            modelNumber = r.nextInt(8);
        }
        else if(modelName.equals("car_sport_"))
        {
            modelNumber = r.nextInt(10);
        }
        return modelNumber;
    }

    private String getRandomCarModel()
    {
        Random r = new Random();

        List<String> models = new ArrayList<>();
        models.add("car_audi_");
        models.add("car_bmw_");
        models.add("car_fjsuv_");
        models.add("car_rsc_");
        models.add("car_sport_");

        String model = models.get(r.nextInt(models.size()));
        int modelNumber = getModelNumber(model);
        return model + modelNumber;
    }

}
