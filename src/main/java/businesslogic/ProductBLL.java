package businesslogic;

import businesslogic.product.NameProduct;
import businesslogic.product.PriceValidator;
import dao.ProductDAO;
import model.OrderTable;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Clasa de care ne ajutam sa accesam operatiile CRUD descrise pe larg in AbstractDAO respectand Layer Architecture
 * In constructor avem validatori pentrunumele produsului si pentru pretul acestuia
 */
public class ProductBLL {

    private List<Validator<Product>> validators;
    public ProductDAO productDAO;

    public ProductBLL() {
        validators = new ArrayList<Validator<Product>>();
        validators.add(new PriceValidator());
        validators.add(new NameProduct());
        productDAO = new ProductDAO();
    }

    public List<Product> findAllProducts() {
        List<Product> products = new ArrayList<Product>();
        try {
            products = productDAO.findAll();
        }
        catch(Exception e) {
            throw new NoSuchElementException("No clients found! ");
        }
        return products;
    }

    public Product findById(int id) {
        Product product = productDAO.findById(id);

        if(product == null) {
            throw new NoSuchElementException("The product with id= " + id + " was not found!");
        }

        return product;
    }


    public Product update(Product product) {
        Product product1 = productDAO.update(product);

        if(product1 == null) {
            throw new NoSuchElementException("The order cannot be updated");
        }

        return product1;
    }

    public int delete(int id) {
        int p = productDAO.delete(id);

        if(p == 0) {
            throw new NoSuchElementException("The product cannot be deleted");
        }

        return p;
    }

    public Product insert(Product product) {

        try {
            validators.get(0).validate(product);
            validators.get(1).validate(product);
            return productDAO.insert(product);
        } catch (Exception e) {
            return null;
        }
    }
}
