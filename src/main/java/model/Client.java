package model;

/**
 * Clasa care defineste atributele clientilor din baza de date
 */
public class Client {

    private int id;
    private String clientName;
    private String addressClient;

    public Client() {

    }

    public Client(int id, String clientName, String addressClient) {
        this.id = id;
        this.clientName = clientName;
        this.addressClient = addressClient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAddressClient() {
        return addressClient;
    }

    public void setAddressClient(String addressClient) {
        this.addressClient = addressClient;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + id +
                ", clientName='" + clientName + '\'' +
                ", addressClient='" + addressClient + '\'' +
                '}';
    }
}
