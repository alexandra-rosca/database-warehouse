package businesslogic.product;

import businesslogic.Validator;
import model.Product;

/**
 * Clasa folosita ca sa valideze numele unui produs din baza de date
 */
public class NameProduct implements Validator<Product> {
    @Override
    public void validate(Product product) {
        if(product.getProductName() == null) {
            throw new IllegalArgumentException("Nu exista produse!");
        }
    }
}
