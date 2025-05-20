import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<CartItem> items;
    private Address shippingAddress;
    private String orderId;
    private static int nextOrderId = 1;

    public ShoppingCart() {
        this.items = new ArrayList<>();
        this.orderId = String.valueOf(nextOrderId++);
    }

    /**
     * Kart numarası ile birlikte ürünü sepete ekler.
     */
    public void addItem(Product product, int quantity, String cardNumber) {
        items.add(new CartItem(product, quantity, cardNumber));
    }

    /**
     * Eğer isterseniz, kart bilgisi olmadan da eklemeye izin vermek için
     * bu overload’u bırakabilir veya kaldırabilirsiniz.
     */
    public void addItem(Product product, int quantity) {
        // Varsayılan kart numarası kullanılır
        items.add(new CartItem(product, quantity, "1111111111111111"));
    }

    /**
     * Sepetten bir ürünü tamamen çıkarır.
     */
    public void removeItem(Product product) {
        items.removeIf(ci -> ci.getProduct().equals(product));
    }

    /**
     * Sepetteki tüm kalemlerin toplam tutarını hesaplar.
     */
    public double calculateTotal() {
        double total = 0.0;
        for (CartItem ci : items) {
            total += ci.getTotalPrice();
        }
        return total;
    }

    /**
     * Sepeti boşaltır.
     */
    public void clearCart() {
        items.clear();
    }

    /**
     * Gönderim adresini ayarlar.
     */
    public void setAddress(Address address) {
        this.shippingAddress = address;
    }

    /**
     * Oluşturulan siparişin ID’sini döner.
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Siparişin toplam tutarını döner.
     */
    public double getTotalAmount() {
        return calculateTotal();
    }

    /**
     * Atanmışsa gönderim adresini alır.
     */
    public Address getShippingAddress() {
        return shippingAddress;
    }

    /**
     * Sepetteki tüm CartItem nesnelerini döner.
     */
    public List<CartItem> getItems() {
        return items;
    }
}
