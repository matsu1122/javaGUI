import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

// 画像を表すクラス
public class ImageShape extends MyDrawing {
    private transient Image image; // Imageオブジェクトは直接直列化できないためtransientにする
    private String imagePath; // 画像のパスを保存して、ロード時に再読み込みする
    private double rotationAngle = 0; // 回転角度を管理するフィールド
    private boolean isRotating = false; // 回転させるかどうかを制御するフラグ

    public ImageShape(Image image, String imagePath, int x, int y) {
        this.image = image;
        this.imagePath = imagePath;
        setLocation(x, y);
    }

    // デシリアライズ時に画像を再読み込みするためのコンストラクタ
    public ImageShape(String imagePath, int x, int y) {
        this.imagePath = imagePath;
        setLocation(x, y);
        loadImage(); // コンストラクタで画像をロード
    }

    @Override
    public void draw(Graphics g) {
        if (image != null) {
            Graphics2D g2 = (Graphics2D) g;
            Graphics2D g2Copy = (Graphics2D) g2.create();
            // g2.drawImage(image, getX(), getY(), null);
            // super.draw(g2Copy);
            if (isRotating) {
                // 角度に応じて画像の幅を縮小（コサイン関数を使用）
                double cos = Math.cos(Math.toRadians(rotationAngle));
                int newWidth = (int) (image.getWidth(null) * Math.abs(cos));
                int newX = getX() + (image.getWidth(null) - newWidth) / 2;

                if (cos > 0) { // 表面
                    g2.drawImage(image, newX, getY(), newWidth, image.getHeight(null), null);
                } else { // 裏面
                    // 裏面を左右反転して描画
                    g2.drawImage(image, newX + newWidth, getY(), -newWidth, image.getHeight(null), null);
                }
            } else {
                // 回転しない場合、通常の描画
                g2.drawImage(image, getX(), getY(), null);
            }
            super.draw(g2Copy);
        }
    }

    // 画像ファイルを読み込むヘルパーメソッド
    private void loadImage() {
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                // ファイルパスから画像を読み込む
                this.image = javax.imageio.ImageIO.read(new java.io.File(imagePath));
            } catch (IOException e) {
                System.err.println("画像の読み込みに失敗しました: " + imagePath);
                e.printStackTrace();
                this.image = null; // 読み込み失敗時はnullにする
            }
        }
    }

    // カスタム直列化 (書き込み)
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject(); // デフォルトの直列化処理
        // imageはtransientなので書き込まれない。imagePathは自動的に書き込まれる。
    }

    // カスタム直列化解除 (読み込み)
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject(); // デフォルトの直列化解除処理
        // imagePathが読み込まれた後、画像を再読み込みする
        loadImage();
    }

    // --- 回転を開始・停止するためのメソッド ---
    public void startRotation() {
        this.isRotating = true;
    }

    public void stopRotation() {
        this.isRotating = false;
    }

    // --- PaintPanelから呼び出されるアニメーション更新用メソッド ---
    public void updateRotation() {
        if (isRotating) {
            this.rotationAngle = (this.rotationAngle + 2) % 360;
        }
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
