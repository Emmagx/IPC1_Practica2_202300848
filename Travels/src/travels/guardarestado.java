public class UtilidadSerializacion {
    public static void guardarEstado(EstadoAplicacion estado, String archivoDestino) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(archivoDestino))) {
            out.writeObject(estado);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static EstadoAplicacion cargarEstado(String archivoOrigen) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(archivoOrigen))) {
            return (EstadoAplicacion) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
