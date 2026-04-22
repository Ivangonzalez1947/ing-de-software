/**
 * PATRÓN BRIDGE - EXAMEN INGENIERÍA DE SOFTWARE
 * Concepto: Desacoplar una abstracción de su implementación para que ambas 
 * puedan variar independientemente.
 */

// 1. IMPLEMENTOR (La interfaz que define qué puede hacer cualquier aparato)
interface Dispositivo {
    void setEncendido(boolean estado);
    boolean estaEncendido();
    void setVolumen(int vol);
    int getVolumen();
}

// 2. CONCRETE IMPLEMENTORS (Las implementaciones reales de los aparatos)
class TV implements Dispositivo {
    private boolean encendido = false;
    private int volumen = 10;

    public void setEncendido(boolean e) { this.encendido = e; }
    public boolean estaEncendido() { return encendido; }
    public void setVolumen(int v) { this.volumen = v; }
    public int getVolumen() { return volumen; }
}

class Radio implements Dispositivo {
    private boolean encendido = false;
    private int volumen = 5;

    public void setEncendido(boolean e) { this.encendido = e; }
    public boolean estaEncendido() { return encendido; }
    public void setVolumen(int v) { this.volumen = v; }
    public int getVolumen() { return volumen; }
}

// 3. ABSTRACTION (La lógica de control que "usa" el puente)
class Control {
    // EL PUENTE: Referencia a la interfaz, no a una clase concreta
    protected Dispositivo dispositivo; 

    public Control(Dispositivo d) {
        this.dispositivo = d;
    }

    public void botonPoder() {
        if (dispositivo.estaEncendido()) {
            dispositivo.setEncendido(false);
            System.out.println("LOG: Dispositivo apagado.");
        } else {
            dispositivo.setEncendido(true);
            System.out.println("LOG: Dispositivo encendido.");
        }
    }
}

// 4. REFINED ABSTRACTION (Evolución del control sin tocar los dispositivos)
class ControlAvanzado extends Control {
    public ControlAvanzado(Dispositivo d) {
        super(d);
    }

    public void silenciar() {
        dispositivo.setVolumen(0);
        System.out.println("LOG: Volumen en 0 (Mute activado).");
    }
}

// 5. CLASE PRINCIPAL (Punto de entrada)
public class Main {
    public static void main(String[] args) {
        System.out.println("=== INICIO PRUEBA PATRON BRIDGE ===");

        // Probamos con una TV
        System.out.println("\n[Probando con TV]");
        Dispositivo miTv = new TV();
        ControlAvanzado controlTv = new ControlAvanzado(miTv);
        controlTv.botonPoder();
        controlTv.silenciar();

        // Probamos con una Radio (El mismo control sirve para cualquier dispositivo)
        System.out.println("\n[Probando con Radio]");
        Dispositivo miRadio = new Radio();
        ControlAvanzado controlRadio = new ControlAvanzado(miRadio);
        controlRadio.botonPoder();
        controlRadio.silenciar();
        
        System.out.println("\n=== PRUEBA FINALIZADA EXITOSAMENTE ===");
    }
}