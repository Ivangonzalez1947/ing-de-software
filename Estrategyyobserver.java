import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ============ PATRON STRATEGY ============
interface Estrategia {
    void ejecutar(String datos);
}

class EstrategiaA implements Estrategia {
    @Override
    public void ejecutar(String datos) {
        System.out.println("  ▶️ Aplicando Estrategia A: " + datos.toUpperCase());
    }
}

class EstrategiaB implements Estrategia {
    @Override
    public void ejecutar(String datos) {
        System.out.println("  ▶️ Aplicando Estrategia B: " + new StringBuilder(datos).reverse().toString());
    }
}

class EstrategiaC implements Estrategia {
    @Override
    public void ejecutar(String datos) {
        System.out.println("  ▶️ Aplicando Estrategia C: " + datos.length() + " caracteres");
    }
}

// ============ PATRON OBSERVER - SUJETO OBSERVABLE ============
// Interfaz Observer
interface Observador {
    void actualizar(String evento, Object datos);
}

// Sujeto Observable: es el objeto que puede cambiar y notificar a los observadores
class ProcesadorDatos {
    private String nombre;
    private String estadoActual;
    private Estrategia estrategiaActual;
    private List<Observador> observadores;
    private List<String> historialProcesados;
    
    public ProcesadorDatos(String nombre) {
        this.nombre = nombre;
        this.estadoActual = "INICIALIZADO";
        this.observadores = new ArrayList<>();
        this.historialProcesados = new ArrayList<>();
        this.estrategiaActual = new EstrategiaA();
        notificarObservadores("CREACION", "Procesador creado con estrategia A");
    }
    
    // ========== MÉTODOS DEL OBSERVABLE ==========
    public void agregarObservador(Observador obs) {
        observadores.add(obs);
        notificarObservadores("OBSERVADOR_AGREGADO", obs.getClass().getSimpleName());
    }
    
    public void eliminarObservador(Observador obs) {
        if (observadores.remove(obs)) {
            notificarObservadores("OBSERVADOR_ELIMINADO", obs.getClass().getSimpleName());
        }
    }
    
    private void notificarObservadores(String evento, Object datos) {
        System.out.println("\n📢 [" + nombre + "] Notificando evento: " + evento);
        for (Observador obs : observadores) {
            obs.actualizar(evento, datos);
        }
    }
    
    // ========== MÉTODOS DE NEGOCIO (CAUSAN NOTIFICACIONES) ==========
    
    // Cambiar estrategia - ESTO NOTIFICA A LOS OBSERVADORES
    public void cambiarEstrategia(Estrategia nuevaEstrategia) {
        Estrategia anterior = this.estrategiaActual;
        this.estrategiaActual = nuevaEstrategia;
        notificarObservadores("ESTRATEGIA_CAMBIADA", 
            "De " + anterior.getClass().getSimpleName() + " a " + nuevaEstrategia.getClass().getSimpleName());
    }
    
    // Procesar datos - ESTO NOTIFICA A LOS OBSERVADORES
    public void procesar(String datos) {
        System.out.println("\n🔄 Procesando: \"" + datos + "\"");
        this.estadoActual = "PROCESANDO";
        notificarObservadores("INICIO_PROCESO", datos);
        
        // Aplicar estrategia
        estrategiaActual.ejecutar(datos);
        
        // Guardar en historial
        historialProcesados.add(datos);
        this.estadoActual = "COMPLETADO";
        
        notificarObservadores("FIN_PROCESO", "Procesado: " + datos + " | Total procesados: " + historialProcesados.size());
    }
    
    // Cambiar nombre - ESTO NOTIFICA A LOS OBSERVADORES
    public void setNombre(String nuevoNombre) {
        String anterior = this.nombre;
        this.nombre = nuevoNombre;
        notificarObservadores("NOMBRE_CAMBIADO", anterior + " → " + nuevoNombre);
    }
    
    // Obtener historial - ESTO NOTIFICA A LOS OBSERVADORES
    public List<String> getHistorial() {
        notificarObservadores("CONSULTA_HISTORIAL", "Se consultó el historial");
        return new ArrayList<>(historialProcesados);
    }
    
