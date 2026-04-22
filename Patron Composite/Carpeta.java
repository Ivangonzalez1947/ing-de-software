
// Carpeta.java
import java.util.ArrayList;
import java.util.List;

public class Carpeta implements Componente {

    private String nombre;
    private List<Componente> elementos = new ArrayList<>(); // Corazón del patrón

    public Carpeta(String nombre) {
        this.nombre = nombre;
    }

    public void agregar(Componente componente) {
        elementos.add(componente);
    }

    @Override
    public void mostrar(String indentacion) {
        System.out.println(indentacion + "+ Carpeta: " + nombre);

        for (Componente c : elementos) {
            c.mostrar(indentacion + "   ");
        }
    }
}