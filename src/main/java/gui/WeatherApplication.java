package gui;

import javax.swing.*;

public class WeatherApplication {

    private WeatherApplication() {
        initialize();
    }

    private void initialize() {
        JFrame window = new JFrame("Weather Prediction");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setBounds(100, 100, 700, 500);
        window.setVisible(true);

        new WeatherController();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
                new WeatherApplication();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
