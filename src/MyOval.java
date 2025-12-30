import java.awt.*;
import java.awt.geom.Ellipse2D;

public class MyOval extends MyDrawing {
    public MyOval() {
        super();
    }

    public MyOval(int xpt, int ypt) {
        super(xpt, ypt);
    }

    public MyOval(int xpt, int ypt, int w, int h) {
        super(xpt, ypt, w, h);
    }

    public MyOval(int xpt, int ypt, int w, int h, Color lineColor, Color fillColor) {
        super(xpt, ypt, w, h, lineColor, fillColor);
    }

    public MyOval(int xpt, int ypt, int w, int h, Color lineColor, Color fillColor, boolean shadow) {
        super(xpt, ypt, w, h, lineColor, fillColor, shadow);
    }

    public MyOval(int xpt, int ypt, int w, int h, Color lineColor, Color fillColor, boolean shadow, int lineWidth, boolean dashLine) {
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

        Graphics2D g2Copy = (Graphics2D) g2.create();

        if(getShadow()) {
            int shiftX = x + getShadowShift();
            int shiftY = y + getShadowShift();
            g2Copy.setColor(Color.black);
            g2Copy.fillOval(shiftX, shiftY, w, h);
            g2Copy.setColor(Color.black);
            g2Copy.drawOval(shiftX, shiftY, w, h);
        }

        // 破線の幅をセット
        float[] dashPattern = new float[2];
        dashPattern[0] = 10.0f;
        if(getDashLine()) {
            dashPattern[1] = 10.0f;
        } else {
            dashPattern[1] = 0.0f;
        }

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
        g2Copy.fillOval(x, y, w, h);
        g2Copy.setColor(getLineColor());
        g2Copy.drawOval(x, y, w, h);

        g2Copy.dispose();

        super.draw(g);
    }

    public void setRegion() {
        int x = getX();
        int y = getY();
        int w = getW();
        int h = getH();

        region = new Ellipse2D.Double(x, y, w, h);
    }

    public boolean contains(int x, int y) {
        return region.contains(x, y);
    }
}
