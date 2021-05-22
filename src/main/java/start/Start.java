package start;

import presentation.Controller;
import presentation.GUI;

/**
 * The main class
 * Se apeleaza interfata grafica impreuna cu functionalitatea butoanelor din Controller
 */
public class Start {
    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());
    public static void main(String[] args) {
        GUI gui = new GUI();
        Controller controller = new Controller(gui);
    }
}
