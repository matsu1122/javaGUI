import javax.swing.*;
import java.awt.event.*;

public class PasteButton extends JButton {
    StateManager stateManager;

    public PasteButton(StateManager stateManager) {
        super("貼り付け");

        addActionListener(new RectListener());

        this.stateManager = stateManager;
    }

    class RectListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new PasteState(stateManager));
        }
    }
}
