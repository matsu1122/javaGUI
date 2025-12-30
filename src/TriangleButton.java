import javax.swing.*;
import java.awt.event.*;

public class TriangleButton extends JButton {
    StateManager stateManager;

    public TriangleButton(StateManager stateManager) {
        super("三角形");

        addActionListener(new TriangleListener());

        this.stateManager = stateManager;
    }

    class TriangleListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new TriangleState(stateManager));
        }
    }
}
