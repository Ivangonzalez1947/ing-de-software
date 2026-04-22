// Leche.java
public class Leche extends DecoradorCafe {

    public Leche(Cafe cafe) {
        super(cafe);
    }

    @Override
    public double costo() {
        return cafe.costo() + 20;
    }

    @Override
    public String descripcion() {
        return cafe.descripcion() + " + leche";
    }
}