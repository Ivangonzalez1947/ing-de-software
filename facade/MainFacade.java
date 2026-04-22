package facade;

public class MainFacade {
    public static void main(String[] args) {

        JuegoFacade juego = new JuegoFacade();

        juego.iniciarJuego();
        juego.jugar();
        juego.guardarJuego();
        juego.cerrarJuego();
    }
}