public class Carrito {
    private String productos = "";

    public void agregarProducto(String producto) {
        this.productos += producto + " ";
    }

    public String mostrar() {
        return "Carrito: [ " + productos + "]";
    }

    public CarritoMemento guardar() {
        return new CarritoMemento(productos);
    }

    public void restaurar(CarritoMemento m) {
        this.productos = m.getEstado();
    }
}