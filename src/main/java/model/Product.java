package model;

/**
 * Clasa care defineste atributele Produselor din baza de date
 */
public class Product {

    private int id;
    private String productName;
    private int productPrice;
    private int productStock;

    public Product() {

    }

    public Product(int idProduct, String productName, int productPrice, int productStock) {
        this.id = idProduct;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productStock=" + productStock +
                '}';
    }
}
