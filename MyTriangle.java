import java.awt.*;

public class MyTriangle extends MyDrawing {
    public MyTriangle() {
        super();
    }

    public MyTriangle(int xpt, int ypt) {
        super(xpt, ypt);
    }

    public MyTriangle(int xpt, int ypt, int w, int h) {
        super(xpt, ypt, w, h);
    }

    public MyTriangle(int xpt, int ypt, int w, int h, Color lineColor, Color fillColor, boolean shadow) {
        super(xpt, ypt, w, h, lineColor, fillColor, shadow);
    }

    public MyTriangle(int xpt, int ypt, int w, int h, Color lineColor, Color fillColor, boolean shadow, int lineWidth, boolean dashLine) {
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

        int[] xpoints = new int[3];
        int[] ypoints = new int[3];

        // 点の座標指定(左下、右下、中央上)
        xpoints[0] = x;                     ypoints[0] = y + h;
        xpoints[1] = x + w;                 ypoints[1] = y + h;
        xpoints[2] = x + Math.round(w / 2); ypoints[2] = y;

        Graphics2D g2 = (Graphics2D) g;

        Graphics2D g2Copy = (Graphics2D) g2.create();

        if(getShadow()) {
            int[] shiftXpoints = new int[3];
            int[] shiftYpoints = new int[3];

            for(int i=0; i < 3; i++) {
                shiftXpoints[i] = xpoints[i] + getShadowShift();
                shiftYpoints[i] = ypoints[i] + getShadowShift();
            }
            g2Copy.setColor(Color.black);
            g2Copy.fillPolygon(shiftXpoints, shiftYpoints, 3);
            g2Copy.setColor(Color.black);
            g2Copy.drawPolygon(shiftXpoints, shiftYpoints, 3);
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
        g2Copy.fillPolygon(xpoints, ypoints, 3);
        g2Copy.setColor(getLineColor());
        g2Copy.drawPolygon(xpoints, ypoints, 3);

        g2Copy.dispose();

        super.draw(g);
    }

    public void setRegion() {
        int x = getX();
        int y = getY();
        int w = getW();
        int h = getH();

        int[] xpoints = new int[3];
        int[] ypoints = new int[3];

        // 点の座標指定(左下、右下、中央上)
        xpoints[0] = x;                     ypoints[0] = y + h;
        xpoints[1] = x + w;                 ypoints[1] = y + h;
        xpoints[2] = x + Math.round(w / 2); ypoints[2] = y;

        region = new Polygon(xpoints, ypoints, 3);
    }

    public boolean contains(int x, int y) {
        return region.contains(x, y);
    }
}