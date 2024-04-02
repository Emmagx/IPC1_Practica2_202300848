package travels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import javax.swing.table.DefaultTableModel;

public class ViajeRealizado implements Serializable{
    
    private ArrayList<Viaje> historialViajes;

    public ViajeRealizado(ArrayList<Viaje> listaViajes) {
        // Usas la lista que pasas como argumento
        this.historialViajes = listaViajes;
    }

    // Extending RegistroCSV to include additional attributes for a completed route
    public static class Viaje extends RegistroCSV {
        
        private final Date fechaHoraInicio;
        private final Date fechaHoraFin;
        private final double gasolinaConsumida;
        private final Vehiculo vehiculo; // Added attribute for Vehiculo
        
        public Viaje(String inicio, String fin, String distancia, Vehiculo vehiculo, Date fechaHoraInicio, Date fechaHoraFin, double gasolinaConsumida) {
            super(UUID.randomUUID().toString(), inicio, fin, distancia);
            this.vehiculo = vehiculo;
            this.fechaHoraInicio = fechaHoraInicio;
            this.fechaHoraFin = fechaHoraFin;
            this.gasolinaConsumida = gasolinaConsumida;
        }

        // Getters for new attributes
        public Date getFechaHoraInicio() {
            return fechaHoraInicio;
        }

        public Date getFechaHoraFin() {
            return fechaHoraFin;
        }

        public double getGasolinaConsumida() {
            return gasolinaConsumida;
        }

        // Getter for Vehiculo
        public Vehiculo getVehiculo() {
            return vehiculo;
        }
        
    }
}