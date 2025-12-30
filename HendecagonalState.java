import java.awt.Color;

public class HendecagonalState extends State {
    MyDrawing d;

    public HendecagonalState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void mouseDown(int x, int y) {
        d = new MyHendecagonal(x, y, 0, 0, Color.GRAY, Color.LIGHT_GRAY, stateManager.getShadow(), stateManager.getLineWidth(), stateManager.getDashLine());
        mouseDownFunc(x, y, d);
    }

    public void mouseDrag(int x, int y) {
        mouseDragFunc(x, y, d, true);
    }

    public void mouseUp(int x, int y) {
        mouseUpFunc(x, y, d);
    }

}
