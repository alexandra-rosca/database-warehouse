package dao;

import model.OrderTable;

import java.util.List;

/**
 *  Clasa care mosteneste AbstractDAO
 *  Aici numai facem Override la metodele din clasa AbstractDAO
 */
public class OrderDAO extends AbstractDAO<OrderTable> {

    @Override
    public int delete(int id) {
        return super.delete(id);
    }

    @Override
    public OrderTable findById(int id) {
        return super.findById(id);
    }

    @Override
    public OrderTable findProduct(Object field, String fieldName) {
        return super.findProduct(field, fieldName);
    }

    @Override
    public List<OrderTable> findAll() {
        return super.findAll();
    }

    @Override
    public OrderTable insert(OrderTable o) {
        return super.insert(o);
    }

    @Override
    public OrderTable update(OrderTable o) {
        return super.update(o);
    }
}
