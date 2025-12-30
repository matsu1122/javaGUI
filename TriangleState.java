import java.awt.Color;

public class TriangleState extends State {
    MyDrawing d;

    public TriangleState(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    // mouseDragを移した瞬間三角形が描けなくなった
    // triangleは呼ばれてるみたい
    public void mouseDown(int x, int y) {
        d = new MyTriangle(x, y, 0, 0, Color.GRAY, Color.LIGHT_GRAY, stateManager.getShadow(), stateManager.getLineWidth(), stateManager.getDashLine());
        mouseDownFunc(x, y, d);
    }

    public void mouseDrag(int x, int y) {
        mouseDragFunc(x, y, d, false);
    }

    public void mouseUp(int x, int y) {
        mouseUpFunc(x, y, d);
    }

}
