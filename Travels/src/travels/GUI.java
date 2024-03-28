package travels;

import javax.swing.*;

public class GUI extends JFrame {
    public GUI() {
        setTitle("Aplicación de Viajes");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
        JTabbedPane tabbedPane = new JTabbedPane();
        
        TableRutes tableRutes = new TableRutes();
        TripsPanel tripsPanel = new TripsPanel(tableRutes);
        tableRutes.setTripsPanel(tripsPanel); // Establecer la conexión

        tabbedPane.add("Rutas", tableRutes.new SubirCSV());
        tabbedPane.add("Viajes", tripsPanel);

        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUI().setVisible(true);
        });
    }
}
