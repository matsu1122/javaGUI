import java.awt.*;

public class MyHendecagonal extends MyDrawing {
    final int POINTNUM = 11;

    public MyHendecagonal() {
        super();
    }

    public MyHendecagonal(int xpt, int ypt) {
        super(xpt, ypt);
    }

    public MyHendecagonal(int xpt, int ypt, int w, int h) {
        super(xpt, ypt, w, h);
    }

    public MyHendecagonal(int xpt, int ypt, int w, int h, Color lineColor, Color fillColor) {
        super(xpt, ypt, w, h, lineColor, fillColor);
    }

    public MyHendecagonal(int xpt, int ypt, int w, int h, Color lineColor, Color fillColor, boolean shadow) {
        super(xpt, ypt, w, h, lineColor, fillColor, shadow);
    }

    public MyHendecagonal(int xpt, int ypt, int w, int h, Color lineColor, Color fillColor, boolean shadow, int lineWidth, boolean dashLine) {
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

        int size = Math.max(w, h);

        int[] xpoints = new int[POINTNUM];
        int[] ypoints = new int[POINTNUM];
        double xpoint = 0.0;
        double ypoint = 0.0;

        for(int i=0; i<POINTNUM; i++) {
            double rad = Math.PI * 2 * i / POINTNUM + Math.PI / 2;
            xpoint = Math.cos(rad) * size / 2 + x + size / 2;
            ypoint = Math.sin(rad) * size / 2 + y + size / 2;
            xpoints[i] = (int)Math.round(xpoint);
            ypoints[i] = (int)Math.round(ypoint);
        }

        // 三角関数を使って点を回転させる
        Graphics2D g2 = (Graphics2D) g;

        Graphics2D g2Copy = (Graphics2D) g2.create();

        if(getShadow()) {
            int[] shiftXpoints = new int[POINTNUM];
            int[] shiftYpoints = new int[POINTNUM];

            for(int i=0; i < POINTNUM; i++) {
                shiftXpoints[i] = xpoints[i] + getShadowShift();
                shiftYpoints[i] = ypoints[i] + getShadowShift();
            }
            g2Copy.setColor(Color.black);
            g2Copy.fillPolygon(shiftXpoints, shiftYpoints, POINTNUM);
            g2Copy.setColor(Color.black);
            g2Copy.drawPolygon(shiftXpoints, shiftYpoints, POINTNUM);
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
        g2Copy.fillPolygon(xpoints, ypoints, POINTNUM);
        g2Copy.setColor(getLineColor());
        g2Copy.drawPolygon(xpoints, ypoints, POINTNUM);

        g2Copy.dispose();

        setSize(size, size);
        super.draw(g);
    }

    public void setRegion() {
        int x = getX();
        int y = getY();
        int w = getW();
        int h = getH();

        int size = Math.max(w, h);
        int[] xpoints = new int[POINTNUM];
        int[] ypoints = new int[POINTNUM];
        double xpoint = 0.0;
        double ypoint = 0.0;

        for(int i=0; i<POINTNUM; i++) {
            double rad = Math.PI * 2 * i / POINTNUM + Math.PI / 2;
            xpoint = Math.cos(rad) * size / 2 + x + size / 2;
            ypoint = Math.sin(rad) * size / 2 + y + size / 2;
            xpoints[i] = (int)Math.round(xpoint);
            ypoints[i] = (int)Math.round(ypoint);
        }


        region = new Polygon(xpoints, ypoints, POINTNUM);
    }

    public boolean contains(int x, int y) {
        return region.contains(x, y);
    }

}

// memo
// コンストラクタ MyHendecagonal() の引数を変えることによってどのコンストラクタを呼び出すか決められる
// 引数を二個与えたなら引数二個のコンストラクタが呼び出される