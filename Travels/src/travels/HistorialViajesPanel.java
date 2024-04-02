package travels;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import travels.ViajeRealizado.Viaje;

public class HistorialViajesPanel extends JPanel implements Serializable {
    private JTable historialTable;
    private DefaultTableModel tableModel;
    private ArrayList<Viaje> listaViajes;

    public HistorialViajesPanel() {
        listaViajes = new ArrayList<>();
        setLayout(new BorderLayout());
        initUIComponents();
//        actualizarTabla(); // Asegúrate de actualizar la tabla inicialmente con los datos actuales
    }

private void initUIComponents() {
    JLabel titleLabel = new JLabel("Historial de Viajes", SwingConstants.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
    titleLabel.setOpaque(true);
    titleLabel.setBackground(new Color(153, 255, 153));
    titleLabel.setPreferredSize(new Dimension(400, 50));
    add(titleLabel, BorderLayout.NORTH);

String[] columnNames = {"#", "Fecha y Hora Inicio", "Fecha y Hora Fin", "Distancia (km)", "Vehículo", "Gasolina Consumida"};
    tableModel = new DefaultTableModel(columnNames, 0);
    historialTable = new JTable(tableModel);
    
    // Asegúrate de que la tabla esté envuelta en un JScrollPane
    JScrollPane scrollPane = new JScrollPane(historialTable);
    add(scrollPane, BorderLayout.CENTER);
    validate();
    repaint();
}

public void agregarViajeYActualizarTabla(Viaje viaje) {
    SwingUtilities.invokeLater(() -> {
        System.out.println("Cambiamos de clase a la que hara la tabla");
        listaViajes.add(viaje); // Añade el nuevo viaje a la lista
        actualizarTabla(); // Actualiza la tabla con la lista actualizada
    });
}


    public void cargarHistorial(ArrayList<Viaje> historialViajes) {
        SwingUtilities.invokeLater(() -> {
            listaViajes.clear();
            listaViajes.addAll(historialViajes);
            actualizarTabla();
        });
    }
public void actualizarTabla() {
    DefaultTableModel modelo = (DefaultTableModel) historialTable.getModel();
    modelo.setRowCount(0); // Limpia la tabla completamente
    
    int contador = 1; // Inicializa un contador para seguir la iteración

    for (Viaje v : listaViajes) {
        System.out.println("Iteración de objeto No. " + contador); // Muestra el contador actual
        
        // Convierte todo a String y maneja valores nulos
        String id = Integer.toString(contador); // Usa el contador como ID
        String fechaHoraInicio = v.getFechaHoraInicio().toString();
        String fechaHoraFin = v.getFechaHoraFin().toString();
        String distancia = v.getDistancia();
        String vehiculoTipo = v.getVehiculo().getTipo();
        String gasolinaConsumida = String.format("%.2f", v.getGasolinaConsumida());
        
        System.out.println(id+fechaHoraInicio + fechaHoraFin + distancia + vehiculoTipo + gasolinaConsumida);

        // Añade la fila al modelo de la tabla
        modelo.addRow(new Object[]{id, fechaHoraInicio, fechaHoraFin, distancia, vehiculoTipo, gasolinaConsumida});
        System.out.println("numero de columnas " +modelo.getRowCount());
        contador++; // Incrementa el contador después de cada iteración
    }
    
    // Añade una fila de prueba para asegurar que el modelo se está actualizando correctamente
//    modelo.addRow(new Object[]{"Test", "Test", "Test", "Test", "Test", "Test"});
    System.out.println("Numero de columnas "+ historialTable.getRowCount());
    // Estas llamadas pueden ser innecesarias si el modelo ya está vinculado a la tabla y se actualiza automáticamente
//    historialTable.setModel(modelo);
    historialTable.revalidate();
    historialTable.repaint();
}




    public ArrayList<Viaje> getListaViajes() {
        return new ArrayList<>(listaViajes);
    }
}