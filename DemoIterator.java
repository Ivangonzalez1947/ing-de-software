import java.util.Iterator;//importa la interfaz iterador usada para permitir recorrido secuencial
import java.util.NoSuchElementException; //si se intenta acceder a un elemento que no existe

// Clase que representa un libro
class Libro {
    private String titulo;
    private String autor;

    // Constructor que inicializa título y autor
    public Libro(String titulo, String autor) {
        this.titulo = titulo;// Asigna el titulo recibido al campo de la instancia
        this.autor = autor;// Asigna el autor recibido al campo de la instancia
    }

    @Override
    // Método que devuelve una representación legible del libro
    public String toString() {
        // Concatena título y autor en formato "título de autor"
        return titulo + " de " + autor;
    }
}

// Colección personalizada que implementa Iterable
class CatalogoLibros implements Iterable<Libro> {
    private Libro[] libros;
    private int tamaño;
    private static final int CAPACIDAD_INICIAL = 5;

    // Constructor que inicializa la colección
    public CatalogoLibros() {
        // Crea el array con la capacidad inicial
        libros = new Libro[CAPACIDAD_INICIAL];
        tamaño = 0;// inicialmente no hay libros
    }
    // Método para agregar un libro al catálogo
    public void agregarLibro(Libro libro) {
        if (tamaño == libros.length) {// Si el array está lleno, hay que redimensionar
            // Redimensionar (simplificado)
            // Crea un array nuevo con el doble de capacidad
            Libro[] nuevo = new Libro[libros.length * 2];
            // Copia los elementos al array nuevo
            System.arraycopy(libros, 0, nuevo, 0, tamaño);
            libros = nuevo;// Reemplaza el array interno por el nuevo
        }
        // Inserta el libro en la siguiente posición libre y aumenta el tamaño
        libros[tamaño++] = libro;
    }

    // Método requerido por Iterable: devuelve un Iterator
    @Override
    public Iterator<Libro> iterator() {
        // Retorna una instancia del iterador interno
        return new IteradorCatalogo();
    }

    // Clase interna que implementa la lógica de iteración
    private class IteradorCatalogo implements Iterator<Libro> {
        // Índice del siguiente elemento a devolver por next()
        private int indiceActual = 0;

        @Override
        public boolean hasNext() {// Indica si quedan elementos por iterar
            // True si el índice actual es menor que el tamaño
            return indiceActual < tamaño;
        }

        @Override
        public Libro next() {// Devuelve el siguiente elemento de la colección
            if (!hasNext()) {// Si no hay siguiente elemento...
                // lanza excepcion apropiada
                throw new NoSuchElementException("No hay más libros");
            }
            // Devuelve el libro en indiceActual y luego incrementa el índice
            return libros[indiceActual++];
        }
    }
}

// Demostración del patrón Iterator
public class DemoIterator {
    public static void main(String[] args) {
        CatalogoLibros catalogo = new CatalogoLibros();
        catalogo.agregarLibro(new Libro("Cien años de soledad", "García Márquez"));
        catalogo.agregarLibro(new Libro("El Quijote", "Cervantes"));
        catalogo.agregarLibro(new Libro("1984", "George Orwell"));

        // Recorrido usando Iterator explícito
        System.out.println("=== Usando Iterator ===");
        // Obtiene un iterador del catálogo
        Iterator<Libro> it = catalogo.iterator();
        while (it.hasNext()) {// Mientras queden elementos...
            System.out.println(it.next());// imprime el siguiente elemento
        }

//El bucle for-each es una estructura de control optimizada
//para recorrer colecciones (arrays, listas, conjuntos) o rangos, 
//permitiendo iterar sobre cada elemento automáticamente 
//sin gestionar índices iniciales o finales.

        // Recorrido con for-each (gracias a Iterable)
        System.out.println("\n=== Usando for-each ===");// Encabezado para el recorrido for-each        
        // Sintaxis for-each que usa el iterator internamente
        for (Libro libro : catalogo) {
            // Imprime cada libro del catálogo
            System.out.println(libro);
        }
    }
}