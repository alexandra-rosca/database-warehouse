package dao;

import model.Client;

import java.util.List;

/**
 * Clasa care mosteneste AbstractDAO
 * Aici numai facem Override la metodele din clasa AbstractDAO
 */
public class ClientDAO extends AbstractDAO<Client>{

    @Override
    public List<Client> findAll() {
        return super.findAll();
    }

    @Override
    public int delete(int id) {
        return super.delete(id);
    }

    @Override
    public Client insert(Client o) {
        return super.insert(o);
    }

    @Override
    public Client findById(int id) {
        return super.findById(id);
    }

    @Override
    public Client update(Client o) {
        return super.update(o);
    }
}
