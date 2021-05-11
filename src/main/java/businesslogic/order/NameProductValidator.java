package businesslogic.order;

import businesslogic.Validator;
import model.OrderTable;

/**
 * Clasa folosita ca sa valideze numele unui produs comandata din baza de date
 */
public class NameProductValidator implements Validator<OrderTable> {
    @Override
    public void validate(OrderTable order) {
        if(order.getProductName() == null) {
            throw new IllegalArgumentException("Nu exista produse!");
        }
    }
}
