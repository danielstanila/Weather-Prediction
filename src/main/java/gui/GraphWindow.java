package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GraphWindow extends JFrame {

    private Double[] elements;
    private Double max;
    private Double min;

    private Double width;
    private Double height;
    private Double wPadding;
    private Double hPadding;

    public GraphWindow(String title, Double... elements) {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 1000);
        setVisible(true);

        Dimension dimension = getContentPane().getSize();
        this.elements = elements;
        this.max = Utils.getMax(elements);
        this.min = Utils.getMin(elements);

        this.wPadding = (dimension.getWidth() * 0.1);
        this.hPadding = (dimension.getHeight() * 0.1);
        this.width = (dimension.getWidth() * 0.8) / elements.length;
        this.height = (dimension.getHeight() * 0.8) / (max - min);
        switch (Utils.sign(min, max)) {
            case -1: {
                this.height = (dimension.getHeight() * 0.8) / -min;
                break;
            }
            case 1: {
                this.height = (dimension.getHeight() * 0.8) / max;
                break;
            }
        }

        getContentPane().add(new Graph(elements));
    }

    @Override
    public void repaint(long time, int x, int y, int width, int height) {
        Dimension dimension = getContentPane().getSize();
        this.wPadding = (dimension.getWidth() * 0.1);
        this.hPadding = (dimension.getHeight() * 0.1);
        this.width = (dimension.getWidth() * 0.8) / elements.length;
        this.height = (dimension.getHeight() * 0.8) / (max - min);
        switch (Utils.sign(min, max)) {
            case -1: {
                this.height = (dimension.getHeight() * 0.8) / -min;
                break;
            }
            case 1: {
                this.height = (dimension.getHeight() * 0.8) / max;
                break;
            }
        }

        super.repaint(time, x, y, width, height);
    }

    private class Graph extends JComponent {

        private Double[] elements;

        private Graph(Double... elements) {
            this.elements = elements;
        }

        @Override
        public void paint(Graphics g) {
            g.setColor(Color.RED);
            Dimension dimension = GraphWindow.this.getContentPane().getSize();
            //g.drawRect((int)(dimension.getWidth() * 0.1), (int)(dimension.getHeight() * 0.1), (int)(dimension.getWidth() * 0.8), (int)(dimension.getHeight() * 0.8));

            for (int i = 0; i < elements.length; i++) {
                int x = (int) (wPadding + i * width);
                int w = (int) (width - 1);
                int y = (int) (hPadding + max * height);
                int h = (int) (-elements[i] * height);
                if (min < 0 && max < 0) {
                    y = hPadding.intValue();
                    h = (int) (-elements[i] * height);
                }
                g.fillRect(x, y, w, h);
            }

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

    public static void main(String[] args) {

        Random random = new Random();
        int n = 81;
        Double[] values = new Double[n];
        for (int i = 0 ; i < n; i++) {
            values[i] = i * 10 + random.nextDouble() * 20 - 500;
            //values[i] = random.nextDouble() * 10;
        }

        values[5] = 1000.;
        new GraphWindow("Weather", values);
    }
}
