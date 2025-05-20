public class Product {
    private String id;
    private String name;
    private String description;
    private Category category;
    private double price;
    private int stock;

    /**
     * Main constructor matching your SearchService usage.
     */
    public Product(String id,
                   String name,
                   String description,
                   Category category,
                   double price,
                   int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    /** Optional convenience constructor if you ever want to pass category by name. */
    public Product(String id,
                   String name,
                   String description,
                   String categoryName,
                   double price,
                   int stock) {
        this(id, name, description, new Category("", categoryName), price, stock);
    }

    // Getters
    public String getId()            { return id; }
    public String getName()          { return name; }
    public String getDescription()   { return description; }
    public Category getCategory()    { return category; }
    public double getPrice()         { return price; }
    public int getStock()            { return stock; }

    // Setter for stock so purchases can decrement it
    public void setStock(int stock)  { this.stock = stock; }

    @Override
    public String toString() {
        return String.format("%s - $%.2f (stock: %d)", name, price, stock);
    }
}
