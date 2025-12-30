import java.awt.Color;

public class RectState extends State {
    MyDrawing d;

    public RectState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void mouseDown(int x, int y) {
        d = new MyRectangle(x, y, 0, 0, Color.GRAY, Color.LIGHT_GRAY, stateManager.getShadow(), stateManager.getLineWidth(), stateManager.getDashLine());
        mouseDownFunc(x, y, d);
    }

    public void mouseDrag(int x, int y) {
        mouseDragFunc(x, y, d, false);
    }

    public void mouseUp(int x, int y) {
        mouseUpFunc(x, y, d);
    }
}
