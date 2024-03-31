package travels;

import java.io.Serializable;

public class RegistroCSV implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String inicio;
    private String fin;
    private String distancia;

    public RegistroCSV(String id, String inicio, String fin, String distancia) {
        this.id = id;
        this.inicio = inicio;
        this.fin = fin;
        this.distancia = distancia;
    }

    //getters y setters
        public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }
     @Override
    public String toString() {
        return "RegistroCSV{" +
                "id='" + id + '\'' +
                ", inicio='" + inicio + '\'' +
                ", fin='" + fin + '\'' +
                ", distancia='" + distancia + '\'' +
                '}';
    }
    
}