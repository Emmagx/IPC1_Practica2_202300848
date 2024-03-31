package travels;

import java.io.Serializable;
import java.util.ArrayList;

public class EstadoAplicacion implements Serializable {
    private ArrayList<RegistroCSV> registrosRutas;
    private ArrayList<ViajeRealizado.Viaje> historialViajes;

    // Constructor
    public EstadoAplicacion(ArrayList<RegistroCSV> registrosRutas, ArrayList<ViajeRealizado.Viaje> historialViajes) {
        this.registrosRutas = registrosRutas;
        this.historialViajes = historialViajes;
    }

    // Getters y setters
    public ArrayList<RegistroCSV> getRegistrosRutas() {
        return registrosRutas;
    }

    public void setRegistrosRutas(ArrayList<RegistroCSV> registrosRutas) {
        this.registrosRutas = registrosRutas;
    }

    public ArrayList<ViajeRealizado.Viaje> getHistorialViajes() {
        return historialViajes;
    }

    public void setHistorialViajes(ArrayList<ViajeRealizado.Viaje> historialViajes) {
        this.historialViajes = historialViajes;
    }

    // Puedes agregar más campos y métodos según sea necesario
}
