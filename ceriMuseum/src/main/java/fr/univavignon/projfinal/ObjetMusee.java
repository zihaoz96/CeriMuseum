package fr.univavignon.projfinal;

import java.io.Serializable;

public class ObjetMusee implements Serializable {

    private long id; // used for the _id colum of the db helper

    private String idObjet;
    private String picture;
    private String technicalDetails;
    private String name;
    private String brand;
    private String timeFrame;
    private String category;
    private String working;
    private String description;
    private String year;

    public ObjetMusee(String idObjet){this.idObjet = idObjet;}
    public ObjetMusee(long id, String idObjet, String name, String brand, String category, String picture, String technicalDetails, String timeFrame, String year, String description, String working){
        this.id = id;
        this.idObjet = idObjet;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.picture = picture;
        this.technicalDetails = technicalDetails;
        this.timeFrame = timeFrame;
        this.year = year;
        this.description = description;
        this.working = working;
    }

    public long getId(){return id;}
    public String getIdObjet(){return idObjet;}
    public String getName() {return name;}
    public String getBrand() {return brand;}
    public String getCategory() {return category;}
    public String getDescription(){return description;}
    public String getYear(){return year;}
    public String getWorking() {return working;}
    public String getTimeFrame(){return timeFrame;}
    public String getTechnicalDetails(){return technicalDetails;}
    public String getPictures(){return  picture;}

    public void setName(String name){this.name = name;}
    public void setBrand(String brand) {this.brand = brand;}
    public void setCategory(String category) {this.category = category;}
    public void setDescription(String description){this.description = description;}
    public void setYear(String year){this.year = year;}
    public void setWorking(String working){this.working = working;}
    public void setTimeFrame(String timeFrame) {this.timeFrame = timeFrame;}
    public void setPicture(String picture){this.picture = picture;}
    public void setTechnicalDetails(String technicalDetails) {this.technicalDetails = technicalDetails;}

}
