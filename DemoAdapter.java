// Interfaz que espera el cliente moderno
interface PagoModerno {
     // Método público que recibe una cantidad en double 
     //y devuelve true si el pago fue exitoso
    boolean pagar(double cantidad);
}

// Clase legacy (existente, no podemos modificarla)
class SistemaPagoLegacy {
    public String hacerPago(String montoStr) {
        // Simula procesamiento en el sistema antiguo
        System.out.println("Legacy: Procesando pago de " + montoStr + " pesos argentinos");
        return "OK";
    }
}

// Adaptador: convierte la interfaz moderna a la legacy
class AdaptadorPago implements PagoModerno {
    // referencia al sistema legacy que vamos a reutilizar
    private SistemaPagoLegacy legacy;
    // Recibimos la instancia del sistema legacy 
    // por inyección (permite reusar y testear)
    public AdaptadorPago(SistemaPagoLegacy legacy) {
        this.legacy = legacy;
    }

    @Override
    public boolean pagar(double cantidad) {
        // Convertir double a String con formato
        String montoStr = String.format("%.2f", cantidad);
        // Delegamos al método legacy que espera un String
        String resultado = legacy.hacerPago(montoStr);
        // Interpretamos la respuesta del legacy: "OK" significa éxito
        return "OK".equals(resultado);
    }
}

// Demostración del patrón Adapter
public class DemoAdapter {
    public static void main(String[] args) {
        SistemaPagoLegacy sistemaViejo = new SistemaPagoLegacy();
        // Creamos el adaptador que implementa la interfaz moderna pero delega al legacy
        PagoModerno adaptador = new AdaptadorPago(sistemaViejo);
        
        // El cliente usa la interfaz moderna sin conocer el sistema legacy
        boolean exito = adaptador.pagar(49.99);
        // Mostramos el resultado en formato legible para el usuario moderno
        System.out.println("Resultado del pago (moderno): " + (exito ? "Éxito" : "Fallo"));
    }
}