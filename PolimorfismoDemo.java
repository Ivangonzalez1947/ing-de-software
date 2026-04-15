// Interfaz Pet
interface Pet {
    void play(); // Método que será implementado por las clases que sean mascotas
}

// Clase padre Animal
class Animal {
    // Atributos
    private String name;
    private int age;

    // Constructor
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Métodos getter y setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Método que será sobrescrito por la clase hija
    public void makeSound() {
        System.out.println("Animal [" + name + "] hace un sonido genérico.");
    }

    // Método que no será sobrescrito (para mostrar llamada al padre)
    public void eat() {
        System.out.println("Animal [" + name + "] está comiendo.");
    }
}

// Clase hija Dog que extiende Animal e implementa Pet
class Dog extends Animal implements Pet {
    // Atributo adicional
    private String breed;

    // Constructor
    public Dog(String name, int age, String breed) {
        super(name, age); // Llama al constructor de la clase padre
        this.breed = breed;
    }

    // Getter y setter para breed
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    // Sobrescritura del método makeSound() de Animal
    @Override
    public void makeSound() {
        System.out.println("Perro [" + getName() + "] ladra: ¡Guau guau!");
    }

    // Implementación del método play() de la interfaz Pet
    @Override
    public void play() {
        System.out.println("Perro [" + getName() + "] está jugando con la pelota.");
    }
}

// Clase principal para demostrar polimorfismo
public class PolimorfismoDemo {
    public static void main(String[] args) {
        System.out.println("=== Demostración de Polimorfismo ===");

        // 1. Crear un objeto de tipo Animal (referencia de Animal a un Animal)
        Animal animal = new Animal("Genérico", 5);
        System.out.println("\n--- Llamadas desde referencia Animal a objeto Animal ---");
        animal.makeSound(); // Llama al método de Animal
        animal.eat();       // Llama al método de Animal

        // 2. Crear un objeto de tipo Dog (referencia de Dog a un Dog)
        Dog dog = new Dog("Max", 3, "Labrador");
        System.out.println("\n--- Llamadas desde referencia Dog a objeto Dog ---");
        dog.makeSound();    // Llama al método sobrescrito de Dog
        dog.eat();          // Llama al método heredado de Animal (no sobrescrito)
        dog.play();         // Llama al método de la interfaz implementado en Dog

        // 3. Polimorfismo: referencia de tipo Animal que apunta a un objeto Dog
        Animal animalRef = new Dog("Rex", 2, "Pastor Alemán");
        System.out.println("\n--- Polimorfismo: referencia Animal a objeto Dog ---");
        animalRef.makeSound(); // Llama al método sobrescrito de Dog (polimorfismo)
        animalRef.eat();       // Llama al método de Animal (no sobrescrito, heredado)

        // Nota: No se puede llamar a animalRef.play() porque play() no está definido en Animal.
        // Si se desea, se puede hacer un casting explícito:
        if (animalRef instanceof Pet) {
            ((Pet) animalRef).play(); // Casting para acceder al método de Pet
        }

        // 4. Método que recibe un Animal y demuestra polimorfismo
        System.out.println("\n--- Método que recibe Animal (polimorfismo) ---");
        hacerSonido(animal);   // Pasa Animal
        hacerSonido(dog);      // Pasa Dog (polimórfico)
        hacerSonido(animalRef); // Pasa Dog referenciado como Animal
    }

    // Método que demuestra polimorfismo: el parámetro Animal puede recibir cualquier subclase
    public static void hacerSonido(Animal a) {
        a.makeSound(); // La implementación real se determina en tiempo de ejecución
    }
}