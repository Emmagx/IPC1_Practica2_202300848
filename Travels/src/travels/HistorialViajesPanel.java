package travels;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import travels.ViajeRealizado.Viaje;

public class HistorialViajesPanel extends JPanel implements Serializable {
    TableRutes tableRutes = new TableRutes();
    private JTable historialTable;
    private DefaultTableModel tableModel;
    ArrayList<ViajeRealizado.Viaje> listaViajes = new ArrayList<>();
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
    
    
    
public void agregarViajeATabla(Viaje viaje) {
    // Agregar viaje al modelo de la tabla
    tableModel.addRow(new Object[]{
            viaje.getId(),
            viaje.getFechaHoraInicio(),
            viaje.getFechaHoraFin(),
            viaje.getDistancia(),
            viaje.getVehiculo().getTipo(),
            viaje.getGasolinaConsumida()
    });

    // Sincronizar la lista con el modelo de la tabla
    listaViajes.add(viaje);

    // No es necesario llamar a fireTableDataChanged
}

    // You might also need a method to update the table with a list of trips
    public void actualizarTablaConViajes(ArrayList<ViajeRealizado.Viaje> listaViajes) {
        
        tableModel.setRowCount(0); // Clear the table
        for (ViajeRealizado.Viaje viaje : listaViajes) {
            agregarViajeATabla(viaje); // Add each trip to the table
        }
        System.out.println("Tabla historial Actualizado");
    }
    public ArrayList<ViajeRealizado.Viaje> getHistorial() {
        
        
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            // Asumiendo que tienes un constructor en tu clase Viaje que acepta estos parámetros.
            // Deberás convertir o parsear cada objeto a su tipo correspondiente si es necesario.
            String id = (String) tableModel.getValueAt(i, 0);
            Date fechaHoraInicio = (Date) tableModel.getValueAt(i, 1);
            Date fechaHoraFin = (Date) tableModel.getValueAt(i, 2);
            String distancia = (String) tableModel.getValueAt(i, 3);
//            String tipoVehiculo = (String) tableModel.getValueAt(i, 4);
            Vehiculo vehiculo = (Vehiculo)tableModel.getValueAt(i, 4);
            double gasolinaConsumida = Double.parseDouble((String) tableModel.getValueAt(i, 5));
            
            // Crear un nuevo objeto Viaje con los datos obtenidos y añadirlo a la lista.
            ViajeRealizado.Viaje nuevoViaje = new ViajeRealizado.Viaje(null, null, distancia, vehiculo, fechaHoraInicio, fechaHoraFin, gasolinaConsumida);
            listaViajes.add(nuevoViaje);
        }
        
        return listaViajes;
    }
public void cargarHistorial(ArrayList<ViajeRealizado.Viaje> historialViajes) {
    DefaultTableModel modelo = (DefaultTableModel) historialTable.getModel();
    modelo.setRowCount(0); // Limpia la tabla
    
    System.out.println("Cargando historial de viajes...");
    for (ViajeRealizado.Viaje viaje : historialViajes) {
        System.out.println("Agregando viaje al historial: " + viaje.getId());
        modelo.addRow(new Object[]{
            viaje.getId(),
            viaje.getFechaHoraInicio().toString(),
            viaje.getFechaHoraFin().toString(),
            viaje.getDistancia(),
            viaje.getVehiculo().getTipo(),
            String.valueOf(viaje.getGasolinaConsumida())
        });
    }
}

    
    
}