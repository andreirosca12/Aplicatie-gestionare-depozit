package dao;

import model.Client;
import model.Produs;

public class ProdusDao extends AbstractDAO<Produs>{
    public Produs findById(int id) {
    return super.findById(id);
}
    public int insert(Produs produs) {
        return super.insert(produs);
    }
    public void update(Produs produs) {
        super.update(produs);
    }
    public void delete(int id) {
        super.delete(id);
    }
}
