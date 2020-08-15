package net.melonl;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {

    private final static String TEXT_HELP = "这是一个根据你输入的表达式画出相应函数图形的程序。\n" +
            "表达式只能是Y=f(x)的形式，其中‘Y=’在输入框左边已给出，你只需要补全f(x)即可。\n" +
            "在‘X轴范围’与‘Y轴范围’区域可设置可见的X轴、Y轴的范围，范围只保留到小数点后两位。";

    private JButton btnClear;
    private JButton btnDraw;
    private JButton btnHelp;
    private JTextField textStartX;
    private JTextField textEndX;
    private JTextField textStartY;
    private JTextField textEndY;
    private JTextField textFunc;
    private DrawComponent componentdraw;
    private JFrame mainWindow;
    private JPanel mainContainer;
    private JPanel container;

    public MainWindow() {
        setUpWindow();
        setUpListener();
    }

    private double f(double x) {
        return Math.sin(x);
    }

    private void setUpWindow() {

        mainWindow = new JFrame("JDrawFunc");
        mainWindow.setSize(800, 600);
        mainWindow.setResizable(false);//固定窗口尺寸
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLocationRelativeTo(null);

        /*
        final int H = 400;
        final int W = 400;
        final double axisX = 3.14159;
        final double axisY = 2;
        final double dx = 0.2;
        final double scaleX = axisX / 400.0;
        final double scaleY = H / axisY;
        Array<Line2D.Double> points = new Array<>();
        for (double i = 0; i < W; i += dx) {
            double y = H / 2 - f((i - W / 2) * scaleX) * scaleY;
            points.addLast(new Line2D.Double(i, y, i, y));
        }
        System.out.println(points.getSize());


        JPanel jPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                for (int i = 0; i < points.getSize(); ++i)
                    g2d.draw(points.get(i));
                // g2d.drawLine(i, points.get(i), i + 1, points.get(i + 1));

            }
        };

         */

        mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout());
        componentdraw = new DrawComponent();
        container = new JPanel();

        container.add(newLine(150, 150, Box.createVerticalStrut(4)));

        textFunc = new JTextField(14);
        container.add(newLine(150, 150, new JLabel("函数表达式 Y = "), textFunc));

        textStartX = new JTextField(6);
        textEndX = new JTextField(6);
        container.add(newLine(130, 100, new JLabel("X轴范围："), textStartX, new JLabel(" 到 "), textEndX));

        textStartY = new JTextField(6);
        textEndY = new JTextField(6);
        container.add(newLine(130, 100, new JLabel("Y轴范围："), textStartY, new JLabel(" 到 "), textEndY));

        btnHelp = new JButton("    帮助    ");
        btnClear = new JButton("    重置    ");
        btnDraw = new JButton("    画！    ");
        container.add(newLine(150, 150, btnHelp, Box.createHorizontalStrut(8), btnClear, Box.createHorizontalStrut(8), btnDraw));

        container.setPreferredSize(new Dimension(600, 130));
        mainContainer.add(componentdraw, BorderLayout.CENTER);
        mainContainer.add(container, BorderLayout.SOUTH);

        mainWindow.setContentPane(mainContainer);
        mainWindow.setVisible(true);

    }

    private void setUpListener() {
        btnHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, TEXT_HELP, "帮助", JOptionPane.PLAIN_MESSAGE);
            }
        });

    }

    private Box newSpaceLine(int lStrutWidth, int rStrutWidth, int strutHight) {
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalStrut(lStrutWidth));
        box.add(Box.createVerticalStrut(strutHight));
        box.add(Box.createHorizontalStrut(rStrutWidth));
        return box;
    }

    private Box newLine(int lStrutWidth, int rStrutWidth, Component... components) {
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalStrut(lStrutWidth));
        for (Component c : components) box.add(c);
        box.add(Box.createHorizontalStrut(rStrutWidth));
        return box;
    }
}
