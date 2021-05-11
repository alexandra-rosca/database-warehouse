package presentation;

import businesslogic.ClientBLL;
import businesslogic.OrderBLL;
import businesslogic.ProductBLL;
import dao.ClientDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Client;
import model.OrderTable;
import model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

/**
 * Clasa Contrller se ocupa cu implementarea Action Listenerilor pe fiecare buton din interfata pentru ca user-ul sa poata interactiona
 * cu aplicatia creata.
 */
public class Controller {

    public GUI gui;

    public Controller(GUI g) {
        this.gui = g;

        gui.addMainClientListener(new AddMainClientListener());
        gui.addMainOrderListener(new AddMainOrderListener());
        gui.addMainProductListener(new AddMainProductListener());
        gui.addBtnClient(new AddBtnClient());
        gui.addBtnProduct(new AddBtnProduct());
        gui.addBtnOrder(new AddBtnOrder());
        gui.updateBtnClient(new UpdateClient());
        gui.updateBtnProduct(new UpdateProduct());
        gui.deleteClient(new DeleteClient());
        gui.deleteProduct(new DeleteProduct());
    }

    /**
     * Deschide un nou frame care acceseaza datele din DB pentru Clienti
     */
    public class AddMainClientListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gui.clientFrame.setVisible(true);
            gui.tableFiller(new ClientDAO(), gui.tableClient, Client.class);
        }
    }

    /**
     * Deschide un nou frame care acceseaza datele din DB pentru Comenzi
     */
    public class AddMainOrderListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gui.orderFrame.setVisible(true);
            gui.tableFiller(new OrderDAO(), gui.tableOrder, OrderTable.class);
        }
    }

    /**
     * Deschide un nou frame care acceseaza datele din DB pentru Produse
     */
    public class AddMainProductListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gui.productFrame.setVisible(true);
            gui.tableFiller(new ProductDAO(), gui.tableProduct, Product.class);
        }
    }

    /**
     * Inseram noi clienti in baza de date
     */
    public class AddBtnClient implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] fields  = gui.dateFields(Client.class);
            ClientBLL clientBLL = new ClientBLL();

            try {
                Client client = new Client(Integer.parseInt(fields[0]), fields[1], fields[2]);
                clientBLL.insert(client);
                gui.tableFiller(new ClientDAO(), gui.tableClient, Client.class);
                gui.tableClient.repaint();
                gui.tableClient.revalidate();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Nu putem insera un nou client!", "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Inseram noi produse in baza de date
     */
    public class AddBtnProduct implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] fields = gui.dateFields(Product.class);
            ProductBLL productBLL = new ProductBLL();

            try {
                Product product = new Product(Integer.parseInt(fields[0]), fields[1], Integer.parseInt(fields[2]), Integer.parseInt(fields[3]));
                productBLL.insert(product);
                gui.tableFiller(new ProductDAO(), gui.tableProduct, Product.class);
                gui.tableClient.repaint();
                gui.tableClient.revalidate();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Nu putem insera un nou produs!", "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Facem o comanda
     */
    public class AddBtnOrder implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] fields = gui.dateFields(OrderTable.class);
            OrderBLL orderBLL = new OrderBLL();
            ProductBLL productBLL = new ProductBLL();

            try {
                Product product = productBLL.findById(Integer.parseInt(fields[4]));
                OrderTable order = new OrderTable(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2], fields[3], Integer.parseInt(fields[4]));

                if(order.getOrderQuantity() <= product.getProductStock()) {

                    orderBLL.insert(order);
                    product.setProductStock(product.getProductStock() - order.getOrderQuantity()); //decrementam cantitatea
                    productBLL.update(product); //facem update la date */
                    orderBLL.insert(order);

                    PrintWriter printWriter = new PrintWriter("Bill.txt", "UTF-8");
                    printWriter.println("----------------------------");
                    printWriter.println("Client :" + order.getClientName());
                    printWriter.println("Produs :" + order.getProductName());
                    printWriter.println("Cantitate :" + order.getOrderQuantity());
                    printWriter.println("Pret: " + order.getOrderQuantity() * product.getProductPrice());
                    printWriter.println("VA MULTUMIM PENTRU COMANDA!");
                    printWriter.println("----------------------------");
                    printWriter.close();
                } else {
                    throw new Exception("Nu sunt produse pe stoc pentru a face comanda!");
                }

                gui.tableFiller(new OrderDAO(), gui.tableOrder, OrderTable.class);
                gui.tableOrder.repaint();
                gui.tableOrder.revalidate();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Nu putem face o comanda!", "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Editam informatiile unui client
     */
    public class UpdateClient implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] fields  = gui.dateFields(Client.class);
            ClientBLL clientBLL = new ClientBLL();

            try {
                Client client = new Client(Integer.parseInt(fields[0]), fields[1], fields[2]);
                clientBLL.update(client);
                gui.tableFiller(new ClientDAO(), gui.tableClient, Client.class);
                gui.tableClient.repaint();
                gui.tableClient.revalidate();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Nu putem edita clientul!", "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Editam informatiile unui produs
     */
    public class UpdateProduct implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] fields  = gui.dateFields(Product.class);
            ProductBLL productBLL = new ProductBLL();

            try {
                Product product = new Product(Integer.parseInt(fields[0]), fields[1], Integer.parseInt(fields[2]), Integer.parseInt(fields[3]));
                productBLL.update(product);
                gui.tableFiller(new ProductDAO(), gui.tableProduct, Product.class);
                gui.tableClient.repaint();
                gui.tableClient.revalidate();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Nu putem edita produsul!", "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Stergem un client din baza de date dupa id-ul sau
     */
    public class DeleteClient implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] fields = gui.dateFields(Client.class);
            ClientBLL clientBLL = new ClientBLL();

            try {
                int id = clientBLL.delete(Integer.parseInt(fields[0]));
                gui.tableFiller(new ClientDAO(), gui.tableClient, Client.class);
                gui.tableClient.repaint();
                gui.tableClient.revalidate();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Nu putem sterge clientul!", "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Stergem un produs din baza de date dupa id
     */
    public class DeleteProduct implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String[] fields = gui.dateFields(Product.class);
            ProductBLL productBLL = new ProductBLL();

            try {
                int id = productBLL.delete(Integer.parseInt(fields[0]));
                gui.tableFiller(new ProductDAO(), gui.tableProduct, Product.class);
                gui.tableClient.repaint();
                gui.tableClient.revalidate();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Nu putem sterge produsul!", "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
