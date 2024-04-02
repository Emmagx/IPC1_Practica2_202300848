package travels;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class Travels {
    private static GUI gui;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EstadoAplicacion estadoCargado = cargarEstado("estadoAplicacion.bin");
            // Si no hay estado previo cargado, inicializa uno nuevo con valores predeterminados
            if (estadoCargado == null) {
                estadoCargado = new EstadoAplicacion(new ArrayList<>(), new ArrayList<>(), "");
            }
            gui = new GUI(estadoCargado);
            setGUI(gui);
        });
    }

    public static void setGUI(GUI gui) {
        Travels.gui = gui;
    }

    public static void guardarEstado(EstadoAplicacion estado, String archivoDestino) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivoDestino))) {
            out.writeObject(estado);
        } catch (IOException e) {
            e.printStackTrace();
            // Aquí podrías agregar lógica adicional para manejar el error, por ejemplo, mostrar un mensaje al usuario.
        }
    }

    public static EstadoAplicacion cargarEstado(String archivoOrigen) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivoOrigen))) {
            return (EstadoAplicacion) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            // Si hay un error al cargar, devuelve null para que se inicialice un estado nuevo
            return null;
        }
    }
}
