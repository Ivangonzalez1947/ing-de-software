package facade;

public class JuegoFacade {

    private MotorGrafico motor;
    private SistemaSonido sonido;
    private SistemaGuardado guardado;

    public JuegoFacade() {
        motor = new MotorGrafico();
        sonido = new SistemaSonido();
        guardado = new SistemaGuardado();
    }

    public void iniciarJuego() {
        System.out.println("Iniciando juego...");
        motor.iniciar();
        sonido.iniciar();
    }

    public void jugar() {
        motor.renderizar();
        sonido.reproducir();
    }

    public void guardarJuego() {
        guardado.guardarPartida();
    }

    public void cerrarJuego() {
        System.out.println("Cerrando juego...");
        motor.apagar();
        sonido.apagar();
    }
}