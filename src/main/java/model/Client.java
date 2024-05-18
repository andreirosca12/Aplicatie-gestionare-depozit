package model;

public class Client {
    private int clientId;
    private String nume;
    private String email;
    private String adresa;

    /**
     * Constructor implicit.
     */
    public Client() {
    }

    /**
     * Constructor cu toti parametrii.
     *
     * @param clientId ID-ul clientului.
     * @param nume     Numele clientului.
     * @param email    Email-ul clientului.
     * @param adresa   Adresa clientului.
     */
    public Client(int clientId, String nume, String email, String adresa) {
        this.clientId = clientId;
        this.nume = nume;
        this.email = email;
        this.adresa = adresa;
    }

    /**
     * Constructor fara ID, folosit pentru crearea de noi clienti.
     *
     * @param nume   Numele clientului.
     * @param email  Email-ul clientului.
     * @param adresa Adresa clientului.
     */
    public Client(String nume, String email, String adresa) {
        this.nume = nume;
        this.email = email;
        this.adresa = adresa;
    }

    /**
     * Obtine ID-ul clientului.
     *
     * @return ID-ul clientului.
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Seteaza ID-ul clientului.
     *
     * @param clientId noul ID al clientului.
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * Obtine numele clientului.
     *
     * @return numele clientului.
     */
    public String getNume() {
        return nume;
    }

    /**
     * Seteaza numele clientului.
     *
     * @param nume noul nume al clientului.
     */
    public void setNume(String nume) {
        this.nume = nume;
    }

    /**
     * Obtine email-ul clientului.
     *
     * @return email-ul clientului.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Seteaza email-ul clientului.
     *
     * @param email noul email al clientului.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtine adresa clientului.
     *
     * @return adresa clientului.
     */
    public String getAdresa() {
        return adresa;
    }

    /**
     * Seteaza adresa clientului.
     *
     * @param adresa noua adresa a clientului.
     */
    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    /**
     * Returneaza o reprezentare sub forma de string a obiectului Client.
     *
     * @return string care contine informatiile clientului.
     */
    @Override
    public String toString() {
        return "Client : clientId=" + clientId + " ,nume=" + nume + " ,email=" + email + " ,adresa=" + adresa + "\n";
    }
}