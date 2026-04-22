// CafeSimple.java
public class CafeSimple implements Cafe {

    @Override
    public double costo() {
        return 100;
    }

    @Override
    public String descripcion() {
        return "Café simple";
    }
}