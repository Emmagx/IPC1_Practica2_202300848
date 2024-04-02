package travels;

import java.io.Serializable;

public class EstadoViaje implements Serializable {
    // Atributos
    private String inicioRuta;
    private String finRuta;
    private String tipoVehiculo;
    private double distanciaTotal;
    private double gasolinaConsumida;
    private int posicionX; // Asumiendo que se refiere a la posición en una animación o algo similar.

    // Constructor
    public EstadoViaje(String inicioRuta, String finRuta, String tipoVehiculo, String distanciaTotal1, double distanciaTotal, int posicionX) {
        this.inicioRuta = inicioRuta;
        this.finRuta = finRuta;
        this.tipoVehiculo = tipoVehiculo;
        this.distanciaTotal = distanciaTotal;
        this.gasolinaConsumida = gasolinaConsumida;
        this.posicionX = posicionX;
    }

    // Getters
    public String getInicioRuta() {
        return inicioRuta;
    }

    public String getFinRuta() {
        return finRuta;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public double getDistanciaTotal() {
        return distanciaTotal;
    }

    public double getGasolinaConsumida() {
        return gasolinaConsumida;
    }

    public int getPosicionX() {
        return posicionX;
    }

    // Setters
    public void setInicioRuta(String inicioRuta) {
        this.inicioRuta = inicioRuta;
    }

    public void setFinRuta(String finRuta) {
        this.finRuta = finRuta;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public void setDistanciaTotal(double distanciaTotal) {
        this.distanciaTotal = distanciaTotal;
    }

    public void setGasolinaConsumida(double gasolinaConsumida) {
        this.gasolinaConsumida = gasolinaConsumida;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }
}
