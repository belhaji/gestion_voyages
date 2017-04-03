package models;

import java.util.Date;

public class Reservation {
    private int id;
    private String type;
    private String classe;
    private Date date;
    private Client client;
    private Ligne ligne;

    public Reservation(Client client, Ligne ligne) {
        this.client = client;
        this.ligne = ligne;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Ligne getLigne() {
        return ligne;
    }

    public void setLigne(Ligne ligne) {
        this.ligne = ligne;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", classe='" + classe + '\'' +
                ", date=" + date +
                ", client=" + client +
                ", ligne=" + ligne +
                '}';
    }
    
}
