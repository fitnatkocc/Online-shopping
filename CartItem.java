public class CartItem {
    private Product product;
    private int quantity;
    private String cardNumber;  // Basit kart numarası (örn. "1111111111111111")

    // Kart bilgisi de alan constructor
    public CartItem(Product product, int quantity, String cardNumber) {
        this.product = product;
        this.quantity = quantity;
        this.cardNumber = cardNumber;
    }

    public Product getProduct() {
        return product;
    }
    public int getQuantity() {
        return quantity;
    }
    public String getCardNumber() {
        return cardNumber;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        // Son 4 haneyi göster, geri yıldızla gizle
        String masked = cardNumber.length() >= 4
            ? "**** **** **** " + cardNumber.substring(cardNumber.length() - 4)
            : cardNumber;
        return product.getName()
            + " x" + quantity
            + " = " + getTotalPrice()
            + " (Card: " + masked + ")";
    }
}
