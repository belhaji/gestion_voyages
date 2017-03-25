package models;

public class Avion {
    private int id;
    private String nom;
    private Integer nbPlace;
    private Pilote pilote;

    public Avion(Pilote pilote) {
        this.pilote = pilote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(Integer nbPlace) {
        this.nbPlace = nbPlace;
    }

    public Pilote getPilote() {
        return pilote;
    }

    public void setPilote(Pilote pilote) {
        this.pilote = pilote;
    }

    @Override
    public String toString() {
        return "Avion{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", nbPlace=" + nbPlace +
                ", pilote=" + pilote +
                '}';
    }
}
