import javax.swing.*;
import java.awt.event.*;

public class HendecagonalButton extends JButton {
    StateManager stateManager;

    public HendecagonalButton(StateManager stateManager) {
        super("正十一角形");

        addActionListener(new HendecagonalListener());

        this.stateManager = stateManager;
    }

    class HendecagonalListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new HendecagonalState(stateManager));
        }
    }
}
