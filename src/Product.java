public class Product {
    private String name;
    private String url;
    private String desc;
    private double price;
    private double rating;

    public Product(String name, String url, String desc, double price, double rating) {
        this.name = name;
        this.url = url;
        this.desc = desc;
        this.price = price;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }
    public String getURL() {
        return url;
    }
    public String getDesc() {
        return desc;
    }
    public double getPrice() {
        return price;
    }
    public double getRating() {
        return rating;
    }
    public String toString() {
        return String.format("Product Name: %s%nProduct URL: %s%nDescription: %s%nPrice: %.2f%n Rating: %.1f", name,
            url, desc, price, rating);
    }

}