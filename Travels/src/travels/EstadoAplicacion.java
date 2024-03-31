package travels;

import java.io.Serializable;
import java.util.ArrayList;

public class EstadoAplicacion implements Serializable {
    private ArrayList<RegistroCSV> registrosRutas;
    private ArrayList<ViajeRealizado.Viaje> historialViajes;
    private String rutaArchivoCSV;
    // Constructor
    public EstadoAplicacion(ArrayList<RegistroCSV> registrosRutas, ArrayList<ViajeRealizado.Viaje> historialViajes, String rutaArchivoCSV) {
        this.registrosRutas = registrosRutas;
        this.historialViajes = historialViajes;
        this.rutaArchivoCSV = rutaArchivoCSV;
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
    public String getRutaArchivoCSV() {
        return rutaArchivoCSV;
    }

    public void setRutaArchivoCSV(String rutaArchivoCSV) {
        this.rutaArchivoCSV = rutaArchivoCSV;
    }
}
