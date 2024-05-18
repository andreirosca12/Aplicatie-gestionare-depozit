package dao;

import model.Comanda;

public class ComandaDao extends AbstractDAO<Comanda>{
    public Comanda findById(int id) {
        return super.findById(id);
    }
    public int insert(Comanda comanda) {
        return super.insert(comanda);
    }
    public void update(Comanda comanda) {
        super.update(comanda);
    }
    public void delete(int id) {
        super.delete(id);
    }
}
