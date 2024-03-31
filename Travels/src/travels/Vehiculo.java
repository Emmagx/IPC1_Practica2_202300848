package travels;

import javax.swing.ImageIcon;

public class Vehiculo {
    private String tipo;
    private double gastoCombustible; // galones por kilómetro
    private int capacidadTanque; // capacidad en galones
    private double combustibleActual;
    private boolean disponible; // Indica si el vehículo está disponible para un nuevo viaje
    private ImageIcon imagen; // Imagen para representar visualmente el vehículo

    public Vehiculo(String tipo, double gastoCombustible, int capacidadTanque, ImageIcon imagen) {
        this.tipo = tipo;
        this.gastoCombustible = gastoCombustible;
        this.capacidadTanque = capacidadTanque;
        this.combustibleActual = capacidadTanque; // inicialmente el tanque está lleno
        this.disponible = true; // inicialmente todos los vehículos están disponibles
        this.imagen = imagen;
    }

    // Métodos getter y setter

    public String getTipo() {
        return tipo;
    }

    public double getGastoCombustible() {
        return gastoCombustible;
    }

    public int getCapacidadTanque() {
        return capacidadTanque;
    }

    public double getCombustibleActual() {
        return combustibleActual;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setGastoCombustible(double gastoCombustible) {
        this.gastoCombustible = gastoCombustible;
    }

    public void setCapacidadTanque(int capacidadTanque) {
        this.capacidadTanque = capacidadTanque;
    }

    public void setCombustibleActual(double combustibleActual) {
        this.combustibleActual = combustibleActual;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }

    // Métodos para la simulación de viaje

    // Calcula la distancia máxima que puede recorrer el vehículo con un tanque lleno
    public double distanciaMaxima() {
        return capacidadTanque / gastoCombustible;
    }

    // Recargar el vehículo al máximo de su capacidad
    public void recargarCombustible() {
        combustibleActual = capacidadTanque;
    }

    // Método para consumir combustible basado en la distancia recorrida
    public void consumirCombustible(double distancia) {
        combustibleActual = Math.max(0, combustibleActual - distancia * gastoCombustible);
    }

    // Método para iniciar un viaje
    public void iniciarViaje(double distancia) {
        if (combustibleActual >= distancia * gastoCombustible) {
            consumirCombustible(distancia);
            setDisponible(false); // El vehículo no está disponible hasta que se complete el viaje o se cancele
        } else {
            throw new IllegalStateException("No hay suficiente combustible para el viaje.");
        }
    }

    // Método para finalizar un viaje
    public void finalizarViaje() {
        setDisponible(true); // El vehículo está disponible de nuevo
    }
    @Override
    public String toString() {
        return tipo; // Así es como el JComboBox mostrará cada vehículo.
    }
}
