import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JOptionPane;

import java.awt.*;
import java.io.*;

public class Mediator{
    Vector<MyDrawing> drawings;
    MyCanvas canvas;
    private Vector<MyDrawing> selectedDrawings = new Vector<>();
    Vector<MyDrawing> buffer = new Vector<>();    //コピー時にはここに保存
    MyRectangle groupRange;    //複数選択時に選択範囲をわかりやすくするための矩形
    int startX, startY; //groupRegionを作る際の左上の座標
    Vector<Integer> gapXs = new Vector<>();
    Vector<Integer> gapYs = new Vector<>(); //複数選択時の各図形のギャップ 
    Vector<Integer> copyXs = new Vector<>();
    Vector<Integer> copyYs = new Vector<>(); //コピーする際の左上からの*相対*座標
    private RotationAnimator animator;
    private MyDrawing drawing;


    public Mediator(MyCanvas canvas) {
        this.canvas = canvas;
        drawings = new Vector<MyDrawing>();
        animator = new RotationAnimator(canvas);
    }

    public Enumeration<MyDrawing> drawingsElements() {
        return drawings.elements();
    }

    // selectedDrawingsを初期化
    public void initializeSelected() {
        System.out.println("initializeSelected");
        for(int i=0; i<selectedDrawings.size(); i++) {
            MyDrawing d = selectedDrawings.get(i);
            d.setSelected(false);
        }
        selectedDrawings.clear();
        repaint();
    }

    public void initializeGaps() {
        gapXs.clear();
        gapYs.clear();
    }

    public void initializeCopys() {
        copyXs.clear();
        copyYs.clear();
    }

    public void addDrawing(MyDrawing d) {
        drawings.add(d);
        // System.out.println("drawings added. this size: " + drawings.size());
    }

    public void removeDrawing(MyDrawing d) {
        drawings.remove(d);
        // System.out.println("drawings removed. this size: " + drawings.size());
    }

    // 画像の追加
    public void addImage(Image image, String imagePath, int x, int y, int w, int h) {
        ImageShape imageShape = new ImageShape(image, imagePath, x, y);
        imageShape.setSize(w, h);
        drawings.add(imageShape);
        imageShape.setRegion();
        repaint();
    }
    
    public void singleSelected(int x, int y) {
        MyDrawing selected = null;   // 押された箇所の図形
        if(!selectedDrawings.isEmpty()) initializeSelected();

        Enumeration<MyDrawing> e = drawingsElements();

        while(e.hasMoreElements()) {
            MyDrawing d = e.nextElement();
            if(d.contains(x, y)) {
                selected = d;
            }
            
            // 一旦すべてのオブジェクトを非選択状態に
            d.setSelected(false);
        }

        System.out.println(selected);

        if(selected != null) {
            // 選ばれた図形(重なってる場合上にある図形)のみ選択状態に
            setSelectedDrawings(selected);
            repaint();
        }
    }

    public void makeGroupRange(int x, int y) {
        startX = x; startY = y;
        groupRange = new MyRectangle(x, y, 0, 0, Color.BLACK, new Color(0, 0, 0, 1), false, 1, true);
        addDrawing(groupRange);
    }

    public void removeGroupRange() {
        if(groupRange == null) return;
        removeDrawing(groupRange);
    }

    // 範囲を動的に選択
    public void selectRange(int x, int y) {
        // System.out.println("selectRange");
        if(groupRange == null) return;

        int fromStartX = x - startX;
        int fromStartY = y - startY;

        int w = Math.abs(fromStartX);
        int h = Math.abs(fromStartY);

        if(fromStartX>=0 && fromStartY>=0) {
            x = startX;
            y = startY;
        } else if(fromStartX>=0 && fromStartY<0) {
            x = startX;
        } else if(fromStartX<0 && fromStartY<0) {
            
        } else if(fromStartX<0 && fromStartY>=0) {
            y = startY;
        }

        groupRange.setLocation(x, y);
        groupRange.setSize(w, h);
        repaint();
    }

