import javax.swing.*;
import java.awt.event.*;

public class KyukurarinButton extends JButton {
    StateManager stateManager;

    public KyukurarinButton(StateManager stateManager) {
        super("きゅうくらりん");

        addActionListener(new KyukurarinListener());

        this.stateManager = stateManager;
    }

    class KyukurarinListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(new KyukurarinState(stateManager));
        }
    }
}
