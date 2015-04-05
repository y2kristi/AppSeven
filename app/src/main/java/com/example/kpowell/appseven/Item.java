package com.example.kpowell.appseven;

/**
 * Created by kpowell on 4/4/15.
 */
public class Item {

    private int id;
    private String title;
    private String description;
    private String date;

    public Item(){}

    public Item(String a, String b, String c){

        title = a;
        description = b;
        date = c;

    }

    public Item(int i, String a, String b, String c){

        id = i;
        title = a;
        description = b;
        date = c;

    }

    public int getId(){

        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public String getDate(){
        return date;
    }

    public void setTitle(String a){
        title = a;
    }

    public void setDescription(String b){
        description = b;
    }

    public void setDate(String c){
        date = c;

    }
    public void setId(int d){
        id = d;
    }

    public String toString() {
        return id + "\n" + title + "\n" + description + "\n" + date;
    }
}
