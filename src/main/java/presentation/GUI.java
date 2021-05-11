package presentation;

import dao.AbstractDAO;
import model.Client;
import model.OrderTable;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;


/**
 * Interfata grafica unde sunt declarate frame-urile si componentele pentru meniul aplicatiei, pentru comenzi, clienti si produse
 */
public class GUI extends JFrame{

    public JFrame clientFrame = new JFrame();
    public JFrame productFrame = new JFrame();
    public JFrame orderFrame = new JFrame();
    public JFrame mainFrame = new JFrame();

    public JPanel panel = new JPanel();
    public JPanel clientPanel = new JPanel();
    public JPanel orderPanel = new JPanel();
    public JPanel productPanel = new JPanel();

    public JTable tableClient = new JTable();
    public JTable tableProduct = new JTable();
    public JTable tableOrder = new JTable();

    public JButton btnMenu1 = new JButton("Clients");
    public JButton btnMenu2 = new JButton("Orders");
    public JButton btnMenu3 = new JButton("Products");

    public JButton addClient = new JButton("Add");
    public JButton addProduct = new JButton("Add");
    public JButton addOrder = new JButton("Add");

    public JButton updateClient = new JButton("Update");
    public JButton updateProduct = new JButton("Update");

    public JButton deleteClient = new JButton("Delete");
    public JButton deleteProduct = new JButton("Delete");

    JTextField[] textClient;
    JTextField[] textProduct;
    JTextField[] textOrder;

    public GUI() {
        clientPanel.add(btnMenu1);
        orderPanel.add(btnMenu2);
        productPanel.add(btnMenu3);

        panel.add(clientPanel);
        panel.add(orderPanel);
        panel.add(productPanel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        addClientFrame();
        addOrderFrame();
        addProductFrame();

        mainFrame.add(panel);
        mainFrame.setTitle("Menu");
        mainFrame.setSize(500, 500);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }

    /**
     * @param abstractDAO
     * @param table
     * @param c
     * Metoda care pune datele in tabel cu ajutorul metodei findAll() din AbstractDAO
     * Ne folosim de obiect de tip Class pentru a evidentia faptul ca aplicatia se adapteaza fiecarui tabel din baza de date
     */
    public void tableFiller(AbstractDAO abstractDAO, JTable table, Class c) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnCount(c.getDeclaredFields().length);
        String[] columns = new String[c.getDeclaredFields().length];
        int ct = 0;

        for(Field field : c.getDeclaredFields()) {
            columns[ct++] = field.getName();
        }

        tableModel.setColumnIdentifiers(columns);
        int i = 0;
        abstractDAO.findAll();

        while(abstractDAO.data[i] != null) {
            tableModel.addRow(abstractDAO.data[i]);
            i++;
        }

        table.setModel(tableModel);
    }

