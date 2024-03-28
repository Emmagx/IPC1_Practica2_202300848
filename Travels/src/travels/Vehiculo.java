package travels;

public class Vehiculo {
    private String tipo;
    private double gastoCombustible; // galones por kilómetro
    private int capacidadTanque; // capacidad en galones
    private double combustibleActual;
    private boolean disponible; // Indica si el vehículo está disponible para un nuevo viaje

    public Vehiculo(String tipo, double gastoCombustible, int capacidadTanque) {
        this.tipo = tipo;
        this.gastoCombustible = gastoCombustible;
        this.capacidadTanque = capacidadTanque;
        this.combustibleActual = capacidadTanque; // inicialmente el tanque está lleno
        this.disponible = true; // inicialmente todos los vehículos están disponibles
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
            // Después de iniciar un viaje, el vehículo no está disponible hasta que se complete el viaje o se cancele
            setDisponible(false);
        } else {
            throw new IllegalStateException("No hay suficiente combustible para el viaje.");
        }
    }

    // Método para finalizar un viaje
    public void finalizarViaje() {
        // Marcar el vehículo como disponible nuevamente después de finalizar el viaje
        setDisponible(true);
    }
}
