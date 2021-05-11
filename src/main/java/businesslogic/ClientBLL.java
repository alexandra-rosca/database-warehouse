package businesslogic;

import businesslogic.client.AddressValidator;
import businesslogic.client.NameValidator;

import dao.ClientDAO;
import model.Client;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Clasa de care ne ajutam sa accesam operatiile CRUD descrise pe larg in AbstractDAO respectand Layer Architecture
 * In constructori avem vaidatori pentru numele si adresa clientului
 */
public class ClientBLL {

    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new NameValidator());
        validators.add(new AddressValidator());

        clientDAO = new ClientDAO();

    }

    public List<Client> findAllClients() {
        List<Client> clienti = new ArrayList<Client>();
        try {
            clienti =  clientDAO.findAll();
        }
        catch(Exception e) {
            throw new NoSuchElementException("No clients found! ");
        }
        return clienti;
    }

    public Client findClient(int id) {
        Client cl =  clientDAO.findById(id);

        if (cl == null) {
            throw new NoSuchElementException("The client with id= " + id + " was not found!");
        }

        return cl;
    }

    public Client update(Client client) {
        Client client1 = (Client) clientDAO.update(client);

        if(client1 == null) {
            throw new NoSuchElementException("No clients to update! ");
        }

        return client1;
    }

    public int delete(int id) {
        int c = clientDAO.delete(id);

        if(c == 0) {
            throw new NoSuchElementException("The client does not exist! ");
        }

        return c;
    }

    public Client insert(Client client) {

        try {
            validators.get(0).validate(client);
            validators.get(1).validate(client);
            return clientDAO.insert(client);
        } catch (Exception e) {
            return null;
        }
    }


}
