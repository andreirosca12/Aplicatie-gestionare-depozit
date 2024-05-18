package dao;

import model.Factura;

import java.util.List;

public class FacturaDao extends AbstractDAO<Factura>{
    public Factura findById(int id) {
        return super.findById(id);
    }
    public List<Factura> findAll() {
        return super.findAll();
    }
    public int insert(Factura factura) {
        return super.insert(factura);
    }
}