    /**
     * @param table
     * @param insert
     * @param delete
     * @param update
     * @param frame
     * @param c
     * Construim frame-ul pentru fiecare clasa
     */
    public void genericFrame(JTable table, JButton insert, JButton delete, JButton update, JFrame frame, Class c) {
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel p4 = new JPanel();
        JPanel p5 = new JPanel();
        JPanel p6 = new JPanel();
        JPanel p7 = new JPanel();

        JScrollPane scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        table.setRowHeight(20);

        p1.add(scroll);
        if (insert != null) {
            p2.add(insert);
        }
        if(delete != null) {
            p3.add(delete);
        }
        if(update != null) {
            p4.add(update);
        }

        p5.add(p2);
        p5.add(p3);
        p5.add(p4);
        p5.setLayout(new BoxLayout(p5, BoxLayout.Y_AXIS));

        p7.add(coloane(c));
        p7.add(p5);
        p7.setLayout(new BoxLayout(p7, BoxLayout.X_AXIS));

        p6.add(p1);
        p6.add(p7);
        p6.setLayout(new BoxLayout(p6, BoxLayout.Y_AXIS));

        frame.add(p6);
        frame.setSize(600, 700);
        frame.setVisible(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    /**
     * @param c
     * @return
     * Creeaza JLabel-uri si JText-uri in functie de fiecare frame/clasa
     */
    JPanel coloane(Class c) {
        JPanel res = new JPanel();
        switch (c.getName()) {
            case "model.Client":
                textClient = new JTextField[c.getDeclaredFields().length];
                break;
            case "model.Product":
                textProduct = new JTextField[c.getDeclaredFields().length];
                break;
            case "model.OrderTable":
                textOrder = new JTextField[c.getDeclaredFields().length];
                break;
        }
        int index = 0;
        res.setLayout(new BoxLayout(res, BoxLayout.Y_AXIS));
        for (Field f : c.getDeclaredFields()) {
            JPanel p = new JPanel();
            JLabel name = new JLabel(f.getName());
            p.add(name);
            switch (c.getName()) {
                case "model.Client":
                    textClient[index] = new JTextField(10);
                    p.add(textClient[index]);
                    break;
                case "model.Product":
                    textProduct[index] = new JTextField(10);
                    p.add(textProduct[index]);
                    break;
                case "model.OrderTable":
                    textOrder[index] = new JTextField(10);
                    p.add(textOrder[index]);
                    break;
            }
            index++;
            res.add(p);
        }

        return res;
    }

    /**
     * @param c
     * @return
     * Citeste datele din text field-uri in functie de fiecare frame
     */
    String[] dateFields(Class c) {
        String[] campuri = new String[c.getDeclaredFields().length];
        for (int i = 0; i < c.getDeclaredFields().length; i++) {
            switch (c.getName()) {
                case "model.Client":
                    campuri[i] = textClient[i].getText();
                    break;
                case "model.Product":
                    campuri[i] = textProduct[i].getText();
                    break;
                case "model.OrderTable":
                    campuri[i] = textOrder[i].getText();
                    break;
            }
        }

        return campuri;
    }

    /**
     * Creeaza frame-ul pentru functionalitatile clientului
     */
    void addClientFrame() {
        clientFrame.setTitle("Clienti");
        genericFrame(tableClient, addClient, deleteClient, updateClient, clientFrame, Client.class);
    }

    void addMainClientListener(ActionListener listener) {
        btnMenu1.addActionListener(listener);
    }

    /**
     * Creeaza frame-ul pentru a efectua o comanda
     */
    void addOrderFrame() {
        orderFrame.setTitle("Comenzi");
        genericFrame(tableOrder, addOrder, null, null, orderFrame, OrderTable.class);
    }

    void addMainOrderListener(ActionListener actionListener) {
        btnMenu2.addActionListener(actionListener);
    }

    /**
     * Creeaza frame-ul pentru functionalitatile unui produs
     */
    void addProductFrame() {
        productFrame.setTitle("Produse");
        genericFrame(tableProduct, addProduct, deleteProduct, updateProduct, productFrame, Product.class);
    }

    void addMainProductListener(ActionListener actionListener) {
        btnMenu3.addActionListener(actionListener);
    }

    void addBtnClient(ActionListener actionListener) {
        addClient.addActionListener(actionListener);
    }

    void addBtnProduct(ActionListener actionListener) {
        addProduct.addActionListener(actionListener);
    }

    void addBtnOrder(ActionListener actionListener) {
        addOrder.addActionListener(actionListener);
    }

    void updateBtnClient(ActionListener actionListener) {
        updateClient.addActionListener(actionListener);
    }

    void updateBtnProduct(ActionListener actionListener) { updateProduct.addActionListener(actionListener); }

    void deleteClient(ActionListener actionListener) {
        deleteClient.addActionListener(actionListener);
    }

    void deleteProduct(ActionListener actionListener) {
        deleteProduct.addActionListener(actionListener);
    }

}


