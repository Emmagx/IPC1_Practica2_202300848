package travels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class TripsPanel extends JPanel {
    private JButton btnEmpezarViaje = new JButton("Empezar Viaje");
    private TableRutes tableRutes;

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
            JOptionPane.showMessageDialog(dialog, "Viaje generado correctamente.");
            dialog.dispose(); // Cierra la ventana de diálogo
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
}
