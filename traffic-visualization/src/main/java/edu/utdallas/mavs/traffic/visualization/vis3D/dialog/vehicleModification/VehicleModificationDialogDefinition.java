package edu.utdallas.mavs.traffic.visualization.vis3D.dialog.vehicleModification;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ControlDefinitionBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.DefaultController;
import de.lessvoid.nifty.controls.checkbox.builder.CheckboxBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.tools.Color;
import edu.utdallas.mavs.divas.visualization.vis3D.dialog.utils.CommonBuilders;

/**
 * The {@link VehicleModificationDialogDefinition} registers a new control with Nifty that
 * represents the whole {@link VehicleModificationDialogDefinition}. This gives us later an
 * appropriate ControlBuilder to actual construct the Dialog (as a control) with
 * the given NAME.
 */
public class VehicleModificationDialogDefinition
{
    /**
     * The name of the control {@link AgentPropertyDialogDefinition}.
     */
    public static final String    NAME                    = VehicleModificationDialogDefinition.class.getName();

    /**
     * The id for the label containing the heading of the agent.
     */
    public static final String    HEADING_LABEL           = "#heading";

    /**
     * The id for the label containing the acceleration of the agent.
     */
    public static final String    ACCELERATION_LABEL      = "#acceleration";

    /**
     * The id for the label containing the velocity of the agent.
     */
    public static final String    VELOCITY_LABEL          = "#velocity";

    /**
     * The id for the label containing the position of the agent.
     */
    public static final String    POSITION_LABEL          = "#position";

    /**
     * The id for the label containing the scale of the agent.
     */
    public static final String    SCALE_LABEL             = "#scale";

    /**
     * The id for the label containing planning details of the agent.
     */
    public static final String    PLANNING_DETAILS_LABEL  = "#planningDetails";

    /**
     * The id of the panel for the agent control (i.e. autonomous, keyboard) of the agent.
     */
    public static final String    AGENT_CONTROL_PANEL     = "#agentControlPanel";

    /**
     * The id for the checkbox containing whether the agent camera is enabled or not.
     */
    public static final String    AGENT_CAM_CHECKBOX      = "#agentCamEnabled";

    /**
     * The id for the textfield containing the maximum speed of the agent.
     */
    public static final String    MAX_SPEED_TEXTFIELD     = "#maxSpeed";

    /**
     * The id for the panel containing the maximum speed of the agent.
     */
    public static final String    MAX_SPEED_PANEL         = "#maxSpeedPanel";

    /**
     * The id for the textfield containing the desired speed of the agent.
     */
    public static final String    DESIRED_SPEED_TEXTFIELD = "#desiredSpeed";

    /**
     * The id for the panel containing the desired speed of the agent.
     */
    public static final String    DESIRED_SPEED_PANEL     = "#desiredSpeedPanel";

    private static CommonBuilders builders                = new CommonBuilders();

