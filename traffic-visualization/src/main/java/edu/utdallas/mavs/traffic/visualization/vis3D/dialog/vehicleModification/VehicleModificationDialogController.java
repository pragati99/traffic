package edu.utdallas.mavs.traffic.visualization.vis3D.dialog.vehicleModification;

import org.bushe.swing.event.EventTopicSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lessvoid.nifty.controls.CheckBox;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.DropDownSelectionChangedEvent;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import edu.utdallas.mavs.divas.core.msg.RuntimeAgentCommandMsg.RuntimeAgentCommand;
import edu.utdallas.mavs.divas.core.sim.common.state.AgentControlType;
import edu.utdallas.mavs.divas.visualization.vis3D.dialog.customControls.voModification.AbstractPropertyDialogController;
import edu.utdallas.mavs.traffic.simulation.sim.common.state.VehicleAgentState;
import edu.utdallas.mavs.traffic.visualization.vis3D.vo.VehicleAgentVO;

/**
 * The VehicleModificationDialogController contains all the events that the VehicleModificationDialogDefinition element generates.
 */
public class VehicleModificationDialogController extends AbstractPropertyDialogController<VehicleAgentVO>
{
    @SuppressWarnings("unused")
    private final static Logger        logger = LoggerFactory.getLogger(VehicleModificationDialogController.class);

    private VehicleAgentState          agent;

    // Agent Properties
    private Element                    position;
    private Element                    velocity;
    private Element                    acceleration;
    private Element                    heading;
    private Element                    scale;
    private CheckBox                   cameraEnabled;

    private DropDown<AgentControlType> agentControlType;

    /**
     * Constructs a new agent properties dialog controller
     */
    public VehicleModificationDialogController()
    {}

    @Override
    public void init()
    {
        if(entityVO != null)
        {
            agent = (VehicleAgentState) entityVO.getState();
            super.init();
        }
    }

    @Override
    public void bindNiftyElements()
    {
        // Agent Properties
        setupLabels();
        setupCheckBoxes();
        setupDropDowns();
        setupSpinners();
    }

    private void setupLabels()
    {
        position = getElement(VehicleModificationDialogDefinition.POSITION_LABEL);
        velocity = getElement(VehicleModificationDialogDefinition.VELOCITY_LABEL);
        acceleration = getElement(VehicleModificationDialogDefinition.ACCELERATION_LABEL);
        heading = getElement(VehicleModificationDialogDefinition.HEADING_LABEL);
        scale = getElement(VehicleModificationDialogDefinition.SCALE_LABEL);
        getElement(VehicleModificationDialogDefinition.PLANNING_DETAILS_LABEL);
    }

    private void setupCheckBoxes()
    {
        cameraEnabled = getNiftyControl(VehicleModificationDialogDefinition.AGENT_CAM_CHECKBOX, CheckBox.class);
    }

    @SuppressWarnings("unchecked")
    private void setupDropDowns()
    {
        agentControlType = (DropDown<AgentControlType>) createDropDownControl("agentControlType", VehicleModificationDialogDefinition.AGENT_CONTROL_PANEL);
    }

    private void setupSpinners()
    {

    }

    @Override
    public void populatePanel()
    {
        // Populate dropdowns
        populateDropDown(agentControlType, AgentControlType.class);

        // Update agent properties
        updatePanel();

        // Select dropdown item
        agentControlType.selectItem(agent.getControlType());

        // Set checkbox controls
        cameraEnabled.setChecked(entityVO.isCamModeOn());
    }

    @Override
    public void subscriptions()
    {
        /*
         * Subscriptions for DropDown Controls
         */
        nifty.subscribe(screen, getAgentControlType().getId(), DropDownSelectionChangedEvent.class, new EventTopicSubscriber<DropDownSelectionChangedEvent<AgentControlType>>()
        {

            @Override
            public void onEvent(final String id, DropDownSelectionChangedEvent<AgentControlType> event)
            {
                simCommander.sendRuntimeAgentCommand(agent.getID(), RuntimeAgentCommand.SET_CONTROL_TYPE, event.getSelection().toString());
            }
        });

        /*
         * Subscriptions for CheckBox Controls
         */
        nifty.subscribe(screen, getCameraEnabled().getId(), CheckBoxStateChangedEvent.class, new EventTopicSubscriber<CheckBoxStateChangedEvent>()
        {
            @Override
            public void onEvent(final String id, final CheckBoxStateChangedEvent event)
            {
                if(event.getCheckBox().isChecked())
                {
                    // Detach free camera
                    entityVO.setCamMode(true);
                    app.dettachFreeCamera();
                }

                else if(!event.getCheckBox().isChecked())
                {
                    entityVO.setCamMode(false);
                    app.attachFreeCamera();
                }
            }
        });
    }

    @Override
    public void updatePanel()
    {
        agent = (VehicleAgentState) entityVO.getState();
        updatePosition();
        updateVelocity();
        updateAcceleration();
        updateHeading();
        updateScale();
        updatePlanningDetails();
    }

    private void updateHeading()
    {
        heading.getRenderer(TextRenderer.class).setText(String.format("(%.2f, %.2f, %.2f)", agent.getHeading().x, agent.getHeading().y, agent.getHeading().z));
    }

    private void updateAcceleration()
    {
        acceleration.getRenderer(TextRenderer.class).setText(String.format("(%.2f, %.2f, %.2f) m/s^2", agent.getAcceleration().x, agent.getAcceleration().y, agent.getAcceleration().z));
    }

    private void updateVelocity()
    {
        velocity.getRenderer(TextRenderer.class).setText(String.format("(%.2f, %.2f, %.2f) m/s", agent.getVelocity().x, agent.getVelocity().y, agent.getVelocity().z));
    }

    private void updatePosition()
    {
        position.getRenderer(TextRenderer.class).setText(String.format("(%.2f, %.2f, %.2f)", agent.getPosition().x, agent.getPosition().y, agent.getPosition().z));
    }

    private void updateScale()
    {
        scale.getRenderer(TextRenderer.class).setText(String.format("(%.2f, %.2f, %.2f)", agent.getScale().x, agent.getScale().y, agent.getScale().z));
    }

    private void updatePlanningDetails()
    {
        
    }

    /**
     * Gets the camera check box control of the nifty gui window.
     * 
     * @return the camera check box control.
     */
    public CheckBox getCameraEnabled()
    {
        return cameraEnabled;
    }

    private DropDown<AgentControlType> getAgentControlType()
    {
        return agentControlType;
    }

    @Override
    public void setEntity(VehicleAgentVO entity)
    {
        super.entityVO = entity;
    }
}
