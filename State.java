import java.awt.Color;

public abstract class State {
    StateManager stateManager;

    public abstract void mouseDown(int x, int y);
    public abstract void mouseUp(int x, int y);
    public abstract void mouseDrag(int x, int y);

    int startX;
    int startY;
    int minW = 1;
    int minH = 1;

    // 図形を描く際に使うメソッド
    public void mouseDownFunc(int x, int y, MyDrawing d) {
        Mediator med = this.stateManager.canvas.getMediator();
        med.initializeSelected();
        startX = x; startY = y;
        stateManager.addDrawing(d);
    }

    public void mouseDragFunc(int x, int y, MyDrawing d, boolean equilateral) {

        int fromStartX = x - startX;
        int fromStartY = y - startY;

        int w = Math.abs(fromStartX);
        int h = Math.abs(fromStartY);

        if(equilateral) {
            int size = Math.max(w, h);

            if(fromStartX>=0 && fromStartY>=0) {
                x = startX;
                y = startY;
            } else if(fromStartX>=0 && fromStartY<0) {
                x = startX;
                y = startY - size;
            } else if(fromStartX<0 && fromStartY<0) {
                x = startX - size;
                y = startY - size;
            } else if(fromStartX<0 && fromStartY>=0) {
                x = startX - size;
                y = startY;
            }

        } else {

            if(fromStartX>=0 && fromStartY>=0) {
                x = startX;
                y = startY;
            } else if(fromStartX>=0 && fromStartY<0) {
                x = startX;
            } else if(fromStartX<0 && fromStartY<0) {
                
            } else if(fromStartX<0 && fromStartY>=0) {
                y = startY;
            }

        }

        // インスタンスの変数のみを変更しrepaintをcanvasに対して実行する
        d.setLocation(x, y);
        d.setSize(w, h);
        stateManager.repaint();
    }

    public void mouseUpFunc(int x, int y, MyDrawing d) {
        if(d.getW() > minW && d.getH() > minH) {
            d.setFillColor(Color.white);
            d.setLineColor(Color.BLACK);
            stateManager.repaint();
            d.setRegion();
        }
    }
}
