// Address.java

public class Address {
    private String street;

    // Tek parametreli constructor: GUI'den gelen tek satırlık adres metni
    public Address(String street) {
        this.street = street;
    }

    public String getFullAddress() {
        return street;
    }

    @Override
    public String toString() {
        return street;
    }
}
