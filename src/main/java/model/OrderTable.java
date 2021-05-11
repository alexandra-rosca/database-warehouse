package model;

/**
 * Clasa care defineste atributele unei comenzi
 */
public class OrderTable {

    private int id;
    private int orderQuantity;
    private String clientName;
    private String productName;
    private int idProduct;

    public OrderTable(int id, int orderQuantity, String clientName, String productName, int idProduct) {
        this.id = id;
        this.orderQuantity = orderQuantity;
        this.clientName = clientName;
        this.productName = productName;
        this.idProduct = idProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    @Override
    public String toString() {
        return "OrderTable{" +
                "id=" + id +
                ", orderQuantity=" + orderQuantity +
                ", clientName='" + clientName + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
