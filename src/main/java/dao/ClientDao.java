package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Client;

public class ClientDao extends AbstractDAO<Client>{
    public Client findById(int id) {
        return super.findById(id);
    }
    public List<Client> findAll() {
        return super.findAll();
    }
    public int insert(Client client) {
        return super.insert(client);
    }
    public void update(Client client) {
        super.update(client);
    }
    public void delete(int id) {
        super.delete(id);
    }
}
