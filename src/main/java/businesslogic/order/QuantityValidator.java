package businesslogic.order;

import businesslogic.Validator;
import model.OrderTable;

/**
 * Clasa folosita ca sa valideze cantitatea unui produs comandat din baza de date
 */
public class QuantityValidator implements Validator<OrderTable> {

    @Override
    public void validate(OrderTable t) {
        if(t.getOrderQuantity() <= 0) {
            throw new IllegalArgumentException("Cantitatea trebuie sa fie strict mai mare decat 0!!");
        }
    }

}
