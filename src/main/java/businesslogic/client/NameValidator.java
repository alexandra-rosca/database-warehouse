package businesslogic.client;

import businesslogic.Validator;
import model.Client;


/**
 * Clasa folosita ca sa valideze numele unui client din baza de date
 */
public class NameValidator implements Validator<Client> {

    public NameValidator() {

    }

    @Override
    public void validate(Client client) {
        if(client.getClientName() == null) {
            throw new IllegalArgumentException("Name field is empty!");
        }
    }
}
