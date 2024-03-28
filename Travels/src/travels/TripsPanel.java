package travels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TripsPanel extends JPanel {
    private JButton btnGenerarViaje;
    private ArrayList<RegistroCSV> registros;
    private ArrayList<Vehiculo> flotaVehiculos;
    private JComboBox<String> cbPuntoInicial;
    private JComboBox<String> cbPuntoFinal;
    private JComboBox<String> cbTipoTransporte;
    private Set<String> puntosInicio = new HashSet<>();
    private Set<String> puntosFin = new HashSet<>();
    
    public TripsPanel(ArrayList<RegistroCSV> registros, ArrayList<Vehiculo> flotaVehiculos) {
        this.registros = registros;
        this.flotaVehiculos = flotaVehiculos;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        
        cbPuntoInicial = new JComboBox<>();
        cbPuntoFinal = new JComboBox<>();
        cbTipoTransporte = new JComboBox<>(getTipoTransporteOptions());

        btnGenerarViaje = new JButton("Generar Viaje");
        btnGenerarViaje.addActionListener(e -> abrirVentanaGenerarViaje());
        setLayout(new FlowLayout());
        add(btnGenerarViaje);
    }

    private void abrirVentanaGenerarViaje() { // Asegúrate de que el método coincida con la firma del actionListener
        actualizarComboBoxes(); 
        JDialog dialog = new JDialog();
        dialog.setTitle("Generar Viaje");
        dialog.setResizable(false);
        dialog.setLayout(new GridLayout(0, 2, 10, 10)); // Puedes ajustar esto según tus necesidades de diseño

        // Poblar los comboboxes con datos actualizados


        dialog.add(new JLabel("Seleccionar punto inicial:"));
        dialog.add(cbPuntoInicial);
        dialog.add(new JLabel("Seleccionar punto final:"));
        dialog.add(cbPuntoFinal);
        dialog.add(new JLabel("Seleccionar tipo de transporte:"));
        dialog.add(cbTipoTransporte);

        JButton btnGenerar = new JButton("Generar Viaje");
        btnGenerar.addActionListener(this::accionGenerarViaje); // Revisa que el método sea llamado aquí
        dialog.add(btnGenerar);

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setModal(true);
        dialog.setVisible(true);
    }


        private void accionGenerarViaje(ActionEvent e) {
                // Implementa la lógica para manejar la generación del viaje aquí
                // ...
                JOptionPane.showMessageDialog(this, "Viaje generado correctamente.");
                ((Window) SwingUtilities.getRoot((JButton) e.getSource())).dispose(); // Cierra la ventana de diálogo
            }

        private void extractUniquePoints() {
                puntosInicio.clear();
                puntosFin.clear();
                for (RegistroCSV registro : registros) {
                    puntosInicio.add(registro.getInicio());
                    puntosFin.add(registro.getFin());
                }
            }
    private void actualizarComboBoxes() {
        extractUniquePoints(); // Actualiza los sets de puntos
        cbPuntoInicial.setModel(new DefaultComboBoxModel<>(puntosInicio.toArray(String[]::new)));
        cbPuntoFinal.setModel(new DefaultComboBoxModel<>(puntosFin.toArray(String[]::new)));
    }

        public void actualizarDatos(ArrayList<RegistroCSV> nuevosRegistros) {
            this.registros = nuevosRegistros; // Actualiza la lista de registros
            actualizarComboBoxes(); // Actualiza los JComboBox con los nuevos datos
        }
    
    
    private String[] getTipoTransporteOptions() {
        ArrayList<Vehiculo> vehiculos = (ArrayList<Vehiculo>) VehiculoManager.getVehiculos();
        String[] tipos = new String[vehiculos.size()];
        for (int i = 0; i < vehiculos.size(); i++) {
            tipos[i] = vehiculos.get(i).getTipo();
            
        }
        return tipos;
    }
    public void actualizarRegistrosYComboBoxes(ArrayList<RegistroCSV> nuevosRegistros) {
    this.registros = nuevosRegistros;
    actualizarComboBoxes(); // Suponiendo que este método actualiza los JComboBoxes
}
}
