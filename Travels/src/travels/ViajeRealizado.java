package travels;

import java.time.LocalDateTime;

public class ViajeRealizado extends RegistroCSV {
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private double gasolinaConsumida;

    public ViajeRealizado(RegistroCSV registro, LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin, double gasolinaConsumida) {
        super(registro.getId(), registro.getInicio(), registro.getFin(), registro.getDistancia());
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFin = fechaHoraFin;
        this.gasolinaConsumida = gasolinaConsumida;
    }

    // Getters y setters
    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public double getGasolinaConsumida() {
        return gasolinaConsumida;
    }

    public void setGasolinaConsumida(double gasolinaConsumida) {
        this.gasolinaConsumida = gasolinaConsumida;
    }
}
