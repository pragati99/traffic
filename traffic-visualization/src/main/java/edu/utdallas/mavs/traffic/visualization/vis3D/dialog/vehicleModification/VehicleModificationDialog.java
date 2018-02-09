package edu.utdallas.mavs.traffic.visualization.vis3D.dialog.vehicleModification;

import java.util.HashMap;
import java.util.Map;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.elements.Element;
import edu.utdallas.mavs.divas.visualization.vis3D.dialog.customControls.voModification.AbstractPropertyDialog;
import edu.utdallas.mavs.traffic.visualization.vis3D.vo.VehicleAgentVO;

/**
 * This class describes a property dialog for a vehicle agent.
 */
public class VehicleModificationDialog extends AbstractPropertyDialog<VehicleAgentVO, VehicleModificationDialogController>
{
    /**
     * The {@link VehicleModificationDialog} constructor.
     * 
     * @param parentElement
     *        The parent element for the dialog.
     */
    public VehicleModificationDialog(Element parentElement)
    {
        super(parentElement);
    }

    @Override
    public boolean isContextSelected(Object object)
    {
        if(object instanceof VehicleAgentVO)
        {
            return ((VehicleAgentVO) object).isContextSelected();
        }
        else
        {
            return false;
        }
    }

    @Override
    public String getWidth()
    {
        return "275px";
    }

    @Override
    public String getHeight()
    {
        return "310px";
    }

    @Override
    public String getAlignment()
    {
        return "center";
    }

    @Override
    public Map<String, String> getParameters()
    {
        return new HashMap<String, String>();
    }

    @Override
    public Class<VehicleModificationDialogDefinition> getDefinitionClass()
    {
        return VehicleModificationDialogDefinition.class;
    }

    @Override
    public Class<VehicleModificationDialogController> getControllerClass()
    {
        return VehicleModificationDialogController.class;
    }

    @Override
    public void registerNiftyDefinition(Nifty nifty)
    {
        VehicleModificationDialogDefinition.register(nifty);
    }

    @Override
    public void updateDialog()
    {
        if(content != null)
        {
            try
            {
                content.getControl(getControllerClass()).updatePanel();
            }
            catch(Exception e)
            {
                entity.setContextSelected(false);
                super.removeDialog();
            }
        }
    }

    @Override
    public String getControllerName()
    {
        return (new VehicleModificationDialogController()).getClass().getName();
    }

    @Override
    public String getPositionX()
    {
        return "40";
    }

    @Override
    public String getPositionY()
    {
        return "40";
    }

    @Override
    public String getContentHeight()
    {
        return "290px";
    }

    @Override
    public String getContentWidth()
    {
        return "100%";
    }

    @Override
    public String createDialogId(String id)
    {
        return String.format("#VehiclePropertyWindow%s", id);
    }

    @Override
    public String createDialogTitle(String id)
    {
        return String.format("%s %s", "Agent", id);
    }

    @Override
    public String getEntityId(Object entity)
    {
        return String.valueOf(((VehicleAgentVO) entity).getState().getID());
    }

    @Override
    public void hideDialog()
    {
        super.showDialog();
    }
}