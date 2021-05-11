package businesslogic.product;

import businesslogic.Validator;
import model.Product;

/**
 * Clasa folosita ca sa valideze pretul unui produs din baza de date
 */
public class PriceValidator implements Validator<Product> {

    private static final int MIN_PRICE = 1;

    @Override
    public void validate(Product product) {
        if (product.getProductPrice() < MIN_PRICE) {
            throw new IllegalArgumentException("Price is out of limits!");
        }
    }
}
