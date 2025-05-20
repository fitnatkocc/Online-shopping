import java.util.ArrayList;
import java.util.List;

public class Wishlist {
    private List<Product> products;

    public Wishlist() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public List<Product> viewWishlist() {
        return products;
    }
}
