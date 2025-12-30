import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.util.Vector;

// 画像を貼り付けるために入れた
import java.io.IOException;
import javax.imageio.ImageIO;

public class MyApplication extends JFrame implements ActionListener {
    private JMenuBar menuBar;
    private JMenu fillColorMenu, lineColorMenu, fileMenu, imageMenu;
    private JMenuItem fillRedItem, fillBlueItem, fillGreenItem, fillWhiteItem, fillOtherItem;
    private JMenuItem lineRedItem, lineBlueItem, lineGreenItem, lineWhiteItem, lineOtherItem;
    private JMenuItem saveItem, loadItem;
    private JMenuItem importItem;
    // private JLabel colorTextLabel;
    private JPanel colorPanel;

    StateManager stateManager;
    MyCanvas canvas;
    Mediator med;

    public MyApplication() {
        super("My Paint Application");

        canvas = new MyCanvas();
        canvas.setBackground(Color.white);

        med = canvas.getMediator();

        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout());

        stateManager = new StateManager(canvas);

        // 各ボタンの表示
        RectButton rectButton = new RectButton(stateManager);
        jp.add(rectButton);
        OvalButton ovalButton = new OvalButton(stateManager);
        jp.add(ovalButton);
        TriangleButton triangleButton = new TriangleButton(stateManager);
        jp.add(triangleButton);
        HendecagonalButton hendecagonalButton = new HendecagonalButton(stateManager);
        jp.add(hendecagonalButton);
        SelectButton selectButton = new SelectButton(stateManager);
        jp.add(selectButton);
        PasteButton pasteButton = new PasteButton(stateManager);
        jp.add(pasteButton);
        KyukurarinButton kyukurarinButton = new KyukurarinButton(stateManager);
        jp.add(kyukurarinButton);
        RotateButton rotateButton = new RotateButton(stateManager);
        jp.add(rotateButton);


        // チェックボックスの表示
        CheckboxShadow shadow = new CheckboxShadow(stateManager);
        jp.add(shadow);
        CheckboxDashLine dashLine = new CheckboxDashLine(stateManager);
        jp.add(dashLine);

        // チョイスの表示
        ChoiceLineWidth lineWidth = new ChoiceLineWidth(stateManager);
        jp.add(lineWidth);

        // 色選択メニューの表示
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        fillColorMenu = new JMenu("図形を塗りつぶす");
        fillRedItem = new JMenuItem("赤");
        fillBlueItem = new JMenuItem("青");
        fillGreenItem = new JMenuItem("緑");
        fillWhiteItem = new JMenuItem("白");
        fillOtherItem = new JMenuItem("他の色を選ぶ");
        fillColorMenu.add(fillRedItem);
        fillColorMenu.add(fillBlueItem);
        fillColorMenu.add(fillGreenItem);
        fillColorMenu.add(fillWhiteItem);
        fillColorMenu.add(fillOtherItem);
        fillRedItem.setActionCommand("fill:RED");
        fillBlueItem.setActionCommand("fill:BLUE");
        fillGreenItem.setActionCommand("fill:GREEN");
        fillWhiteItem.setActionCommand("fill:WHITE");
        fillOtherItem.setActionCommand("fill:OTHER");
        fillRedItem.addActionListener(this);
        fillBlueItem.addActionListener(this);
        fillGreenItem.addActionListener(this);
        fillWhiteItem.addActionListener(this);
        fillOtherItem.addActionListener(this);
        menuBar.add(fillColorMenu);

        lineColorMenu = new JMenu("線の色を変更");
        lineRedItem = new JMenuItem("赤");
        lineBlueItem = new JMenuItem("青");
        lineGreenItem = new JMenuItem("緑");
        lineWhiteItem = new JMenuItem("白");
        lineOtherItem = new JMenuItem("他の色を選ぶ");
        lineColorMenu.add(lineRedItem);
        lineColorMenu.add(lineBlueItem);
        lineColorMenu.add(lineGreenItem);
        lineColorMenu.add(lineWhiteItem);
        lineColorMenu.add(lineOtherItem);
        lineRedItem.setActionCommand("line:RED");
        lineBlueItem.setActionCommand("line:BLUE");
        lineGreenItem.setActionCommand("line:GREEN");
        lineWhiteItem.setActionCommand("line:WHITE");
        lineOtherItem.setActionCommand("line:OTHER");
        lineRedItem.addActionListener(this);
        lineBlueItem.addActionListener(this);
        lineGreenItem.addActionListener(this);
        lineWhiteItem.addActionListener(this);
        lineOtherItem.addActionListener(this);
        menuBar.add(lineColorMenu);

        fileMenu = new JMenu("ファイル");
        saveItem = new JMenuItem("セーブ");
        loadItem = new JMenuItem("ロード");
        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        saveItem.setActionCommand("file:SAVE");
        loadItem.setActionCommand("file:LOAD");
        saveItem.addActionListener(this);
        loadItem.addActionListener(this);
        menuBar.add(fileMenu);

        imageMenu = new JMenu("画像");
        importItem = new JMenuItem("画像の挿入");
        imageMenu.add(importItem);
        importItem.setActionCommand("image:IMPORT");
        importItem.addActionListener(this);
        menuBar.add(imageMenu);


