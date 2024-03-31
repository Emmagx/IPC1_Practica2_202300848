package travels;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;


public class HistorialViajesPanel extends JPanel {
    TableRutes tableRutes = new TableRutes();
    private JTable historialTable;
    private DefaultTableModel tableModel;
    
    public HistorialViajesPanel() {
        setLayout(new BorderLayout()); // Use BorderLayout for the panel
        
        // Create the title label
        JLabel titleLabel = new JLabel("Historial de Viajes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Set the title font
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(153, 255, 153)); // A green background color
        titleLabel.setPreferredSize(new Dimension(400, 50)); // Set the preferred size for the title
        
        // Add the title label to the top of the panel
        add(titleLabel, BorderLayout.NORTH);
        
        // Column names for the table
        String[] columnNames = {"ID", "Fecha y Hora Inicio", "Fecha y Hora Fin", "Distancia (km)", "Vehículo", "Gasolina Consumida"};
        
        // Create the table model
        tableModel = new DefaultTableModel(columnNames, 0);
        
        // Create the table
        historialTable = new JTable(tableModel);
        historialTable.setFillsViewportHeight(true); // Fill the viewport height
        
        // Add a scroll pane containing the table to the center of the panel
        JScrollPane scrollPane = new JScrollPane(historialTable);
        add(scrollPane, BorderLayout.CENTER);
        tableRutes.ajustarAnchoColumnas(historialTable);
    }
    
    public void agregarViajeATabla(ViajeRealizado.Viaje viaje) {
        // This is a method to add a trip to the table
        tableModel.addRow(new Object[]{
                viaje.getId(),
                viaje.getFechaHoraInicio(),
                viaje.getFechaHoraFin(),
                viaje.getDistancia(),
                viaje.getVehiculo().getTipo(),
                viaje.getGasolinaConsumida()
        });
    }

    // You might also need a method to update the table with a list of trips
    public void actualizarTablaConViajes(ArrayList<ViajeRealizado.Viaje> listaViajes) {
        tableModel.setRowCount(0); // Clear the table
        for (ViajeRealizado.Viaje viaje : listaViajes) {
            agregarViajeATabla(viaje); // Add each trip to the table
        }
    }
}