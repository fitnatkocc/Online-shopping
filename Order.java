import java.util.Date;
import java.util.List;

public class Order {
    private String id;
    private List<OrderItem> items;
    private Address deliveryAddress;
    private Date orderDate;
    private String status;

    public double calculateTotal() {
        return 0.0;
    }

    public void updateStatus(String newStatus) {}
}
