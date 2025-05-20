public class Stock {
    // Anything at-or-below this is considered low stock
    private static final int LOW_THRESHOLD = 5;

    /** 
     * Returns true if the product has at least one in stock.
     */
    public static boolean isAvailable(Product p) {
        return p.getStock() > 0;
    }

    /**
     * Returns true if the product’s stock is below the LOW_THRESHOLD.
     */
    public static boolean isLow(Product p) {
        return p.getStock() < LOW_THRESHOLD;
    }
}
