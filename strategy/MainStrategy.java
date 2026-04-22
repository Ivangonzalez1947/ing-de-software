package strategy;

public class MainStrategy {
    public static void main(String[] args) {

        Personaje personaje = new Personaje();

        personaje.setMovimiento(new Caminar());
        personaje.mover();

        personaje.setMovimiento(new Volar());
        personaje.mover();

        personaje.setMovimiento(new Nadar());
        personaje.mover();
    }
}