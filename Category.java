// Category.java

public class Category {
    private String id;
    private String name;

    // Parametresiz constructor (varsa eski kodlar için)
    public Category() {
    }

    // ID ve isimle yeni kategori yaratmak için
    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter / Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        // JList ve diğer UI kontrollerinde sadece isim gösterilsin
        return name;
    }
}
