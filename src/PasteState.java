public class PasteState extends State {
    MyCanvas canvas;
    Mediator mediator;

    public PasteState(StateManager stateManager) {
        this.stateManager = stateManager;
        canvas = stateManager.canvas;
        mediator = canvas.getMediator();
    }

    public void mouseDown(int x, int y) {
        mediator.paste(x, y);
    }

    public void mouseDrag(int x, int y) {}
    public void mouseUp(int x, int y) {}
}
