package model;

public class Produs {
    private int produsId;
    private String nume;
    private int pret;
    private int stoc;

    /**
     * Constructor implicit.
     */
    public Produs() {
    }

    /**
     * Constructor cu toti parametrii.
     *
     * @param produsId ID-ul produsului.
     * @param nume     Numele produsului.
     * @param pret     Pretul produsului.
     * @param stoc     Stocul disponibil al produsului.
     */
    public Produs(int produsId, String nume, int pret, int stoc) {
        this.produsId = produsId;
        this.nume = nume;
        this.pret = pret;
        this.stoc = stoc;
    }

    /**
     * Constructor fara ID, folosit pentru crearea unui nou produs.
     *
     * @param nume Numele produsului.
     * @param pret Pretul produsului.
     * @param stoc Stocul disponibil al produsului.
     */
    public Produs(String nume, int pret, int stoc) {
        this.nume = nume;
        this.pret = pret;
        this.stoc = stoc;
    }

    /**
     * Obtine ID-ul produsului.
     *
     * @return ID-ul produsului.
     */
    public int getProdusId() {
        return produsId;
    }

    /**
     * Seteaza ID-ul produsului.
     *
     * @param produsId ID-ul nou al produsului.
     */
    public void setProdusId(int produsId) {
        this.produsId = produsId;
    }

    /**
     * Obtine numele produsului.
     *
     * @return Numele produsului.
     */
    public String getNume() {
        return nume;
    }

    /**
     * Seteaza numele produsului.
     *
     * @param nume Noul nume al produsului.
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     * Obtine pretul produsului.
     *
     * @return Pretul produsului.
     */
    public int getPret() {
        return pret;
    }

    /**
     * Seteaza pretul produsului.
     *
     * @param pret Noul pret al produsului.
     */
    public void setPret(int pret) {
        this.pret = pret;
    }

    /**
     * Obtine stocul disponibil al produsului.
     *
     * @return Stocul disponibil al produsului.
     */
    public int getStoc() {
        return stoc;
    }

    /**
     * Seteaza stocul disponibil al produsului.
     *
     * @param stoc Noul stoc disponibil al produsului.
     */
    public void setStoc(int stoc) {
        this.stoc = stoc;
    }

    /**
     * Returneaza o reprezentare sub forma de string a obiectului Produs.
     *
     * @return string care contine informatiile despre produs.
     */
    @Override
    public String toString() {
        return "Produs : produsId=" + produsId + " ,nume produs=" + nume + " ,pret=" + pret + " ,stoc=" + stoc;
    }
}
