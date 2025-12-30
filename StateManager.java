// import java.awt.*;

public class StateManager {
    MyCanvas canvas;
    State state;
    boolean panelFocus;

    boolean shadow;
    int lineWidth;
    boolean dashLine;
    // Color fillColor;

    public StateManager(MyCanvas canvas) {
        state = new RectState(this);
        // fillColor = Color.white;
        this.canvas = canvas;
    }

    public void addDrawing(MyDrawing d) {
        Mediator mediator = this.canvas.getMediator();
        // fillColor = mediator.getFillColor();
        mediator.addDrawing(d);
    }

    public void repaint() {
        this.canvas.repaint();
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setDashLine(boolean dashLine) {
        this.dashLine = dashLine;
    }

    public State getState() {
        return state;
    }

    public boolean getShadow() {
        return shadow;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public boolean getDashLine() {
        return dashLine;
    }

    // public Color getFillColor() {
    //     return fillColor;
    // }

    public MyCanvas getCanvas() {
        return canvas;
    }

}

/*
四角形を描くのか楕円を描くのか一括で管理したい
State型を与えておくことでその変数にRectStateやOvalStateなどを与える
Stateの関数をオーバーライドしてRectStateなどで定義する
Stateの関数は必要なくなるので抽象化(abstract)して書き間違いなどをなくす
 */

 /*
RectStateはStateの機能的な上位互換。
ただ概念的にはStateの方が広い概念。
生徒(State/Student)の中の頭が良い生徒(RectState/SuperStudent)みたいな感じ

State rectState = new RectState(this);
rectStateの中に何が入ってるとかはあまり考えない。
これはStateが広い概念なのでRectStateの機能が入る。
つまり、RectStateの関数が使える
  */
