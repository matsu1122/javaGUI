import java.awt.*;

public class MyRectangle extends MyDrawing {

    public MyRectangle() {
        super();
    }

    public MyRectangle(int xpt, int ypt) {
        super(xpt, ypt);
    }

    public MyRectangle(int xpt, int ypt, int w, int h) {
        super(xpt, ypt, w, h);
    }

    public MyRectangle(int xpt, int ypt, int w, int h, Color lineColor, Color fillColor) {
        super(xpt, ypt, w, h, lineColor, fillColor);
    }

    public MyRectangle(int xpt, int ypt, int w, int h, Color lineColor, Color fillColor, boolean shadow) {
        super(xpt, ypt, w, h, lineColor, fillColor, shadow);
    }

    public MyRectangle(int xpt, int ypt, int w, int h, Color lineColor, Color fillColor, boolean shadow, int lineWidth, boolean dashLine) {
        super(xpt, ypt, w, h, lineColor, fillColor, shadow, lineWidth, dashLine);
    }


    public void draw(Graphics g) {
        int x = getX();
        int y = getY();
        int w = getW();
        int h = getH();

        if(w < 0) {
            x += w;
            w *= -1;
        }
        if(h < 0) {
            y += h;
            h *= -1;
        }

        Graphics2D g2 = (Graphics2D) g;

        // Graphics2D を複製して、元の状態を守る
        Graphics2D g2Copy = (Graphics2D) g2.create();

        // 影の処理
        if(getShadow()) {
            int shiftX = x + getShadowShift();
            int shiftY = y + getShadowShift();
            g2Copy.setColor(Color.black);
            g2Copy.fillRect(shiftX, shiftY, w, h);
            g2Copy.setColor(Color.black);
            g2Copy.drawRect(shiftX, shiftY, w, h);
        }

        // 破線の幅をセット
        float[] dashPattern = new float[2];
        dashPattern[0] = 10.0f;
        if(getDashLine()) {
            dashPattern[1] = 10.0f;
        } else {
            dashPattern[1] = 0.0f;
        }

        // 破線の処理
        BasicStroke dashed = new BasicStroke(
            getLineWidth(),
            BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER,
            10.0f,
            dashPattern,
            0.0f
        );
        g2Copy.setStroke(dashed);

        g2Copy.setColor(getFillColor());
        g2Copy.fillRect(x, y, w, h);
        g2Copy.setColor(getLineColor());
        g2Copy.drawRect(x, y, w, h);

        // 使い終わったら開放（メモリ・リソース管理）
        g2Copy.dispose();

        super.draw(g);
    }

    public void setRegion() {
        int x = getX();
        int y = getY();
        int w = getW();
        int h = getH();

        region = new Rectangle(x, y, w, h);
    }

    public boolean contains(int x, int y) {
        return region.contains(x, y);
    }

}
