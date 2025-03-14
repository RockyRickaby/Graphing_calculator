package com.graphcalc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

import org.mariuszgromada.math.mxparser.Function;
import org.mariuszgromada.math.mxparser.mXparser;

public class Plot extends JFrame {
    private static final double N = 5000;

    private HashMap<JButton, Function> buttonToFunction;
    private HashMap<Function, Color> functionColors;

    private ArrayList<Function> functions;
    private ArrayList<JButton> buttonsList;
    private int xMin = -10, xMax = 10;
    private int yMin = -10, yMax = 10;

    private boolean showGrid, showNumbers;
    
    public Plot() {
        buttonsList = new ArrayList<>();
        functions = new ArrayList<>();
        buttonToFunction = new HashMap<>();
        functionColors = new HashMap<>();

        showGrid = false;
        showNumbers = true;

        // optimizations
        mXparser.disableCanonicalRounding();
        mXparser.disableUlpRounding();
        mXparser.disableAlmostIntRounding();

        this.setBackground(Color.WHITE);
        this.setTitle("Graphing Calculator");
        this.add(generateMainPanel());
        this.setJMenuBar(generateMenuBar());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 600);
        //this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void removeAllFunctions() {
        if (buttonsList.isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "There're no functions to remove", "Function list is empty", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JPanel p = (JPanel) buttonsList.get(0).getParent();
        p.removeAll();
        p.revalidate();
        p.repaint();

        buttonToFunction.clear();
        buttonsList.clear();
        functionColors.clear();
        functions.clear();
        repaint();
    }

    private Color getRandomColor() {
        Random rand = new Random();
        float red = rand.nextFloat(),
              green = rand.nextFloat(),
              blue = rand.nextFloat();

        Color fColor = new Color(red, green, blue).darker();
        return fColor;
    }

    private JSplitPane generateMainPanel() {
        JSplitPane panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, generateFunctionArea(), generatePlot());
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem item = new JMenuItem("Clear function list");
        item.addActionListener(e -> removeAllFunctions());
        menu.add(item);

        menu.addSeparator();
        item = new JMenuItem("Exit");
        item.addActionListener(e -> System.exit(0));
        menu.add(item);
        menuBar.add(menu);

        menu = new JMenu("Plot");
        item = new JMenuItem("Show Grid");
        item.addActionListener(e -> {
            this.showGrid = !this.showGrid;
            repaint();
        });
        menu.add(item);

        item = new JMenuItem("Show numbers");
        item.addActionListener(e -> {
            this.showNumbers = !this.showNumbers;
            repaint();
        });
        menu.add(item);
        menuBar.add(menu);

        return menuBar;
    }

    private JPanel generateFunctionArea() {
        JPanel panel = new JPanel(new GridBagLayout());

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

        JTextField text = new JTextField("Just type in some expressions");

        text.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String checkText = text.getText();
                    if (checkText.isEmpty() || checkText.isBlank()) {
                        return;
                    }

                    String func = "";
                    if (!checkText.contains("f(x)")) {
                        func = "f(x) = ";
                    }
                    func += checkText;
                    text.setText("");

                    Function f = new Function(func);
                    if (!f.checkSyntax()) {
                        return;
                    }
                    functions.add(f);

                    Color fColor = getRandomColor();
                    functionColors.put(f, fColor);

                    JButton b = new JButton(func);
                    b.setForeground(fColor);

                    b.addActionListener(act -> {
                        JButton source = (JButton) act.getSource();
                        buttons.remove(source);

                        Function aux = buttonToFunction.remove(source);
                        
                        buttonsList.remove(source);
                        functions.remove(aux);
                        functionColors.remove(aux);

                        buttons.removeAll();
                        buttons.revalidate();
                        buttons.repaint();
                        for (JButton but : buttonsList) {
                            buttons.add(but);
                        }
                        repaint();
                    });
                    buttons.removeAll();
                    buttons.revalidate();
                    buttons.repaint();

                    buttonsList.add(b);
                    buttonToFunction.put(b, f);
                    for (JButton but : buttonsList) {
                        buttons.add(but);
                    }
                    repaint();
                }
            }
        });

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;
        gc.anchor = GridBagConstraints.PAGE_START;
        gc.weightx = 1;
        gc.weighty = .0625;
        panel.add(text, gc);

        JScrollPane scrollButtons = new JScrollPane(buttons);
        gc.gridy = 1;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx =.0625;
        gc.weighty = 1;
        panel.add(scrollButtons, gc);
        return panel;
    }

    private JPanel generatePlot() {
        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                double x = 0, y = 0;
                double percentX = 0, percentY = 0;
                double mathX = 0, mathY = 0;

                double halfWidth = width / 2;
                double halfHeight = height / 2;

                g2.setColor(Color.BLACK);
                g2.draw(new Line2D.Double(halfWidth, 0, halfWidth, height));
                g2.draw(new Line2D.Double(0, halfHeight, width, halfHeight));

                g2.setColor(Color.GRAY);

                for (int i = 0; i <= N; i++) {
                    percentX = i / N;
                    percentY = percentX;

                    mathX = percentX * (xMax - xMin) + xMin;
                    mathY = percentY * (yMax - yMin) + yMin;

                    x = percentX * width;
                    y = percentY * height;
                    if (mathX == (int) mathX) {
                        if (mathX == 0) {
                            continue;
                        }
                        if (showGrid) g2.drawLine((int) x, 0, (int) x, height);
                        if (showNumbers) g2.drawString(String.valueOf((int) mathX), (int) x - 3, (int) halfHeight + 14);
                    }
                    if (mathY == (int) mathY) {
                        if (mathY == 0) {
                            continue;
                        }
                        if (showGrid) g2.drawLine(0, (int) y, width, (int) y);
                        if (showNumbers) g2.drawString(String.valueOf((int) mathY), (int) halfWidth + 4, height - (int) y + 3);
                    }
                }

                g2.setStroke(new BasicStroke(2));

                ArrayList<Point2D.Double> points = new ArrayList<>();
                for (Function f : functions) {
                    if (!f.checkSyntax()) {
                        continue;
                    }
                    for (int i = 0; i <= N; i++) {
                        percentX = i / N;
    
                        mathX = percentX * (xMax - xMin) + xMin;
                        mathY = f.calculate(mathX); // f(x) The function
    
                        percentY = 1 - (mathY - yMin) / (yMax - yMin);
    
                        x = percentX * width;
                        y = percentY * height;
                        points.add(new Point2D.Double(x, y));
                    }
                    int size = points.size() - 1;

                    g2.setColor(functionColors.get(f));
                    for (int i = 0; i < size; i++) {
                        Point2D.Double p1 = points.get(i),
                                       p2 = points.get(i + 1);
                        if (Math.abs(p1.y - p2.y) > 100*yMax) {
                            continue;
                        }
                        g2.draw(new Line2D.Double(p1, p2));
                    }
                    points.clear();
                }
            }
        };
        return panel;
    }
}
