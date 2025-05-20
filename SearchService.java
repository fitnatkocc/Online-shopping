import java.util.ArrayList;
import java.util.List;

public class SearchService {
    private final List<Product> allProducts = new ArrayList<>();

    public SearchService() {
        // 9 categories:
        Category c1 = new Category("C1", "Electronics");
        Category c2 = new Category("C2", "Clothing & Accessories");
        Category c3 = new Category("C3", "Home & Kitchen");
        Category c4 = new Category("C4", "Beauty & Personal Care");
        Category c5 = new Category("C5", "Sports & Outdoors");
        Category c6 = new Category("C6", "Books & Stationery");
        Category c7 = new Category("C7", "Toys & Games");
        Category c8 = new Category("C8", "Health & Wellness");
        Category c9 = new Category("C9", "Pet Supplies");

        // Electronics (5+)
        allProducts.add(new Product("P01","Smartphone","5.5\" AMOLED, 64GB", c1, 299.99, 30));
        allProducts.add(new Product("P02","Laptop","14\" FHD, 256GB SSD", c1, 799.99, 15));
        allProducts.add(new Product("P03","Wireless Earbuds","Noise-Cancelling", c1, 129.99, 50));
        allProducts.add(new Product("P04","Bluetooth Speaker","Waterproof, 10W", c1, 49.99, 40));
        allProducts.add(new Product("P05","Smart Watch","Heart-rate monitor", c1, 199.99, 25));

        // Clothing & Accessories
        allProducts.add(new Product("P06","Denim Jacket","Size M", c2, 59.99, 20));
        allProducts.add(new Product("P07","Sneakers","Size 42", c2, 89.99, 30));
        allProducts.add(new Product("P08","Baseball Cap","One size", c2, 19.99, 50));
        allProducts.add(new Product("P09","Leather Belt","Size L", c2, 29.99, 40));
        allProducts.add(new Product("P10","Wool Scarf","100% wool", c2, 24.99, 35));

        // Home & Kitchen
        allProducts.add(new Product("P11","Coffee Maker","12-cup capacity", c3, 49.99, 25));
        allProducts.add(new Product("P12","Blender","500W motor", c3, 39.99, 30));
        allProducts.add(new Product("P13","Air Fryer","4L basket", c3, 69.99, 15));
        allProducts.add(new Product("P14","Knife Set","8 pieces stainless", c3, 29.99, 40));
        allProducts.add(new Product("P15","Cutting Board","Bamboo", c3, 19.99, 50));

        // Beauty & Personal Care
        allProducts.add(new Product("P16","Shampoo","500ml", c4, 12.99, 60));
        allProducts.add(new Product("P17","Moisturizer","50ml", c4, 24.99, 45));
        allProducts.add(new Product("P18","Facial Cleanser","150ml", c4, 9.99, 55));
        allProducts.add(new Product("P19","Body Lotion","200ml", c4, 14.99, 50));
        allProducts.add(new Product("P20","Lip Balm","Mint flavor", c4, 4.99, 70));

        // Sports & Outdoors
        allProducts.add(new Product("P21","Yoga Mat","6mm thickness", c5, 29.99, 25));
        allProducts.add(new Product("P22","Dumbbell Set","10kg pair", c5, 49.99, 15));
        allProducts.add(new Product("P23","Trekking Backpack","30L", c5, 59.99, 20));
        allProducts.add(new Product("P24","Water Bottle","1L", c5, 9.99, 50));
        allProducts.add(new Product("P25","Fitness Tracker","Step counter", c5, 39.99, 30));

        // Books & Stationery
        allProducts.add(new Product("P26","Novel: The Odyssey","Classic epic", c6, 19.99, 40));
        allProducts.add(new Product("P27","Notebook Set","5× ruled pages", c6, 9.99, 100));
        allProducts.add(new Product("P28","Fountain Pen","Fine nib", c6, 14.99, 35));
        allProducts.add(new Product("P29","Sketch Pad","50 pages", c6, 12.99, 45));
        allProducts.add(new Product("P30","Highlighter Pack","5 colors", c6, 7.99, 60));

        // Toys & Games
        allProducts.add(new Product("P31","Building Blocks","100 pcs", c7, 29.99, 30));
        allProducts.add(new Product("P32","Action Figure","6 inches", c7, 14.99, 50));
        allProducts.add(new Product("P33","Puzzle 1000pc","Landscape", c7, 12.99, 40));
        allProducts.add(new Product("P34","Board Game","Family fun", c7, 24.99, 25));
        allProducts.add(new Product("P35","Dollhouse","Wooden kit", c7, 39.99, 15));

        // Health & Wellness
        allProducts.add(new Product("P36","Vitamins Pack","30 days supply", c8, 19.99, 60));
        allProducts.add(new Product("P37","Protein Powder","1kg tub", c8, 29.99, 50));
        allProducts.add(new Product("P38","Yoga Block","Foam", c8, 9.99, 40));
        allProducts.add(new Product("P39","Massage Ball","Spiky", c8, 14.99, 35));
        allProducts.add(new Product("P40","Resistance Bands","Set of 3", c8, 19.99, 45));

        // Pet Supplies
        allProducts.add(new Product("P41","Dog Leash","1.5m nylon", c9, 12.99, 50));
        allProducts.add(new Product("P42","Cat Toy","Feather teaser", c9, 4.99, 75));
        allProducts.add(new Product("P43","Pet Bed","50×40cm", c9, 29.99, 20));
        allProducts.add(new Product("P44","Fish Tank","20L glass", c9, 59.99, 10));
        allProducts.add(new Product("P45","Bird Cage","Medium size", c9, 49.99, 15));
    }

    /** @return all products */
    public List<Product> getAllProducts() {
        return new ArrayList<>(allProducts);
    }

    /** @return products whose category name matches exactly */
    public List<Product> filterByCategory(String categoryName) {
        List<Product> out = new ArrayList<>();
        for (Product p : allProducts) {
            if (p.getCategory().getName().equals(categoryName)) {
                out.add(p);
            }
        }
        return out;
    }
}
