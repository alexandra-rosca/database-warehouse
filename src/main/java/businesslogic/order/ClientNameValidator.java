package businesslogic.order;

import businesslogic.Validator;
import model.OrderTable;

/**
 * Clasa folosita ca sa valideze numele unui client care plaseaza o comanda din baza de date
 */
public class ClientNameValidator implements Validator<OrderTable> {

    @Override
    public void validate(OrderTable order) {
        if(order.getClientName() == null) {
            throw new IllegalArgumentException("Name field is empty!");
        }
    }
}
