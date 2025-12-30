import java.awt.*;
import java.awt.event.*;

public class CheckboxShadow extends Panel implements ItemListener {
    Checkbox shadow;

    StateManager stateManager;

    public CheckboxShadow(StateManager stateManager) {
        this.stateManager = stateManager;

        setLayout(new FlowLayout());

        shadow = new Checkbox("影");

        shadow.addItemListener(this);

        add(shadow);

        // 初期値設定
        stateManager.setShadow(shadow.getState());
    }

    public void itemStateChanged(ItemEvent e) {
        MyCanvas canvas = stateManager.getCanvas();
        Mediator med = canvas.getMediator();

        stateManager.setShadow(shadow.getState());
        med.setShadow(shadow.getState());
    }
}
