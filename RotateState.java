import java.util.Vector;

public class RotateState extends State {
    MyCanvas canvas;
    Mediator med;
    final int WIDE = 10;

    public RotateState(StateManager stateManager) {
        this.stateManager = stateManager;
        canvas = stateManager.canvas;
        med = canvas.getMediator();
    }

    public void mouseDown(int x, int y) {
        
        MyDrawing d = med.DrawingOverlapMouse(x, y);
        if(d == null) return;
        if(d instanceof ImageShape) {
            ImageShape image = (ImageShape) d;
            med.RotateAnimate(image);
        }
        
        // med.RotateStart("test_image.jpg");
    }

    public void mouseDrag(int x, int y) {}

    public void mouseUp(int x, int y) {}

}
