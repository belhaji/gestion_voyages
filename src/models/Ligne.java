package models;

public class Ligne {
    private int id;
    private String airoportAller;
    private String airoportArriver;
    private double prixClass;
    private double prixNormale;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAiroportAller() {
        return airoportAller;
    }

    public void setAiroportAller(String airoportAller) {
        this.airoportAller = airoportAller;
    }

    public String getAiroportArriver() {
        return airoportArriver;
    }

    public void setAiroportArriver(String airoportArriver) {
        this.airoportArriver = airoportArriver;
    }

    public double getPrixClass() {
        return prixClass;
    }

    public void setPrixClass(double prixClass) {
        this.prixClass = prixClass;
    }

    public double getPrixNormale() {
        return prixNormale;
    }

    public void setPrixNormale(double prixNormale) {
        this.prixNormale = prixNormale;
    }

    @Override
    public String toString() {
        return "Ligne{" +
                "id=" + id +
                ", airoportAller='" + airoportAller + '\'' +
                ", airoportArriver='" + airoportArriver + '\'' +
                ", prixClass=" + prixClass +
                ", prixNormale=" + prixNormale +
                '}';
    }
}