    /**
     * This registers the dialog as a new ControlDefintion with Nifty so that we can
     * later create the dialog dynamically.
     * 
     * @param nifty
     *        The Nifty instance
     */
    public static void register(final Nifty nifty)
    {
        new ControlDefinitionBuilder(NAME)
        {
            {
                controller(new DefaultController());
                panel(new PanelBuilder()
                {
                    {
                        padding("5px,20px,0px,19px"); // top, right, bottom, left
                        backgroundColor(new Color(0.0f, 0.0f, 0.0f, 0.4f));
                        // width("100%");
                        childLayoutVertical();

                        panel(new PanelBuilder()
                        {
                            {
                                childLayoutVertical();

                                panel(new PanelBuilder()
                                {
                                    {
                                        childLayoutHorizontal();
                                        control(builders.createLabel("Position: ", "80px"));
                                        panel(builders.hspacer("0px"));
                                        control(new LabelBuilder(POSITION_LABEL)
                                        {
                                            {
                                                width("*");
                                                alignLeft();
                                                textVAlignCenter();
                                                textHAlignLeft();
                                            }
                                        });
                                    }
                                });
                                panel(builders.vspacer());
                                panel(new PanelBuilder()
                                {
                                    {
                                        childLayoutHorizontal();
                                        control(builders.createLabel("Velocity: ", "80px"));
                                        panel(builders.hspacer("0px"));
                                        control(new LabelBuilder(VELOCITY_LABEL)
                                        {
                                            {
                                                width("*");
                                                alignLeft();
                                                textVAlignCenter();
                                                textHAlignLeft();
                                            }
                                        });
                                    }
                                });
                                panel(builders.vspacer());
                                panel(new PanelBuilder()
                                {
                                    {
                                        childLayoutHorizontal();
                                        control(builders.createLabel("Acceleration: ", "80px"));
                                        panel(builders.hspacer("0px"));
                                        control(new LabelBuilder(ACCELERATION_LABEL)
                                        {
                                            {
                                                width("*");
                                                alignLeft();
                                                textVAlignCenter();
                                                textHAlignLeft();
                                            }
                                        });
                                    }
                                });
                                panel(builders.vspacer());
                                panel(new PanelBuilder()
                                {
                                    {
                                        childLayoutHorizontal();
                                        control(builders.createLabel("Heading: ", "80px"));
                                        panel(builders.hspacer("0px"));
                                        control(new LabelBuilder(HEADING_LABEL)
                                        {
                                            {
                                                width("*");
                                                alignLeft();
                                                textVAlignCenter();
                                                textHAlignLeft();
                                            }
                                        });
                                    }
                                });
                                panel(builders.vspacer());
                                panel(new PanelBuilder()
                                {
                                    {
                                        childLayoutHorizontal();
                                        control(builders.createLabel("Scale: ", "80px"));
                                        panel(builders.hspacer("0px"));
                                        control(new LabelBuilder(SCALE_LABEL)
                                        {
                                            {
                                                width("*");
                                                alignLeft();
                                                textVAlignCenter();
                                                textHAlignLeft();
                                            }
                                        });
                                    }
                                });
                                panel(builders.vspacer());
                                panel(new PanelBuilder(DESIRED_SPEED_PANEL)
                                {
                                    {
                                        childLayoutHorizontal();

                                    }
                                });
                                panel(builders.vspacer());
                                panel(new PanelBuilder(MAX_SPEED_PANEL)
                                {
                                    {
                                        childLayoutHorizontal();
                                    }
                                });
                            }
                        });

                        panel(builders.vspacer("5px"));

                        panel(new PanelBuilder()
                        {
                            {
                                childLayoutVertical();
                                panel(builders.vspacer());
                                panel(new PanelBuilder(AGENT_CONTROL_PANEL)
                                {
                                    {
                                        childLayoutHorizontal();
                                    }
                                });
                            }
                        });

                        panel(builders.vspacer("5px"));

                        panel(new PanelBuilder()
                        {
                            {
                                childLayoutVertical();
                                panel(builders.vspacer());
                                panel(new PanelBuilder()
                                {
                                    {
                                        childLayoutHorizontal();
                                        control(builders.createLabel("Agent Camera"));
                                        panel(builders.hspacer("10px"));
                                        control(new CheckboxBuilder(AGENT_CAM_CHECKBOX)
                                        {
                                            {

                                            }
                                        });
                                    }
                                });
                                panel(builders.vspacer());
                            }
                        });
                        panel(builders.vspacer("5px"));

                        panel(new PanelBuilder()
                        {
                            {
                                childLayoutHorizontal();
                                control(builders.createLabel("Planning information: ", "120px"));
                                panel(builders.hspacer("0px"));
                                control(new LabelBuilder(PLANNING_DETAILS_LABEL)
                                {
                                    {
                                        width("*");
                                        alignLeft();
                                        textVAlignCenter();
                                        textHAlignLeft();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        }.registerControlDefintion(nifty);
    }
}