    // Simular error - ESTO NOTIFICA A LOS OBSERVADORES
    public void simularError(String error) {
        this.estadoActual = "ERROR";
        notificarObservadores("ERROR", error);
    }
    
    public String getEstadoActual() {
        return estadoActual;
    }
}

// ============ IMPLEMENTACIONES CONCRETAS DE OBSERVADORES ============

// Observador que muestra en consola
class ObservadorConsola implements Observador {
    private String id;
    
    public ObservadorConsola(String id) {
        this.id = id;
    }
    
    @Override
    public void actualizar(String evento, Object datos) {
        System.out.println("  💻 [" + id + "] Evento: " + evento + " | Datos: " + datos);
    }
}

// Observador que solo registra errores
class ObservadorErrores implements Observador {
    private List<String> errores = new ArrayList<>();
    
    @Override
    public void actualizar(String evento, Object datos) {
        if (evento.equals("ERROR")) {
            errores.add((String) datos);
            System.out.println("  ❌ [OBS-ERRORES] Error registrado: " + datos);
            System.out.println("  ❌ Total errores: " + errores.size());
        }
    }
    
    public List<String> getErrores() {
        return errores;
    }
}

// Observador que cuenta operaciones
class ObservadorContador implements Observador {
    private int contadorProcesos = 0;
    private int contadorCambiosEstrategia = 0;
    
    @Override
    public void actualizar(String evento, Object datos) {
        switch(evento) {
            case "INICIO_PROCESO":
                contadorProcesos++;
                System.out.println("  🔢 [CONTADOR] Procesos iniciados: " + contadorProcesos);
                break;
            case "ESTRATEGIA_CAMBIADA":
                contadorCambiosEstrategia++;
                System.out.println("  🔢 [CONTADOR] Cambios de estrategia: " + contadorCambiosEstrategia);
                break;
            case "FIN_PROCESO":
                System.out.println("  🔢 [CONTADOR] Proceso completado. Total: " + contadorProcesos);
                break;
        }
    }
    
    public int getTotalProcesos() { return contadorProcesos; }
}

// Observador que actúa como logger
class ObservadorLogger implements Observador {
    @Override
    public void actualizar(String evento, Object datos) {
        System.out.println("  📝 [LOGGER] [" + System.currentTimeMillis() + "] " + evento + ": " + datos);
    }
}

// Observador que solo muestra cambios de estado importantes
class ObservadorEstado implements Observador {
    @Override
    public void actualizar(String evento, Object datos) {
        if (evento.equals("ESTRATEGIA_CAMBIADA") || evento.equals("ERROR")) {
            System.out.println("  ⚡ [ESTADO] Cambio importante - " + evento + ": " + datos);
        }
    }
}

// ============ CLASE PRINCIPAL DEMOSTRACIÓN ============
public class Estrategyyobserver {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== DEMOSTRACIÓN PATRONES STRATEGY + OBSERVER ===\n");
        
        // Crear el sujeto observable
        ProcesadorDatos procesador = new ProcesadorDatos("ProcesadorPrincipal");
        
        // Crear observadores
        ObservadorConsola obsConsola = new ObservadorConsola("Monitor1");
        ObservadorErrores obsErrores = new ObservadorErrores();
        ObservadorContador obsContador = new ObservadorContador();
        ObservadorLogger obsLogger = new ObservadorLogger();
        ObservadorEstado obsEstado = new ObservadorEstado();
        
        // Agregar observadores al sujeto
        System.out.println("--- Agregando observadores ---");
        procesador.agregarObservador(obsConsola);
        procesador.agregarObservador(obsErrores);
        procesador.agregarObservador(obsContador);
        procesador.agregarObservador(obsLogger);
        procesador.agregarObservador(obsEstado);
        
        System.out.println("\n" + "=".repeat(60));
        
