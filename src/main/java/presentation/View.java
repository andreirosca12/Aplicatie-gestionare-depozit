package presentation;

import dao.*;
import model.Client;
import model.Comanda;
import model.Factura;
import model.Produs;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class View extends JFrame {
    private JPanel panel1;
    private JPanel panel2;
    private JButton clientPageButton;
    private JButton produsPageButton;
    private JButton comandaPageButton;



    public <T> JTable tabele(List<T> objects) {
        DefaultTableModel model = new DefaultTableModel();

        if (!objects.isEmpty()) {
            Class<?> objectClass = objects.get(0).getClass();
            Field[] fields = objectClass.getDeclaredFields();
            for (Field field : fields) {
                model.addColumn(field.getName());
            }
            for (T obj : objects) {
                Object[] rowData = new Object[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    fields[i].setAccessible(true);
                    try {
                        rowData[i] = fields[i].get(obj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                model.addRow(rowData);
            }
        }
        return new JTable(model);
    }


    private void refreshClientTable() {
        // Get the updated data from the database
        ClientDao clientDao= new ClientDao();
        List<Client> updatedClients = clientDao.findAll();
        // Clear the table model
        DefaultTableModel model = (DefaultTableModel) tabele(updatedClients).getModel();
        model.setRowCount(0); // Clear existing rows
        // Add new data to the table model
        for (Client client : updatedClients) {
            model.addRow(new Object[]{client.getClientId(), client.getNume(), client.getEmail(), client.getAdresa()});
        }
    }

    /**
     * Constructor implicit care initializeaza interfata grafica.
     */
    public View() {
        setTitle("");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);

        // Create panels
        panel1 = new JPanel(new GridLayout(2, 1));
        panel2 = new JPanel();

        JPanel panelClient = new JPanel(new GridLayout(6, 1));
        JPanel panelProdus = new JPanel(new GridLayout(6, 1));
        JPanel panelComanda = new JPanel(new GridLayout(5, 1));

        panel1.add(new JLabel("Ce tip de operatiune doriti sa efectuati?", SwingConstants.CENTER));
        clientPageButton = new JButton("Clienti");
        produsPageButton = new JButton("Produse");
        comandaPageButton = new JButton("Comenzi");
        panel2.add(clientPageButton);
        panel2.add(produsPageButton);
        panel2.add(comandaPageButton);
        panel1.add(panel2);
       JButton backButton1=new JButton("Back");
        JButton backButton2=new JButton("Back");
        JButton backButton3=new JButton("Back");


        panelProdus.add(backButton1);
        panelClient.add(backButton2);
        panelComanda.add(backButton3);

        // Add panels to the content pane
        getContentPane().setLayout(new CardLayout());
        getContentPane().add(panel1, "panel1");
        getContentPane().add(panelClient, "panelClient");
        getContentPane().add(panelProdus, "panelProdus");
        getContentPane().add(panelComanda, "panelComanda");

        ClientDao clientDao = new ClientDao();
        JPanel panelClient2= new JPanel(new GridLayout(4, 2));
        panelClient.add(new JScrollPane(tabele(clientDao.findAll())));

        JTable tabel1 = tabele(clientDao.findAll());
        panelComanda.add(new JScrollPane(tabel1));
        panelClient.add(panelClient2);

        panelClient2.add(new JLabel("IdClient"));
        JTextField JTextField = new JTextField();
        panelClient2.add(JTextField);
        panelClient2.add(new JLabel("Nume"));
        JTextField JTextField1 = new JTextField();
        panelClient2.add(JTextField1);
        panelClient2.add(new JLabel("Email"));
        JTextField JTextField2 = new JTextField();
        panelClient2.add(JTextField2);
        panelClient2.add(new JLabel("Adresa"));
        JTextField JTextField3 = new JTextField();
        panelClient2.add(JTextField3);
        JButton insertButton = new JButton("Insert");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        panelClient.add(insertButton);
        panelClient.add(updateButton);
        panelClient.add(deleteButton);




        ProdusDao produsDao = new ProdusDao();
        JPanel panelProdus2= new JPanel(new GridLayout(4, 2));
        panelProdus.add(new JScrollPane(tabele(produsDao.findAll())));

        JTable tabel2 = tabele(produsDao.findAll());
        panelComanda.add(new JScrollPane(tabel2));
        panelProdus.add(panelProdus2);

        panelProdus2.add(new JLabel("IdProdus"));
        JTextField JTextField9 = new JTextField();
        panelProdus2.add(JTextField9);

        panelProdus2.add(new JLabel("Nume"));
        JTextField JTextField4 = new JTextField();
        panelProdus2.add(JTextField4);
        panelProdus2.add(new JLabel("Pret"));
        JTextField JTextField5 = new JTextField();
        panelProdus2.add(JTextField5);
        panelProdus2.add(new JLabel("Stoc"));
        JTextField JTextField6 = new JTextField();
        panelProdus2.add(JTextField6);
        JButton insertButton2 = new JButton("Insert");
        JButton updateButton2 = new JButton("Update");
        JButton deleteButton2 = new JButton("Delete");
        panelProdus.add(insertButton2);
        panelProdus.add(updateButton2);
        panelProdus.add(deleteButton2);


        JPanel panelComanda2= new JPanel();
        panelComanda.add(panelComanda2);
        panelComanda2.add(new Label("cantitate"));
        TextField TextField10 = new TextField();
        panelComanda2.add(TextField10);
        JButton insertButton3 = new JButton("Creare comanda");
        panelComanda2.add(insertButton3);




        insertButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the selected row index
                int selectedRow = tabel2.getSelectedRow();
                int selectedRow2 = tabel1.getSelectedRow();
                Object value = tabele(produsDao.findAll()).getValueAt(selectedRow, 0);
                int cantitate = (Integer) tabel2.getValueAt(selectedRow, 3);
                Object value2 = tabele(clientDao.findAll()).getValueAt(selectedRow2, 0);
                if(cantitate<Integer.parseInt(TextField10.getText()))
                {
                    JOptionPane.showMessageDialog(null, "Cantitatea introdusa este mai mare decat stocul");
                }
                else
                {
                    Comanda comanda = new Comanda(Integer.parseInt(value2.toString()), Integer.parseInt(value.toString()), Integer.parseInt(TextField10.getText()));
                    ComandaDao comandaDao = new ComandaDao();
                    int x= comandaDao.insert(comanda);
                    FacturaDao facturaDao = new FacturaDao();
                    facturaDao.insert(new Factura(x,(Integer) tabel2.getValueAt(selectedRow, 2)*Integer.parseInt(TextField10.getText())));
                    ProdusDao produsDao = new ProdusDao();
                    produsDao.update(new Produs(Integer.parseInt(value.toString()),(String) tabel2.getValueAt(selectedRow, 1),(Integer) tabel2.getValueAt(selectedRow, 2), cantitate-Integer.parseInt(TextField10.getText())));
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clientDao.delete(Integer.parseInt(JTextField.getText()));
                panelClient.removeAll();
                panelClient.add(new JScrollPane(tabele(clientDao.findAll())));
                panelClient.add(panelClient2);
                panelClient.add(insertButton);
                panelClient.add(updateButton);
                panelClient.add(deleteButton);
            }
        });
        deleteButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                produsDao.delete(Integer.parseInt(JTextField9.getText()));
                panelProdus.removeAll();
                panelProdus.add(new JScrollPane(tabele(produsDao.findAll())));
                panelProdus.add(panelProdus2);
                panelProdus.add(insertButton2);
                panelProdus.add(updateButton2);
                panelProdus.add(deleteButton2);
            }
        });
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client client = new Client(Integer.parseInt(JTextField.getText()), JTextField1.getText(), JTextField2.getText(), JTextField3.getText());
                clientDao.update(client);
                panelClient.removeAll();
                panelClient.add(new JScrollPane(tabele(clientDao.findAll())));
                panelClient.add(panelClient2);
                panelClient.add(insertButton);
                panelClient.add(updateButton);
                panelClient.add(deleteButton);
            }
        });
        updateButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Produs produs = new Produs(Integer.parseInt(JTextField9.getText()),JTextField4.getText(), Integer.parseInt(JTextField5.getText()), Integer.parseInt(JTextField6.getText()));
                produsDao.update(produs);
                panelProdus.removeAll();
                panelProdus.add(new JScrollPane(tabele(produsDao.findAll())));
                panelProdus.add(panelProdus2);
                panelProdus.add(insertButton2);
                panelProdus.add(updateButton2);
                panelProdus.add(deleteButton2);
            }
        });
        insertButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Produs produs = new Produs(JTextField4.getText(), Integer.parseInt(JTextField5.getText()), Integer.parseInt(JTextField6.getText()));
                produsDao.insert(produs);
                panelProdus.removeAll();
                panelProdus.add(new JScrollPane(tabele(produsDao.findAll())));
                panelProdus.add(panelProdus2);
                panelProdus.add(insertButton2);
                panelProdus.add(updateButton2);
                panelProdus.add(deleteButton2);
            }
        });
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Client client = new Client(JTextField1.getText(), JTextField2.getText(), JTextField3.getText());
                clientDao.insert(client);
                panelClient.removeAll();
                panelClient.add(new JScrollPane(tabele(clientDao.findAll())));
                panelClient.add(panelClient2);
                panelClient.add(insertButton);
                panelClient.add(updateButton);
                panelClient.add(deleteButton);
            }
        });
        clientPageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
                cardLayout.show(getContentPane(), "panelClient");
            }
        });
        produsPageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
                cardLayout.show(getContentPane(), "panelProdus");
            }
        });
        comandaPageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
                cardLayout.show(getContentPane(), "panelComanda");
            }
        });

        backButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
                cardLayout.show(getContentPane(), "panel1");
            }
        });
        backButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
                cardLayout.show(getContentPane(), "panel1");
            }
        });
        backButton3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) getContentPane().getLayout();
                cardLayout.show(getContentPane(), "panel1");
            }
        });
    }

    public static void main(String[] args) {
        View app = new View();
        app.setVisible(true);

        }
}
