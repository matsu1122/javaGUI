import javax.swing.*;
import java.awt.event.*;

public class RotateButton extends JButton {
    StateManager stateManager;

    public RotateButton(StateManager stateManager) {
        super("テトリス");

        addActionListener(new RotateStartListener());

        this.stateManager = stateManager;
    }

    class RotateStartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new RotateState(stateManager));
        }
    }

}
