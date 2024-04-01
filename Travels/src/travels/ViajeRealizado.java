package travels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import java.util.UUID;
public class ViajeRealizado implements Serializable{
    
    private final ArrayList<Viaje> historialViajes;
    private final String[] columnNames = {"ID", "Fecha y Hora Inicio", "Fecha y Hora Fin", "Distancia (km)", "Vehículo", "Gasolina Consumida"};

    public ViajeRealizado() {
        historialViajes = new ArrayList<>();
    }

    public DefaultTableModel crearModeloTabla() {
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for (Viaje viaje : historialViajes) {
            Object[] row = new Object[]{
                    viaje.getId(),
                    viaje.getFechaHoraInicio(),
                    viaje.getFechaHoraFin(),
                    viaje.getDistancia(),
                    viaje.getVehiculo().getTipo(),
                    viaje.getGasolinaConsumida()
            };
            tableModel.addRow(row);
        }
        return tableModel;
    }

    public void agregarViaje(Viaje viaje) {
        historialViajes.add(viaje);
    }
    

    // Extending RegistroCSV to include additional attributes for a completed route
public static class Viaje extends RegistroCSV {
    private final String id;
    private final Date fechaHoraInicio;
    private final Date fechaHoraFin;
    private final double gasolinaConsumida;
    private final Vehiculo vehiculo; // Atributo agregado para Vehiculo

    public Viaje(String inicio, String fin, String distancia, Vehiculo vehiculo, Date fechaHoraInicio, Date fechaHoraFin, double gasolinaConsumida) {
        super(UUID.randomUUID().toString(), inicio, fin, distancia); // Aquí se genera el ID único
        this.id = super.getId(); // Obtiene el ID generado por RegistroCSV
        this.vehiculo = vehiculo;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.gasolinaConsumida = gasolinaConsumida;
    }

    // Getters para los nuevos atributos
    public Date getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public Date getFechaHoraFin() {
        return fechaHoraFin;
    }

    public double getGasolinaConsumida() {
        return gasolinaConsumida;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }
    
}
}
