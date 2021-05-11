package dao;

import model.OrderTable;
import model.Product;

import java.util.List;

/**
 * Clasa care mosteneste AbstractDAO
 * Aici numai facem Override la metodele din clasa AbstractDAO
 */
public class ProductDAO extends AbstractDAO<Product> {

    @Override
    public int delete(int id) {
        return super.delete(id);
    }

    @Override
    public Product findById(int id) {
        return super.findById(id);
    }


    @Override
    public List<Product> findAll() {
        return super.findAll();
    }

    @Override
    public Product insert(Product o) {
        return super.insert(o);
    }

    @Override
    public Product update(Product o) {
        return super.update(o);
    }
}