    public void multiSelected() {
        if(groupRange == null) return;
        Enumeration<MyDrawing> e = drawingsElements();

        // StateFunctionが呼ばれないためここでRegionをセットする
        groupRange.setRegion();

        // System.out.println("drawingsSize: " + drawings.size());
        while(e.hasMoreElements()) {
            MyDrawing d = e.nextElement();
            if(d == groupRange) {
                // System.out.println("break for groupRegion");
                break;
            }
            int[] center = new int[2];

            center[0] = d.getX() + Math.round(d.getW() / 2);
            center[1] = d.getY() + Math.round(d.getH() / 2);

            if(groupRange.contains(center[0], center[1])) {
                setSelectedDrawings(d);
            }
        }
        repaint();
    }

    public void setGaps(int x, int y) {
        // System.out.println("setGaps");
        if(selectedDrawings.isEmpty()) return;

        if(!gapXs.isEmpty()) initializeGaps();
        for(int i=0; i<selectedDrawings.size(); i++) {
            MyDrawing d = selectedDrawings.get(i);
            gapXs.add(x - d.getX());
            gapYs.add(y - d.getY());
        }
    }

    public void move(int x, int y) {
        if(selectedDrawings.isEmpty()) return;

        for(int i=0; i<selectedDrawings.size(); i++) {
            MyDrawing d = selectedDrawings.get(i);
            d.setLocation(x - gapXs.get(i), y - gapYs.get(i));
        }
        repaint();
    }

    public void setRegionOfSelected() {
        if(selectedDrawings.isEmpty()) return;

        for(int i=0; i<selectedDrawings.size(); i++) {
            MyDrawing d = selectedDrawings.get(i);
            d.setRegion();
        }
    }

    public void clearBuffer() {
        buffer.clear();
    }

    public void copy() {
        if(selectedDrawings.isEmpty()) return;

        clearBuffer();
        initializeCopys();
        int x = 9999; int y = 9999; //左上の座標
        for(int i=0; i<selectedDrawings.size(); i++) {
            MyDrawing d = selectedDrawings.get(i);
            buffer.add(d.clone());
            if(d.getX() < x) x = d.getX();
            if(d.getY() < y) y = d.getY();
        }

        // 左上からの相対座標を計算
        for(int i=0; i<selectedDrawings.size(); i++) {
            MyDrawing d = selectedDrawings.get(i);
            copyXs.add(d.getX() - x);
            copyYs.add(d.getY() - y);
        }
    }

    public void cut() {
        copy();
        for(int i=0; i<selectedDrawings.size(); i++) {
            removeDrawing(selectedDrawings.get(i));
        }
        repaint();
    }

    public void paste(int x, int y) {
        if(buffer.isEmpty()) return;
        MyDrawing clone;

        initializeSelected();
        // クローンが全部同じ場所に出てきそう
        for(int i=0; i<buffer.size(); i++) {
            clone = (MyDrawing)buffer.get(i).clone();
            clone.setLocation(x + copyXs.get(i), y + copyYs.get(i));
            clone.setRegion();
            addDrawing(clone);
            setSelectedDrawings(clone);
        }
        repaint();
    }

    public void RotateAnimate(ImageShape image) {
        animator.RotateAnimate(image);
    }

    // // ファイルの保存
    // public void saveDrawings(File file) {
    //     try {
    //         FileOutputStream fout = new FileOutputStream("file.txt");
    //         ObjectOutputStream out = new ObjectOutputStream(fout);

    //         out.writeObject(drawings);
    //         out.flush();

    //         fout.close();
    //     } catch(Exception ex) {}
    // }

    // // ファイルの読み込み
    // public void loadDrawings(File file) {
    //     try {
    //         FileInputStream fin = new FileInputStream("file.txt");
    //         ObjectInputStream in = new ObjectInputStream(fin);

