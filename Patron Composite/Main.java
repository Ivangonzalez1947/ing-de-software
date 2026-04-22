// Main.java
public class Main {
    public static void main(String[] args) {

        // Archivos
        Componente archivo1 = new Archivo("foto.jpg");
        Componente archivo2 = new Archivo("documento.pdf");

        // Carpetas
        Carpeta carpeta1 = new Carpeta("Mis Archivos");
        Carpeta carpeta2 = new Carpeta("Trabajo");

        carpeta2.agregar(new Archivo("reporte.docx"));
        carpeta2.agregar(new Archivo("datos.xlsx"));

        carpeta1.agregar(archivo1);
        carpeta1.agregar(archivo2);
        carpeta1.agregar(carpeta2);

        // Mostrar estructura
        carpeta1.mostrar("");
    }
}