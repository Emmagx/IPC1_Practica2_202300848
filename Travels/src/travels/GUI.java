package travels;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class GUI extends JFrame {
    private JTabbedPane tabbedPane;
    private ArrayList<RegistroCSV> rutasDisponibles; // Deberías llenar esta lista con tus rutas
    private ArrayList<Vehiculo> flotaVehiculos; // Deberías llenar esta lista con tus vehículos

    public GUI() {
        super("UBER");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null); // Centrar la ventana

        // Aquí deberías inicializar tus listas de rutas y vehículos
        rutasDisponibles = obtenerRutas(); 
        flotaVehiculos = obtenerVehiculos(); 

        tabbedPane = new JTabbedPane();

        // Añadir panel para gestionar rutas
        JPanel panelRutas = new TableRutes().new SubirCSV();
        tabbedPane.addTab("Rutas", panelRutas);

        // Añadir panel para gestionar viajes, pasando las listas necesarias al constructor
        TripsPanel panelViajes = new TripsPanel(rutasDisponibles, flotaVehiculos);
        tabbedPane.addTab("Viajes", panelViajes);

        // Añadir el JTabbedPane al JFrame
        this.add(tabbedPane);
        this.setVisible(true);
    }
    private ArrayList<RegistroCSV> obtenerRutas() {
    ArrayList<RegistroCSV> rutas = new ArrayList<>();
    // Simulando la carga de datos desde un archivo CSV
    try (BufferedReader br = new BufferedReader(new FileReader("rutas.csv"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length == 4) {
                RegistroCSV ruta = new RegistroCSV(data[0], data[1], data[2], data[3]);
                rutas.add(ruta);
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return rutas;
}

private ArrayList<Vehiculo> obtenerVehiculos() {
    // En este caso, simplemente devolvemos una lista inicializada de manera dura para ilustrar
    ArrayList<Vehiculo> vehiculos = (ArrayList<Vehiculo>) VehiculoManager.getVehiculos();
    
    return vehiculos;
}

}