// Azucar.java
public class Azucar extends DecoradorCafe {

    public Azucar(Cafe cafe) {
        super(cafe);
    }

    @Override
    public double costo() {
        return cafe.costo() + 10;
    }

    @Override
    public String descripcion() {
        return cafe.descripcion() + " + azúcar";
    }
}