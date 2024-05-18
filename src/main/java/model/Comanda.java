package model;

public class Comanda {
    private int comandaId;
    private int clientId;
    private int produsId;
    private int cantitate;

    /**
     * Constructor implicit.
     */
    public Comanda() {
    }

    /**
     * Constructor cu toți parametrii.
     *
     * @param comandaId ID-ul comenzii.
     * @param clientId  ID-ul clientului care a plasat comanda.
     * @param produsId  ID-ul produsului comandat.
     * @param cantitate Cantitatea produsului comandat.
     */
    public Comanda(int comandaId, int clientId, int produsId, int cantitate) {
        this.comandaId = comandaId;
        this.clientId = clientId;
        this.produsId = produsId;
        this.cantitate = cantitate;
    }

    /**
     * Constructor fără ID, folosit pentru crearea unei noi comenzi.
     *
     * @param clientId  ID-ul clientului care plasează comanda.
     * @param produsId  ID-ul produsului comandat.
     * @param cantitate Cantitatea produsului comandat.
     */
    public Comanda(int clientId, int produsId, int cantitate) {
        this.clientId = clientId;
        this.produsId = produsId;
        this.cantitate = cantitate;
    }

    /**
     * Returnează o reprezentare sub formă de string a obiectului Comanda.
     *
     * @return string care conține informațiile despre comandă.
     */
    @Override
    public String toString() {
        return "Comanda : comandaId=" + comandaId + " ,clientId=" + clientId + " ,produsId=" + produsId + " ,cantitate=" + cantitate;
    }
}
