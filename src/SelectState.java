public class SelectState extends State {
    MyCanvas canvas;
    Mediator med;

    boolean dragFrag;
    boolean selectRangeFlag;

    public SelectState(StateManager stateManager) {
        this.stateManager = stateManager;
        canvas = stateManager.canvas;
        med = canvas.getMediator();
    }

    // マウスを押したときに図形がなかったら複数選択、図形があったら単一選択
    // MouseDragが呼ばれたかどうかでMouseUpでの処理を変える。MouseUpで選択する

    public void mouseDown(int x, int y) {
        dragFrag = false;
        selectRangeFlag = false;
        med.makeGroupRange(x, y);
        if(med.isMouseOnShape(x, y)) {
            if(med.isSelected()) {
                med.setGaps(x, y);
            }
        }
        else {
            System.out.println("notOnShape");
            med.initializeSelected();
        }
    }

    public void mouseDrag(int x, int y) {
        dragFrag = true;
        if(med.isSelected()) {
            med.move(x, y);
        }
        else {
            selectRangeFlag = true;
            med.selectRange(x, y);
        }
    }

    public void mouseUp(int x, int y) {
        if(dragFrag) {
            if(selectRangeFlag) med.multiSelected();
            else med.setRegionOfSelected();
        }
        else med.singleSelected(x, y);
        med.removeGroupRange();
    }
}
