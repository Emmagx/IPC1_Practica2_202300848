package travels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;

public class TripsPanel extends JPanel implements Serializable {
    private JButton btnEmpezarViaje;
    private TableRutes tableRutes;
    private JPanel viajePanel1, viajePanel2, viajePanel3;
    private HistorialViajesPanel historialPanel = new HistorialViajesPanel();
    private int position = -80;
    private VehiculoManager vehiculoManager; // Instancia de VehiculoManager

    public TripsPanel(TableRutes tableRutes) {
        this.tableRutes = tableRutes;
        this.vehiculoManager = new VehiculoManager(); // Crear la instancia de VehiculoManager
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(null); // Usando null layout para posiciones absolutas

        btnEmpezarViaje = new JButton("Empezar Viaje");
        btnEmpezarViaje.setBounds(300, 10, 150, 30); // Posición x, y y tamaño ancho, alto
        add(btnEmpezarViaje);

        // Asegúrate de que el botón de empezar viaje tenga un ActionListener para abrir la ventana de configuración
        btnEmpezarViaje.addActionListener(this::abrirVentanaConfiguracionViaje);
    }

    private void abrirVentanaConfiguracionViaje(ActionEvent e) {
        JDialog dialog = new JDialog((Frame) null, "Configuración del Viaje", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        JComboBox<String> cbPuntoInicial = new JComboBox<>(tableRutes.inicios.toArray(new String[0]));
        JComboBox<String> cbPuntoFinal = new JComboBox<>(tableRutes.finals.toArray(new String[0]));
        JComboBox<Vehiculo> cbTipoTransporte = new JComboBox<>(
                new DefaultComboBoxModel<>(vehiculoManager.obtenerVehiculosDisponibles().toArray(new Vehiculo[0])));

        dialog.add(new JLabel("Punto Inicial:"), gbc);
        dialog.add(cbPuntoInicial, gbc);
        dialog.add(new JLabel("Punto Final:"), gbc);
        dialog.add(cbPuntoFinal, gbc);
        dialog.add(new JLabel("Tipo de Transporte:"), gbc);
        dialog.add(cbTipoTransporte, gbc);

        JButton btnGenerarViaje = new JButton("Generar Viaje");
        btnGenerarViaje.addActionListener(event -> generarViaje(
                cbPuntoInicial, cbPuntoFinal, cbTipoTransporte, dialog));
        dialog.add(btnGenerarViaje, gbc);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void generarViaje(JComboBox<String> cbPuntoInicial,
                              JComboBox<String> cbPuntoFinal,
                              JComboBox<Vehiculo> cbTipoTransporte,
                              JDialog dialog) {
        String puntoInicialSeleccionado = (String) cbPuntoInicial.getSelectedItem();
        String puntoFinalSeleccionado = (String) cbPuntoFinal.getSelectedItem();
        Vehiculo vehiculoSeleccionado = (Vehiculo) cbTipoTransporte.getSelectedItem();

        if (rutaExiste(puntoInicialSeleccionado, puntoFinalSeleccionado) && vehiculoSeleccionado != null) {
            position += 150;
            int contadorHilo = 1;
            RegistroCSV ruta = registroID(puntoInicialSeleccionado, puntoFinalSeleccionado);
            vehiculoSeleccionado.setDisponible(false);
            actualizarListaVehiculos(cbTipoTransporte);

            // Crea un hilo para manejar el viaje
            Thread viajeThread = new Thread(() -> {
                JPanel panelViaje = crearPanelViaje(ruta, vehiculoSeleccionado);
                SwingUtilities.invokeLater(() -> {
                    this.add(panelViaje); // Añade el panel del viaje en el EDT
                    panelViaje.setBounds(0, position, 800, 100);
                    // Aumenta la posición para el siguiente panel
                    this.revalidate();
                    this.repaint();
                });

                System.out.println("Viaje Gerado con exito");
            }, "Viaje-" + contadorHilo);

            viajeThread.start(); // Inicia el hilo
            JOptionPane.showMessageDialog(dialog, "Viaje generado correctamente.");
            dialog.dispose();
        } else {
            JOptionPane.showMessageDialog(dialog, "No existe una ruta entre los puntos seleccionados.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

        private boolean rutaExiste(String inicio, String fin) {
        return tableRutes.registros.stream()
                .anyMatch(registro -> registro.getInicio().equals(inicio) && registro.getFin().equals(fin));
    }

    private RegistroCSV registroID(String inicio, String fin) {
        return tableRutes.registros.stream()
                .filter(registro -> registro.getInicio().equals(inicio) && registro.getFin().equals(fin))
                .findFirst().orElse(null);
    }

    private void actualizarListaVehiculos(JComboBox<Vehiculo> cbTipoTransporte) {
        ArrayList<Vehiculo> vehiculosDisponibles = vehiculoManager.obtenerVehiculosDisponibles(); // Llamada al método en la instancia de VehiculoManager
        cbTipoTransporte.setModel(new DefaultComboBoxModel<>(vehiculosDisponibles.toArray(new Vehiculo[0])));
    }

    private JPanel crearPanelViaje(RegistroCSV ruta, Vehiculo vehiculo) {
        
        JPanel panelViaje = new JPanel();
        JPanel detallesPanel = new JPanel();
        panelViaje.add(detallesPanel);
        panelViaje.setBackground(Color.WHITE);
        detallesPanel.setBackground(Color.WHITE);
        ViajeEnCurso animacionViaje = new ViajeEnCurso(vehiculo, ruta.getInicio(), ruta.getFin(), ruta.getDistancia(), historialPanel);
        panelViaje.add(animacionViaje);

        return panelViaje;
    }
    
}
