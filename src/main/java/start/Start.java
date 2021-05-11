package start;

import businesslogic.ClientBLL;
import businesslogic.ProductBLL;
import model.Client;
import model.Product;
import presentation.Controller;
import presentation.GUI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The main class
 * Se apeleaza interfata grafica impreuna cu functionalitatea butoanelor din Controller
 */
public class Start {
    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());
    public static void main(String[] args) {
        GUI gui = new GUI();
        Controller controller = new Controller(gui);

      /*  ClientBLL clientBLL = new ClientBLL();
        Client client1 = null;
        try{
            client1 = clientBLL.findClient(1);
            System.out.println(client1.toString());

        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
        }

        ProductBLL productBLL = new ProductBLL();
        Product product = null;
        Product p = null;
        try {
           // product = productBLL.findById(1);
            product = productBLL.findProduct("Oua" , "productName");
            System.out.println(product.toString());
        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
        } */
    }
}
