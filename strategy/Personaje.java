package strategy;

public class Personaje {

    private Movimiento movimiento;

    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    public void mover() {
        movimiento.mover();
    }
}