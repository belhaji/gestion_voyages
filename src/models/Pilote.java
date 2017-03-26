package models;

public class Pilote {
	private int id;
	private String nom;
	private String prenom;
	private String matricule;

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

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Pilote)) {
			return false;
		}
		Pilote pilote = (Pilote) obj;
		return (pilote.nom.equals(nom) && pilote.matricule.equals(matricule) && pilote.prenom.equals(prenom));
	}

	@Override
	public String toString() {
		return nom + " | " + matricule;
	}
}
