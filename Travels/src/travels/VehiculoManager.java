package travels;

import java.util.ArrayList;
import java.util.List;

public class VehiculoManager {
    private static List<Vehiculo> vehiculos = new ArrayList<>();

    static {
        // Inicializa los vehículos con los datos específicos
        vehiculos.add(new Vehiculo("Motocicleta 1", 0.1, 6));
        vehiculos.add(new Vehiculo("Motocicleta 2", 0.1, 6));
        vehiculos.add(new Vehiculo("Motocicleta 3", 0.1, 6));
        vehiculos.add(new Vehiculo("Vehículo Estandar 1", 0.3, 10));
        vehiculos.add(new Vehiculo("Vehículo Estandar 2", 0.3, 10));
        vehiculos.add(new Vehiculo("Vehículo Estandar 3", 0.3, 10));
        vehiculos.add(new Vehiculo("Vehículo premium 1", 0.45, 12));
        vehiculos.add(new Vehiculo("Vehículo premium 2", 0.45, 12));
        vehiculos.add(new Vehiculo("Vehículo premium 3", 0.45, 12));
    }

    // Método para obtener la lista de vehículos
    public static List<Vehiculo> getVehiculos() {
        return vehiculos;
    }
}