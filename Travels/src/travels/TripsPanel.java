package travels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class TripsPanel extends JPanel {
    private JButton btnEmpezarViaje = new JButton("Empezar Viaje");
    private TableRutes tableRutes;
    String puntoInicialSeleccionado, puntoFinalSeleccionado, tipoTransporteSeleccionado;
    public TripsPanel(TableRutes tableRutes) {
        this.tableRutes = tableRutes;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        this.setLayout(new FlowLayout());
        btnEmpezarViaje.addActionListener(e -> abrirVentanaConfiguracionViaje());
        add(btnEmpezarViaje);
    }

    private void abrirVentanaConfiguracionViaje() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Configuración del Viaje");
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10); // Margen para los componentes

        JComboBox<String> cbPuntoInicial = new JComboBox<>(tableRutes.inicios.toArray(new String[0]));
        JComboBox<String> cbPuntoFinal = new JComboBox<>(tableRutes.finals.toArray(new String[0]));
        JComboBox<String> cbTipoTransporte = new JComboBox<>(getTipoTransporteOptions());

        dialog.add(new JLabel("Punto Inicial:"), gbc);
        dialog.add(cbPuntoInicial, gbc);
        dialog.add(new JLabel("Punto Final:"), gbc);
        dialog.add(cbPuntoFinal, gbc);
        dialog.add(new JLabel("Tipo de Transporte:"), gbc);
        dialog.add(cbTipoTransporte, gbc);

        JButton btnGenerarViaje = new JButton("Generar Viaje");
        btnGenerarViaje.addActionListener(e -> {
            puntoInicialSeleccionado = (String) cbPuntoInicial.getSelectedItem();
            puntoFinalSeleccionado = (String) cbPuntoFinal.getSelectedItem();
            tipoTransporteSeleccionado = (String) cbTipoTransporte.getSelectedItem();
            System.out.println("Inicio: " + puntoInicialSeleccionado + " Final: " + puntoFinalSeleccionado + " Vehiculo: " + tipoTransporteSeleccionado);
            if (rutaExiste(puntoInicialSeleccionado, puntoFinalSeleccionado)) {
            // La ruta existe, puedes seguir con la lógica para generar el viaje
            System.out.println("Inicio: " + puntoInicialSeleccionado + " Final: " + puntoFinalSeleccionado + " Vehículo: " + tipoTransporteSeleccionado);
            JOptionPane.showMessageDialog(dialog, "Viaje generado correctamente.");
            dialog.dispose(); // Cierra la ventana de diálogo
            } else {
            // La ruta no existe, mostrar mensaje de error
            JOptionPane.showMessageDialog(dialog, "No existe una ruta entre los puntos seleccionados.", "Error", JOptionPane.ERROR_MESSAGE);
            // No se cierra la ventana de diálogo, permitiendo al usuario intentar de nuevo
        }
        
        });
        dialog.add(btnGenerarViaje, gbc);

        dialog.pack(); // Ajusta el tamaño de la ventana al contenido
        dialog.setLocationRelativeTo(null); // Centra la ventana
        dialog.setModal(true); // Hace que la ventana bloqueé el acceso a la ventana principal
        dialog.setVisible(true); // Muestra la ventana
    }

    private String[] getTipoTransporteOptions() {
        ArrayList<Vehiculo> vehiculos = (ArrayList<Vehiculo>) VehiculoManager.getVehiculos();
        String[] tipos = new String[vehiculos.size()];
        for (int i = 0; i < vehiculos.size(); i++) {
            tipos[i] = vehiculos.get(i).getTipo();
        }
        return tipos;
    }
    private boolean rutaExiste(String inicio, String fin) {
    for (RegistroCSV registro : tableRutes.registros) {
        if (registro.getInicio().equals(inicio) && registro.getFin().equals(fin)) {
            return true; // La ruta existe
        }
    }
    return false; // No se encontró la ruta
}

}
