public abstract class Ammunition {
    private String name;
    private double weight;
    private double price;

    public Ammunition(String name, double weight, double price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public double getPrice() { return price; }
    public double getWeight() { return weight; }
    
    @Override
    public String toString() {
        return name + " (Вага: " + weight + ", Ціна: " + price + ")";
    }
}