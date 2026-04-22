import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Carrito carrito = new Carrito();
        Stack<CarritoMemento> historial = new Stack<>();

        // Agregamos algo y guardamos
        carrito.agregarProducto("Camisa");
        historial.push(carrito.guardar());
        System.out.println("Paso 1: " + carrito.mostrar());

        // Agregamos otra cosa y guardamos
        carrito.agregarProducto("Pantalon");
        historial.push(carrito.guardar());
        System.out.println("Paso 2: " + carrito.mostrar());

        // Error: agregamos algo que no queremos
        carrito.agregarProducto("Error_Producto");
        System.out.println("Paso 3 (con error): " + carrito.mostrar());

        // Deshacer: Volvemos al estado del Paso 2
        if (!historial.isEmpty()) {
            carrito.restaurar(historial.pop());
        }

        System.out.println("\n--- ¡DESHACER ACTIVADO! ---");
        System.out.println("Resultado final: " + carrito.mostrar());
    }
}