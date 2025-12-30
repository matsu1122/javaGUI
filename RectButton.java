import javax.swing.*;
import java.awt.event.*;

public class RectButton extends JButton {
    StateManager stateManager;

    public RectButton(StateManager stateManager) {
        super("四角形");

        addActionListener(new RectListener());

        this.stateManager = stateManager;
    }

    class RectListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new RectState(stateManager));
        }
    }
}
