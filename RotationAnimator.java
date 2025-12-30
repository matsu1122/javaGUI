import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList; // または Vector
import java.util.List;

// PaintPanelにインスタンス化して使用するアニメーター
public class RotationAnimator {
    private static final int ANIMATION_DELAY = 8; // 約60fps
    private final List<ImageShape> rotatingImages = new ArrayList<>();
    private final Timer timer;

    public RotationAnimator(javax.swing.JPanel panelToRepaint) {
        this.timer = new Timer(ANIMATION_DELAY, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (ImageShape image : rotatingImages) {
                    image.updateRotation(); // 各画像の回転角度を更新
                }
                panelToRepaint.repaint(); // パネルの再描画を要求
            }
        });
    }

    /**
     * 回転させたいImageShapeを登録し、アニメーションを開始します。
     * 登録した画像は自動的にisRotatingフラグがtrueになります。
     */

    public void RotateAnimate(ImageShape image) {
        if (!rotatingImages.contains(image)) {
            rotatingImages.add(image);
            image.startRotation();
            if (!timer.isRunning()) {
                timer.start();
            }
        }
        else if (rotatingImages.remove(image)) {
            image.stopRotation();
            if (rotatingImages.isEmpty() && timer.isRunning()) {
                timer.stop(); // 回転対象がなくなったらタイマーも停止
            }
        }


    }
}