        // Demostración interactiva
        boolean ejecutando = true;
        while (ejecutando) {
            mostrarMenu();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    procesarDatos(procesador);
                    break;
                case 2:
                    cambiarEstrategia(procesador);
                    break;
                case 3:
                    cambiarNombre(procesador);
                    break;
                case 4:
                    simularError(procesador);
                    break;
                case 5:
                    consultarHistorial(procesador);
                    break;
                case 6:
                    gestionarObservadores(procesador);
                    break;
                case 0:
                    ejecutando = false;
                    System.out.println("\n👋 Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
        scanner.close();
    }
    
    private static void mostrarMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("MENÚ PRINCIPAL - El procesador NOTIFICARÁ a todos los observadores");
        System.out.println("=".repeat(60));
        System.out.println("1.  Procesar datos (NOTIFICA inicio y fin)");
        System.out.println("2.  Cambiar estrategia (NOTIFICA el cambio)");
        System.out.println("3.  Cambiar nombre del procesador (NOTIFICA el cambio)");
        System.out.println("4.  Simular error (NOTIFICA el error)");
        System.out.println("5.  Consultar historial (NOTIFICA la consulta)");
        System.out.println("6.  Gestionar observadores (agregar/eliminar)");
        System.out.println("0.  Salir");
        System.out.print("\nSeleccione: ");
    }
    
    private static int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static void procesarDatos(ProcesadorDatos procesador) {
        System.out.print("Ingrese datos a procesar: ");
        String datos = scanner.nextLine();
        procesador.procesar(datos);
        System.out.println("\n✅ Proceso completado. Estado actual: " + procesador.getEstadoActual());
    }
    
    private static void cambiarEstrategia(ProcesadorDatos procesador) {
        System.out.println("\nEstrategias disponibles:");
        System.out.println("1. Estrategia A (Mayúsculas)");
        System.out.println("2. Estrategia B (Invertir)");
        System.out.println("3. Estrategia C (Contar)");
        System.out.print("Seleccione: ");
        
        int opcion = leerOpcion();
        Estrategia nueva = null;
        switch (opcion) {
            case 1: nueva = new EstrategiaA(); break;
            case 2: nueva = new EstrategiaB(); break;
            case 3: nueva = new EstrategiaC(); break;
            default: System.out.println("Opción inválida"); return;
        }
        
        procesador.cambiarEstrategia(nueva);
    }
    
    private static void cambiarNombre(ProcesadorDatos procesador) {
        System.out.print("Nuevo nombre para el procesador: ");
        String nuevoNombre = scanner.nextLine();
        procesador.setNombre(nuevoNombre);
    }
    
    private static void simularError(ProcesadorDatos procesador) {
        System.out.print("Mensaje de error: ");
        String error = scanner.nextLine();
        procesador.simularError(error);
    }
    
    private static void consultarHistorial(ProcesadorDatos procesador) {
        List<String> historial = procesador.getHistorial();
        System.out.println("\n📜 Historial de procesados:");
        if (historial.isEmpty()) {
            System.out.println("  (vacío)");
        } else {
            for (int i = 0; i < historial.size(); i++) {
                System.out.println("  " + (i+1) + ". " + historial.get(i));
            }
        }
    }
    
    private static void gestionarObservadores(ProcesadorDatos procesador) {
        System.out.println("\n=== GESTIÓN DE OBSERVADORES ===");
        System.out.println("1. Agregar nuevo observador consola");
        System.out.println("2. Agregar observador de errores");
        System.out.println("3. Agregar observador contador");
        System.out.println("4. No implementado - eliminar (requiere referencia)");
        System.out.print("Seleccione: ");
        
        int opcion = leerOpcion();
        Observador nuevoObs = null;
        
        switch (opcion) {
            case 1:
                System.out.print("Nombre del observador: ");
                String nombre = scanner.nextLine();
                nuevoObs = new ObservadorConsola(nombre);
                break;
            case 2:
                nuevoObs = new ObservadorErrores();
                break;
            case 3:
                nuevoObs = new ObservadorContador();
                break;
            default:
                System.out.println("Opción no válida");
                return;
        }
        
        if (nuevoObs != null) {
            procesador.agregarObservador(nuevoObs);
            System.out.println("✅ Observador agregado. Ahora será notificado de todos los eventos.");
        }
    }
}