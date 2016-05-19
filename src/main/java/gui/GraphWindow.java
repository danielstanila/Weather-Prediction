package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Random;

public class GraphWindow extends JFrame {

    private Double[] graphElements;
    private Double max;
    private Double min;

    private Double width;
    private Double height;
    private Double wPadding;
    private Double hPadding;

    public GraphWindow(String title, Double[] graphElements, Double[] lineElements) {
        super(title);
        setVisible(true);

        Dimension dimension = getContentPane().getSize();
        this.graphElements = graphElements;
        this.max = Utils.getMax(graphElements);
        this.min = Utils.getMin(graphElements);

        this.wPadding = 25.0;
        this.hPadding = 25.0;
        this.width = Math.max((dimension.getWidth() * 0.8) / graphElements.length, 10.0);
        this.height = Math.max((dimension.getHeight() * 0.8) / (max - min), 5.0);
        switch (Utils.sign(min, max)) {
            case -1: {
                this.height = Math.max((dimension.getHeight() * 0.8) / -min, 5.0);
                break;
            }
            case 1: {
                this.height = Math.max((dimension.getHeight() * 0.8) / max, 5.0);
                break;
            }
        }
        GraphPanel graphPanel = new GraphPanel(graphElements, lineElements);
        graphPanel.setPreferredSize(new Dimension(50 + 10 * graphElements.length, (int) (50 + (Math.abs(max) + Math.abs(min)) * height)));

        JScrollPane scrollPane = new JScrollPane(graphPanel);
        getContentPane().add(scrollPane);

        setBounds(100, 100, 800, 600);
    }

    @Override
    public void repaint(long time, int x, int y, int width, int height) {
        Dimension dimension = getContentPane().getSize();
        this.width = Math.max((dimension.getWidth() * 0.8) / graphElements.length, 10.0);
        this.height = Math.max((dimension.getHeight() * 0.8) / (max - min), 5.0);
        switch (Utils.sign(min, max)) {
            case -1: {
                this.height = Math.max((dimension.getHeight() * 0.8) / -min, 5.0);
                break;
            }
            case 1: {
                this.height = Math.max((dimension.getHeight() * 0.8) / max, 5.0);
                break;
            }
        }

        super.repaint(time, x, y, width, height);
    }

    private class GraphPanel extends JPanel {

        private Graph graph;
        private Line line;

        public GraphPanel(Double[] graphElements, Double[] lineElements) {
            this.graph = new Graph(graphElements);
            this.line = new Line(lineElements);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            graph.paint(g);
            line.paint(g);
        }
    }

    private class Graph extends JComponent {
        private Double[] elements;

        private Graph(Double... elements) {
            this.elements = elements;
        }

        @Override
        public void paint(Graphics g) {
            g.setColor(Color.RED);
            for (int i = 0; i < elements.length; i++) {
                int x = (int) (wPadding + i * width);
                int w = (int) (width - 1);
                int y = (int) (hPadding + max * height);
                int h = (int) (-elements[i] * height);
                if (min < 0 && max < 0) {
                    y = hPadding.intValue();
                    h = (int) (-elements[i] * height);
                }
                if (h < 0) {
                    y += h;
                    h = -h;
                }
                g.fillRect(x, y, w, h);
            }
        }
    }

    private class Line extends JComponent {
        private Double[] elements;

        private Line(Double... elements) {
            this.elements = elements;
        }

        @Override
        public void paint(Graphics g) {
            g.setColor(Color.BLUE);
            for (int i = 0; i < elements.length - 1; i++) {
                int x1 = (int) (wPadding + i * width);
                int x2 = (int) (wPadding + (i + 1) * width);
                int y1 = (int) (hPadding + max * height - elements[i] * height);
                int y2 = (int) (hPadding + max * height - elements[i + 1] * height);
                if (min < 0 && max < 0) {
                    y1 = (int) (hPadding - elements[i] * height);
                    y2 = (int) (hPadding - elements[i + 1] * height);
                }
                g.drawLine(x1, y1, x2, y2);
            }
        }
    }

    private static final class Utils {

        private static Double getMax(Double[] array) {
            Double max = array[0];
            for (int i = 1; i < array.length; i++) {
                if (array[i] > max) {
                    max = array[i];
                }
            }
            return max;
        }

        private static Double getMin(Double[] array) {
            Double min = array[0];
            for (int i = 1; i < array.length; i++) {
                if (array[i] < min) {
                    min = array[i];
                }
            }
            return min;
        }

        private static Integer sign(Double a, Double b) {
            if  (a < 0 && b < 0) {
                return -1;
            } else if (a >= 0 && b >= 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
