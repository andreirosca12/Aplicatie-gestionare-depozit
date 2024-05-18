package model;

public class Factura {
    private int facturaId;
    private int comandaId;
    private int total;

    /**
     * Constructor implicit.
     */
    public Factura() {
    }

    /**
     * Constructor cu toti parametrii.
     *
     * @param facturaId ID-ul facturii.
     * @param comandaId ID-ul comenzii pentru care a fost generata factura.
     * @param total     Totalul facturii.
     */
    public Factura(int facturaId, int comandaId, int total) {
        this.facturaId = facturaId;
        this.comandaId = comandaId;
        this.total = total;
    }

    /**
     * Constructor fara ID, folosit pentru crearea unei noi facturi.
     *
     * @param comandaId ID-ul comenzii pentru care se genereaza factura.
     * @param total     Totalul facturii.
     */
    public Factura(int comandaId, int total) {
        this.comandaId = comandaId;
        this.total = total;
    }

    /**
     * Obtine ID-ul facturii.
     *
     * @return ID-ul facturii.
     */
    public int getFacturaId() {
        return facturaId;
    }

    /**
     * Obtine ID-ul comenzii pentru care a fost generata factura.
     *
     * @return ID-ul comenzii.
     */
    public int getComandaId() {
        return comandaId;
    }

    /**
     * Obtine totalul facturii.
     *
     * @return Totalul facturii.
     */
    public int getTotal() {
        return total;
    }

    /**
     * Seteaza totalul facturii.
     *
     * @param total Noul total al facturii.
     */
    public void setTotal(int total) {
        this.total = total;
    }
}