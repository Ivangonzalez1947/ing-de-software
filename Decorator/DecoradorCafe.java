// DecoradorCafe.java
public abstract class DecoradorCafe implements Cafe {
    protected Cafe cafe;

    public DecoradorCafe(Cafe cafe) {
        this.cafe = cafe;
    }
}