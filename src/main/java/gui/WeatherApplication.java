package gui;

import model.WeatherParameter;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class WeatherApplication {

    private WeatherController controller;

    private WeatherApplication() {
        initialize();
    }

    private void initialize() {
        JFrame window = new JFrame("Weather Prediction");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setBounds(100, 100, 400, 200);
        window.setVisible(true);

        window.setLayout(new MigLayout("insets 5 5 5 5", "[grow]", "[grow][grow]"));

        JButton doMagic = new JButton("Click for magic");
        JButton showGraphs = new JButton("Show Graph");

        window.getContentPane().add(doMagic, "cell 0 0, grow, center");
        window.getContentPane().add(showGraphs, "cell 0 1, grow, center");

        doMagic.addActionListener(e -> controller = new WeatherController());

        showGraphs.addActionListener(e -> {
            JComboBox<WeatherParameter> graphOption  = new JComboBox<>(WeatherParameter.values());

            int result = JOptionPane.showConfirmDialog(window, graphOption, "Create graph", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                controller.interpretResults((WeatherParameter) graphOption.getSelectedItem());
            }
        });
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
