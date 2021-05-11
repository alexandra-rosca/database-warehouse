package businesslogic;

import businesslogic.order.ClientNameValidator;
import businesslogic.order.NameProductValidator;
import businesslogic.order.QuantityValidator;
import dao.OrderDAO;
import model.OrderTable;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Clasa de care ne ajutam sa accesam operatiile CRUD descrise pe larg in AbstractDAO respectand Layer Architecture
 * In constructorul clasei avem validatori pentru numele si cantitatea produsului si numele clientului care face comanda
 */
public class OrderBLL {

    private List<Validator<OrderTable>> validators;
    public OrderDAO orderDAO;

    public OrderBLL() {
        validators = new ArrayList<Validator<OrderTable>>();
        validators.add(new QuantityValidator());
        validators.add(new NameProductValidator());
        validators.add(new ClientNameValidator());
        orderDAO = new OrderDAO();
    }

    public List<OrderTable> findAllOrders() {
        List<OrderTable> orders = new ArrayList<OrderTable>();
        try {
            orders =  orderDAO.findAll();
        }
        catch(Exception e) {
            throw new NoSuchElementException("No clients found! ");
        }
        return orders;
    }

    public OrderTable findById(int id) {
        OrderTable order =  orderDAO.findById(id);

        if (order == null) {
            throw new NoSuchElementException("The order with id= " + id + " was not found!");
        }

        return order;
    }

    public OrderTable findProduct(Object field, String fieldName) {
        OrderTable p = orderDAO.findProduct(field, fieldName);

        if(p == null) {
            throw new NoSuchElementException("The product with id= " + fieldName + " was not found!");
        }

        return p;
    }

    public OrderTable update(OrderTable order) {
        OrderTable order1 = orderDAO.update(order);

        if(order1== null) {
            throw new NoSuchElementException("No orders to update! ");
        }

        return order1;
    }

    public int delete(int id) {
        int o = orderDAO.delete(id);

        if(o == 0) {
            throw new NoSuchElementException("The order does not exist! ");
        }

        return o;
    }

    public OrderTable insert(OrderTable order) {

        try {
            validators.get(0).validate(order);
            validators.get(1).validate(order);
            return orderDAO.insert(order);
        } catch (Exception e) {
            return null;
        }
    }
}