        // 線の色も加えたことで改変が必要
        // colorTextLabel = new JLabel("■");
        // colorTextLabel.setForeground(Color.white);
        // jp.add(colorTextLabel);

        colorPanel = new JPanel();
        colorPanel.setPreferredSize(new Dimension(100, 100));
        colorPanel.setBackground(Color.LIGHT_GRAY);

        // キー操作の追加
        canvas.setFocusable(true);  // 必要？
        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Vector<MyDrawing> drawings = med.getSelectedDrawings();

                if(e.getKeyCode() == KeyEvent.VK_DELETE) {
                    for(int i=0; i<drawings.size(); i++) med.removeDrawing(drawings.get(i));
                    med.repaint();
                    System.out.println("delete");
                }

                else if(e.getKeyCode() == KeyEvent.VK_C) {
                    med.copy();
                    System.out.println("C");
                }

                else if(e.getKeyCode() == KeyEvent.VK_X) {
                    med.cut();
                    System.out.println("X");
                }
            }
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jp, BorderLayout.NORTH);
        getContentPane().add(canvas, BorderLayout.CENTER);

        canvas.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                stateManager.state.mouseDown(e.getX(), e.getY());
                canvas.requestFocusInWindow();
            }

            public void mouseReleased(MouseEvent e) {
                stateManager.state.mouseUp(e.getX(), e.getY());
                canvas.requestFocusInWindow();
            }
        });

        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                stateManager.state.mouseDrag(e.getX(), e.getY());
            }
        });

        // windowを閉じたらプログラム終了
        this.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            }
        );
    }

    public Dimension getPreferredSize() {
        return new Dimension(1000, 400);
    }

    public void stateChanged(ChangeEvent e) {}

    // 選択メニューの動きを監視
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        // 画像の挿入
        if(cmd.startsWith("image:")) {
            if(cmd.endsWith("IMPORT")) {
                JFileChooser fileChooser = new JFileChooser();
                // 画像ファイルのみをフィルタリングする場合 (オプション)
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "画像ファイル (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif"));

                int option = fileChooser.showOpenDialog(MyApplication.this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        Image image = ImageIO.read(selectedFile);
                        if (image != null) {
                            // 画像の描画開始位置をユーザーに選ばせるか、デフォルト値を設定
                            // 例: パネルの中央に配置
                            // int x = (paintPanel.getWidth() - image.getWidth(null)) / 2;
                            // int y = (paintPanel.getHeight() - image.getHeight(null)) / 2;
                            // if (x < 0) x = 0; // パネルより画像が大きい場合
                            // if (y < 0) y = 0;

                            // とりあえず(0,0)に画像を表示
                            int x = 0;
                            int y = 0;
                            int w = image.getWidth(null);
                            int h = image.getHeight(null);

                            med.addImage(image, selectedFile.getAbsolutePath(), x, y, w, h);
                        } else {
                            JOptionPane.showMessageDialog(MyApplication.this,
                                "選択されたファイルは画像として読み込めませんでした。",
                                "画像読み込みエラー", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(MyApplication.this,
                            "画像の読み込み中にエラーが発生しました。\n" + ex.getMessage(),
                            "画像読み込みエラー", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        }

        // ファイルの入出力
        if(cmd.startsWith("file:")) {
            if(cmd.endsWith("SAVE")) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showSaveDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    med.saveDrawings(file);
                }
            }
            else if (cmd.endsWith("LOAD")) {
                JFileChooser fileChooser = new JFileChooser();
                int option = fileChooser.showOpenDialog(this);
                if (option == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    med.loadDrawings(file);
                }
            }
        }

        // // ファイル名を指定して読み込み
        // if(cmd.startsWith("file:")) {
        //     String filename = "test";
        //     File file = new File(filename);
        //     if(cmd.endsWith("SAVE")) {
        //         med.saveDrawings(file);
        //     }
        //     else if (cmd.endsWith("LOAD")) {
        //         med.loadDrawings(file);
        //     }
        // }

        // 色選択
        else if(cmd.startsWith("fill:") || cmd.startsWith("line:")){
            Color settingColor = null;
            int changeColorNum = 0; //1なら塗りつぶし、2なら線を塗る

            if(cmd.startsWith("fill:")) changeColorNum = 1;
            else if(cmd.startsWith("line:")) changeColorNum = 2;

            if(cmd.endsWith("RED")) settingColor = Color.red;
            else if(cmd.endsWith("BLUE")) settingColor = Color.blue;
            else if(cmd.endsWith("GREEN")) settingColor = Color.green;
            else if(cmd.endsWith("WHITE")) settingColor = Color.white;
            else if(cmd.endsWith("OTHER")) {
                JFrame frame = new JFrame("色選択");
                settingColor = JColorChooser.showDialog(frame, "色を選択", colorPanel.getBackground());
            }

            if(settingColor != null) {
                med.setColor(settingColor, changeColorNum);
                // colorTextLabel.setForeground(settingColor);
            }
        }
    }

    public static void main(String[] args) {
        MyApplication app = new MyApplication();
        app.pack();
        app.setVisible(true);
    }
}
