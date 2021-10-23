package fr.univavignon.projfinal;

import java.util.List;

public class Category {

    private String nameCategory;
    private List<String> objetMusees;

    public Category(){}

    public Category(String nameCategory){
        this.nameCategory = nameCategory;
    }

    public void setNameCategory(String name){
        nameCategory = name;
    }

    public void setObjetMusee(String obj){
        objetMusees.add(obj);
    }

    public String getNameCategory(){
        return nameCategory;
    }

    public List<String> getObjetMusee(){
        return objetMusees;
    }
}
