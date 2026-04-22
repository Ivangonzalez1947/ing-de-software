// Main.java
public class Main {
    public static void main(String[] args) {

        Cafe cafe = new CafeSimple();
        cafe = new Leche(cafe);
        cafe = new Azucar(cafe);

        System.out.println(cafe.descripcion());
        System.out.println("Precio: " + cafe.costo());
    }
}