import java.util.Vector;

public class KyukurarinState extends State {
    MyCanvas canvas;
    Mediator med;
    final int WIDE = 10;

    public KyukurarinState(StateManager stateManager) {
        this.stateManager = stateManager;
        canvas = stateManager.canvas;
        med = canvas.getMediator();
    }

    public void mouseDown(int x, int y) {
        med.singleSelected(x, y);
        Vector<MyDrawing> selecteDrawings = med.getSelectedDrawings();
        if(selecteDrawings.size() != 1) return;
        med.copy();
        int selectedX = selecteDrawings.get(0).getX();
        int selectedY = selecteDrawings.get(0).getY();
        int selectedW = selecteDrawings.get(0).getW();
        for(int i=0; i<5; i++) {
            med.paste(selectedX + Math.round((i+1)*selectedW/WIDE), selectedY);
        }
    }

    public void mouseDrag(int x, int y) {}

    public void mouseUp(int x, int y) {}

}
