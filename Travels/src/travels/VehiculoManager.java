package travels;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.util.List;

public class VehiculoManager {
    private static List<Vehiculo> vehiculos = new ArrayList<>();

    static {
        // Inicializa los vehículos con los datos específicos y sus imágenes
        vehiculos.add(new Vehiculo("Motocicleta 1", 0.1, 6, cargarImagen("images/motocicleta.png")));
        vehiculos.add(new Vehiculo("Motocicleta 2", 0.1, 6, cargarImagen("images/motocicleta.png")));
        vehiculos.add(new Vehiculo("Motocicleta 3", 0.1, 6, cargarImagen("images/motocicleta.png")));
        vehiculos.add(new Vehiculo("Vehículo Estandar 1", 0.3, 10, cargarImagen("images/standard.png")));
        vehiculos.add(new Vehiculo("Vehículo Estandar 2", 0.3, 10, cargarImagen("images/standard.png")));
        vehiculos.add(new Vehiculo("Vehículo Estandar 3", 0.3, 10, cargarImagen("images/standard.png")));
        vehiculos.add(new Vehiculo("Vehículo premium 1", 0.45, 12, cargarImagen("images/premium.png")));
        vehiculos.add(new Vehiculo("Vehículo premium 2", 0.45, 12, cargarImagen("images/premium.png")));
        vehiculos.add(new Vehiculo("Vehículo premium 3", 0.45, 12, cargarImagen("images/premium.png")));
    }

    private static ImageIcon cargarImagen(String ruta) {
        java.net.URL imgURL = VehiculoManager.class.getClassLoader().getResource(ruta);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("No se pudo encontrar el archivo: " + ruta);
            return null;
        }
    }


    // Método para obtener la lista de vehículos
    public static List<Vehiculo> getVehiculos() {
        return vehiculos;
    }
    
    public static Vehiculo buscarVehiculoPorTipo(String tipoVehiculo) {
    for (Vehiculo vehiculo : vehiculos) {
        if (vehiculo.getTipo().equals(tipoVehiculo) && vehiculo.isDisponible()) {
            vehiculo.setDisponible(false); // El vehículo ya no está disponible porque se usará para el viaje
            return vehiculo;
        }
    }
    return null; // No se encontró un vehículo disponible de ese tipo
}
    
    public static ArrayList<Vehiculo> obtenerVehiculosDisponibles() {
    ArrayList<Vehiculo> disponibles = new ArrayList<>();
    for (Vehiculo vehiculo : vehiculos) {
        if (vehiculo.isDisponible()) {
            disponibles.add(vehiculo);
        }
    }
    return disponibles;
}
}
