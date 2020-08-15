package net.melonl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class DrawComponent extends JComponent implements MouseMotionListener, MouseListener {

    private final int unitLength = 20;//轴上一格的长度，单位为像素

    //由于是固定窗口大小，而且获取比较困难，直接写死
    private int H = 433;
    private int W = 786;
    //定义比例尺和绘制密度相关
    private float lenX;
    private float lenY;
    private float dx;
    private float scaleX;
    private float scaleY;
    //控制拖动距离相关
    private float lastX = -1;
    private float lastY = -1;
    private float offsetX;
    private float offsetY;
    private float offsetScale = 0.8f;
    //绘制文字的偏移，防止和坐标轴重合
    private float stringOffsetX = 4;
    private float stringOffsetY = -4;
    //坐标轴、刻度相关
    private Line2D.Float axisX;//x轴轴线
    private Line2D.Float axisY;//y轴轴线
    private Point2D.Float zeroPos;//原点坐标
    private Array<Line2D.Float> gradXP;//Graduation X Positive，x轴正半轴刻度
    private Array<Line2D.Float> gradXN;//Graduation X Negative，x轴负半轴刻度
    private Array<Line2D.Float> gradYP;//y轴正半轴刻度
    private Array<Line2D.Float> gradYN;//y轴负半轴刻度

    //记录屏幕上的x轴起点和y轴起点被移动了多少
    private float countOffsetX;
    private float countOffsetY;

    public DrawComponent() {
        super();
        addMouseMotionListener(this);
        addMouseListener(this);

        axisX = new Line2D.Float(0, H / 2, W, H / 2);
        axisY = new Line2D.Float(W / 2, 0, W / 2, H);
        zeroPos = new Point2D.Float(W / 2, H / 2);

        //提前构造好对象，后续只需重用即可，通过判断x1 == -1来判断是否被初始化过
        gradXP = new Array<>(W / unitLength + 1);
        for (int i = 0; i < W / unitLength; ++i)
            gradXP.addLast(new Line2D.Float(-1, 0, 0, 0));
        gradXN = new Array<>(W / unitLength);
        for (int i = 0; i < W / unitLength; ++i)
            gradXN.addLast(new Line2D.Float(-1, 0, 0, 0));

        gradYP = new Array<>(H / unitLength + 1);
        for (int j = 0; j < H / unitLength; ++j)
            gradYP.addLast(new Line2D.Float(-1, 0, 0, 0));
        gradYN = new Array<>(H / unitLength);
        for (int j = 0; j < H / unitLength; ++j)
            gradYN.addLast(new Line2D.Float(-1, 0, 0, 0));

        updateData();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        //拖动过程中离开窗口
        if (mouseEvent.getY() < 0 || mouseEvent.getY() > H || mouseEvent.getX() < 0 || mouseEvent.getX() > W)
            return;

        //如果lastX lastY有数据则计算offset
        if (lastX != -1 && lastY != -1) {
            offsetX = mouseEvent.getX() - lastX;
            offsetY = mouseEvent.getY() - lastY;

            countOffsetX += offsetX;
            countOffsetY += offsetY;
        }
        lastX = mouseEvent.getX();
        lastY = mouseEvent.getY();
        //print(countOffsetX + "," + countOffsetY);
        updateData();
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        lastX = mouseEvent.getX();
        lastY = mouseEvent.getY();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        antiAliasing(g);

        //绘制背景
        g.setColor(Color.white);
        g.fillRect(0, 0, W, H);

        drawAxisXY((Graphics2D) g);
    }

    private void updateData() {
        //x轴只受y方向的移动
        axisX.y1 += offsetY * offsetScale;
        axisX.y2 += offsetY * offsetScale;
        //y轴只受x方向的移动
        axisY.x1 += offsetX * offsetScale;
        axisY.x2 += offsetX * offsetScale;

        zeroPos.x += offsetX * offsetScale;
        zeroPos.y += offsetY * offsetScale;


        float sx = zeroPos.x;
        float sy;

        if (isInScreen(zeroPos)) {
            //计算X轴刻度
            for (int i = 0; i < H / unitLength; ++i) {
                gradYP.get(i).x1 = zeroPos.x;
                gradYP.get(i).y1 = zeroPos.y - (i + 1) * unitLength;
                gradYP.get(i).x2 = zeroPos.x + 4;
                gradYP.get(i).y2 = zeroPos.y - (i + 1) * unitLength;

                gradYN.get(i).x1 = zeroPos.x;
                gradYN.get(i).y1 = zeroPos.y + (i + 1) * unitLength;
                gradYN.get(i).x2 = zeroPos.x + 4;
                gradYN.get(i).y2 = zeroPos.y + (i + 1) * unitLength;
            }
            //计算X轴刻度
            for (int i = 0; i < W / unitLength; ++i) {
                gradXP.get(i).x1 = zeroPos.x + (i + 1) * unitLength;
                gradXP.get(i).y1 = zeroPos.y;
                gradXP.get(i).x2 = zeroPos.x + (i + 1) * unitLength;
                gradXP.get(i).y2 = zeroPos.y - 4;

                gradXN.get(i).x1 = zeroPos.x - (i + 1) * unitLength;
                gradXN.get(i).y1 = zeroPos.y;
                gradXN.get(i).x2 = zeroPos.x - (i + 1) * unitLength;
                gradXN.get(i).y2 = zeroPos.y - 4;
            }
        } else {

        }
    }

    private void drawAxisXY(Graphics2D g) {


        //x y轴线
        g.setColor(Color.black);
        g.draw(axisX);
        g.draw(axisY);
        //原点的0
        g.drawString("0", zeroPos.x + stringOffsetX, zeroPos.y + stringOffsetY);

        if (isInScreen(zeroPos)) {
            for (int i = 0; i < gradYN.getSize(); ++i) {
                g.draw(gradYN.get(i));
                g.draw(gradYP.get(i));
            }

            for (int i = 0; i < gradXP.getSize(); ++i) {
                g.draw(gradXN.get(i));
                g.draw(gradXP.get(i));
            }
        }

    }

    private boolean isInScreen(Line2D.Float l) {
        return isInScreen(l.x1, l.y1);
    }

    private boolean isInScreen(Point2D.Float p) {
        return isInScreen(p.x, p.y);
    }

    private boolean isInScreen(float x, float y) {
        return (x >= 0 && x <= W && y >= 0 && y <= H);
    }

    //抗锯齿
    private void antiAliasing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private void print(String s) {
        System.out.println(s);
    }


}
