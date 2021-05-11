package businesslogic.client;

import businesslogic.Validator;

import model.Client;

/**
 * Clasa folosita ca sa valideze adresa unui client din baza de date
 */
public class AddressValidator implements Validator<Client> {

    public AddressValidator() {

    }

    @Override
    public void validate(Client client) {
        if(client.getAddressClient() == null) {
            throw new IllegalArgumentException("Address field is empty!");
        }
    }
}
