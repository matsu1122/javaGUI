import java.awt.*;
import java.io.*;

public class MyDrawing implements Cloneable, Serializable {
    private int x, y, w, h;
    private Color lineColor, fillColor;
    private int lineWidth;
    private boolean dashLine;
    private boolean shadow;
    final int SHADOWSHIFT = 5;

    boolean isSelected;
    Shape region;   //包含判定用
    final int SIZE = 7; //選択表示矩形に付く四角形の大きさ

    public MyDrawing() {
        this(0, 0);
    }

    public MyDrawing(int xpt, int ypt) {
        this(xpt, ypt, 40, 40);
    }

    public MyDrawing(int xpt, int ypt, int w, int h) {
        this(xpt, ypt, w, h, Color.black, Color.white);
    }

    public MyDrawing(int xpt, int ypt, int w, int h, Color lineColor, Color fillColor) {
        this(xpt, ypt, w, h, lineColor, fillColor, false);
    }

    public MyDrawing(int xpt, int ypt, int w, int h, Color lineColor, Color fillColor, boolean shadow) {
        this(xpt, ypt, w, h, lineColor, fillColor, shadow, 1, false);
    }

    public MyDrawing(int xpt, int ypt, int w, int h, Color lineColor, Color fillColor, boolean shadow, int lineWidth, boolean dashLine) {
        setLocation(xpt, ypt);
        setSize(w, h);
        setLineColor(lineColor);
        setFillColor(fillColor);
        setLineWidth(lineWidth);
        setShadow(shadow);
        setDashLine(dashLine);
        setRegion();
    }


    public void draw(Graphics g) {
        if(isSelected) {
            // 選択されたのがわかりやすいような図形
            g.setColor(Color.black);
            g.fillRect(x+w/2 - SIZE/2, y - SIZE/2, SIZE, SIZE);
            g.fillRect(x - SIZE/2, y+h/2 - SIZE/2, SIZE, SIZE);
            g.fillRect(x+w/2 - SIZE/2, y+h - SIZE/2, SIZE, SIZE);
            g.fillRect(x+w - SIZE/2, y+h/2 - SIZE/2, SIZE, SIZE);
            g.fillRect(x - SIZE/2, y - SIZE/2, SIZE, SIZE);
            g.fillRect(x+w - SIZE/2, y - SIZE/2, SIZE, SIZE);
            g.fillRect(x - SIZE/2, y+h - SIZE/2, SIZE, SIZE);
            g.fillRect(x+w - SIZE/2, y+h - SIZE/2, SIZE, SIZE);
            g.drawRect(x, y, w, h);
        }
    }

    public boolean contains(int x, int y) {
        // 子クラスでオーバーライドする
        System.out.println("オーバーライドしてないよ！");
        return true;
    }

    @Override
    public MyDrawing clone() {
        MyDrawing d = null;

        try {
            d = (MyDrawing) super.clone();
        } catch(Exception e) {
            d = null;
        }

        return d;
    }

    public void setRegion() {}

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public void setDashLine(boolean dashLine) {
        this.dashLine = dashLine;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public boolean getDashLine() {
        return dashLine;
    }

    public boolean getShadow() {
        return shadow;
    }

    public int getShadowShift() {
        return SHADOWSHIFT;
    }

    public boolean getSelected() {
        return isSelected;
    }
}
