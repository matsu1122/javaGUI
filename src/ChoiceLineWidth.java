import java.awt.*;
import java.awt.event.*;

public class ChoiceLineWidth extends Panel implements ItemListener {
    Choice lineWidth;
    Label label;

    StateManager stateManager;

    public ChoiceLineWidth(StateManager stateManager) {
        this.stateManager = stateManager;

        setLayout(new FlowLayout());

        label = new Label("線の太さ");
        lineWidth = new Choice();

        lineWidth.addItemListener(this);

        lineWidth.add("1");
        lineWidth.add("3");
        lineWidth.add("5");

        add(label);
        add(lineWidth);

        // 初期値設定
        stateManager.setLineWidth(Integer.parseInt(lineWidth.getSelectedItem()));
    }

    public void itemStateChanged(ItemEvent e) {
        MyCanvas canvas = stateManager.getCanvas();
        Mediator med = canvas.getMediator();

        stateManager.setLineWidth(Integer.parseInt(lineWidth.getSelectedItem()));
        med.setLineWidth(Integer.parseInt(lineWidth.getSelectedItem()));
    }
}
