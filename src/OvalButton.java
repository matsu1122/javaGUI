import javax.swing.*;
import java.awt.event.*;

public class OvalButton extends JButton {
    StateManager stateManager;

    public OvalButton(StateManager stateManager) {
        super("楕円");

        addActionListener(new OvalListener());

        this.stateManager = stateManager;
    }

    class OvalListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new OvalState(stateManager));
        }
    }
}
