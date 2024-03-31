package travels;

import java.awt.Color;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class GUI extends JFrame {
    private TableRutes tableRutes;
    private HistorialViajesPanel historial;

    public GUI(EstadoAplicacion estadoCargado) {
        setTitle("Aplicación de Viajes");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        JTabbedPane tabbedPane = new JTabbedPane();
        
        this.tableRutes = new TableRutes();
        this.historial = new HistorialViajesPanel();

        TripsPanel tripsPanel = new TripsPanel(tableRutes);
        tableRutes.setTripsPanel(tripsPanel); // Establecer la conexión entre TableRutes y TripsPanel

        tabbedPane.add("Rutas", tableRutes.new SubirCSV());
        tabbedPane.add("Viajes", tripsPanel);
        tabbedPane.add("Historial", historial);
        
        add(tabbedPane);

    if (estadoCargado != null) {
        tableRutes.cargarRegistros(estadoCargado.getRegistrosRutas());
        historial.cargarHistorial(estadoCargado.getHistorialViajes());
    } else {
        // Aquí inicializas tus tablas para empezar con ellas vacías si no hay datos guardados
        tableRutes.cargarRegistros(new ArrayList<>());
        historial.cargarHistorial(new ArrayList<>());
    }

        // Guardar estado al cerrar
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Aquí capturamos el estado actual antes de cerrar
                ArrayList<RegistroCSV> registrosActuales = tableRutes.getRegistros();
                ArrayList<ViajeRealizado.Viaje> historialActuales = historial.getHistorial();
                EstadoAplicacion estado = new EstadoAplicacion(registrosActuales, historialActuales);
                Travels.guardarEstado(estado, "estadoAplicacion.bin");
            }
        });

        setVisible(true);
    }
    public void agregarNuevoViaje(RegistroCSV nuevoRegistro, ViajeRealizado.Viaje nuevoViaje) {
        // Agrega a los registros y al historial
        EstadoAplicacion estado = null;
        estado.getRegistrosRutas().add(nuevoRegistro);
        estado.getHistorialViajes().add(nuevoViaje);

        // Actualiza las tablas
        tableRutes.cargarRegistros(estado.getRegistrosRutas());
        historial.cargarHistorial(estado.getHistorialViajes());

        // Guarda el nuevo estado
        Travels.guardarEstado(estado, "estadoAplicacion.bin");
    }
}