    //         this.drawings = (Vector<MyDrawing>)in.readObject();
    //         fin.close();
    //     } catch(Exception ex) {}
    // }

    // ファイルの保存
    public void saveDrawings(File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(drawings); // shapesリストを直列化してファイルに書き込む
            JOptionPane.showMessageDialog(canvas, "描画データが保存されました。", "保存完了", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(canvas, "描画データの保存中にエラーが発生しました。\n" + e.getMessage(), "保存エラー", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // ファイルの読み込み
    public void loadDrawings(File file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // ファイルからオブジェクトを読み込み、ArrayList<DrawableShape>にキャスト
            @SuppressWarnings("unchecked")
            Vector<MyDrawing> loadedShapes = (Vector<MyDrawing>) ois.readObject();
            this.drawings = loadedShapes; // 読み込んだリストで現在のリストを置き換える
            repaint(); // 描画を更新
            JOptionPane.showMessageDialog(canvas, "描画データが読み込まれました。", "読み込み完了", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(canvas, "描画データの読み込み中にエラーが発生しました。\n" + e.getMessage(), "読み込みエラー", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public MyDrawing DrawingOverlapMouse(int x, int y) {
        MyDrawing rslt = null;
        Enumeration<MyDrawing> e = drawingsElements();

        while(e.hasMoreElements()) {
            MyDrawing d = e.nextElement();
            if(d.contains(x, y)) {
                rslt = d;
            }
        }

        if(rslt != null) {
            return rslt;
        } else {
            return null;
        }
    }

    // マウスの場所に図形があるかどうかを調べる
    public boolean isMouseOnShape(int x, int y) {
        // System.out.println("isMouseOnShape");
        Enumeration<MyDrawing> e = drawingsElements();

        while(e.hasMoreElements()) {
            MyDrawing d = e.nextElement();
            if(d.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    // 選択図形があるかどうか
    public boolean isSelected() {
        if(selectedDrawings.isEmpty()) return false;
        else return true;
    }

    // 色選択メニューから呼ばれる
    // 選択されている図形の色を変える
    public void setColor(Color color, int changeColorNum) {
        if(selectedDrawings.isEmpty()) return;
        if(changeColorNum == 1) {
            for(int i=0; i<selectedDrawings.size(); i++) {
                MyDrawing d = selectedDrawings.get(i);
                d.setFillColor(color);
            }
            repaint();
            // setFillColor(color);
        }
        else if(changeColorNum == 2) {
            for(int i=0; i<selectedDrawings.size(); i++) {
                MyDrawing d = selectedDrawings.get(i);
                d.setLineColor(color);
            }
            repaint();
            // setLineColor(color);
        }
    }

    // 線の太さのドロップダウンから呼ばれる
    // 選択されている図形の線の太さを変える
    public void setLineWidth(int lineWidth) {
        if(selectedDrawings.isEmpty()) return;
        for(int i=0; i<selectedDrawings.size(); i++) {
            MyDrawing d = selectedDrawings.get(i);
            d.setLineWidth(lineWidth);
        }
        repaint();
    }

    // 影のボックスから呼ばれる
    // 選択されている図形の影の有無を変える
    public void setShadow(boolean isShadow) {
        if(selectedDrawings.isEmpty()) return;
        for(int i=0; i<selectedDrawings.size(); i++) {
            MyDrawing d = selectedDrawings.get(i);
            d.setShadow(isShadow);
        }
        repaint();
    }

    public void setDashLine(boolean isDashLine) {
        if(selectedDrawings.isEmpty()) return;
        for(int i=0; i<selectedDrawings.size(); i++) {
            MyDrawing d = selectedDrawings.get(i);
            d.setDashLine(isDashLine);
        }
        repaint();
    }

    public void repaint() {
        canvas.repaint();
    }

    public void setSelectedDrawings(MyDrawing d) {
        selectedDrawings.add(d);
        d.setSelected(true);
    }

    public Vector<MyDrawing> getSelectedDrawings() {
        return selectedDrawings;
    }
}

