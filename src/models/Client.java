package models;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String numPassport;
    private String cin;
    private String adresse;

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumPassport() {
        return numPassport;
    }

    public void setNumPassport(String numPassport) {
        this.numPassport = numPassport;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return nom +" "+prenom+ " | " + numPassport;
    }

    @Override
    public boolean equals(Object obj) {
    	if (obj == null || !(obj instanceof Client) ) {
			return false;
		}
    	Client client = (Client) obj;
    	
    	return client.cin.equals(cin) ;
    }
}
