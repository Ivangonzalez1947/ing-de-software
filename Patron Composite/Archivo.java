// Archivo.java
public class Archivo implements Componente {

    private String nombre;

    public Archivo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void mostrar(String indentacion) {
        System.out.println(indentacion + "- Archivo: " + nombre);
    }
}