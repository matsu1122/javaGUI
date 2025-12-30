import java.awt.*;
import java.awt.event.*;

public class CheckboxDashLine extends Panel implements ItemListener {
    Checkbox dashLine;

    StateManager stateManager;

    public CheckboxDashLine(StateManager stateManager) {
        this.stateManager = stateManager;

        setLayout(new FlowLayout());

        dashLine = new Checkbox("破線");

        dashLine.addItemListener(this);

        add(dashLine);

        // 初期値設定
        stateManager.setDashLine(dashLine.getState());
    }

    public void itemStateChanged(ItemEvent e) {
        MyCanvas canvas = stateManager.getCanvas();
        Mediator med = canvas.getMediator();

        stateManager.setDashLine(dashLine.getState());
        med.setDashLine(dashLine.getState());
    }
}
