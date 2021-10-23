package fr.univavignon.projfinal;

import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class JSONResponseHandlerObjet {

    ObjetDbHelper objetDbHelper;

    public JSONResponseHandlerObjet(ObjetDbHelper objetDbHelper){this.objetDbHelper = objetDbHelper;}


    /**
     * @param response done by the Web service
     * @return A Team with attributes filled with the collected information if response was
     * successfully analyzed
     */
    public void readJsonStream(InputStream response, String searchFor) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(response, "UTF-8"));
        try {
            if (searchFor == "ids"){
                readIds(reader);
            } else if (searchFor == "catalog"){
                readCatalog(reader);
            } else if (searchFor == "categorys"){
                readCategory(reader);
            }

        } finally {
            reader.close();
        }
    }

    public void readIds(JsonReader reader) throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
            objetDbHelper.addObj(new ObjetMusee(reader.nextString()));

        }
        reader.endArray();
    }

    public void readCatalog(JsonReader reader) throws IOException {
        reader.beginObject();

        while (reader.hasNext()) {
            ObjetMusee objetMusee = objetDbHelper.getObjet(reader.nextName());
            reader.beginObject();
            while (reader.hasNext() ) {
                String name = reader.nextName();

                if (name.equals("name")){
                    objetMusee.setName(reader.nextString());

                } else if (name.equals("brand")){
                    String brand = reader.nextString();
                    objetMusee.setBrand(brand);

                } else if (name.equals("categories")){
                    reader.beginArray();

                    String category = "";
                    while (reader.hasNext()){
                        category += reader.nextString() + "\n";
                    }

                    reader.endArray();

                    objetMusee.setCategory(category);

                } else if (name.equals("description")){
                    String description = reader.nextString();
                    objetMusee.setDescription(description);

                } else if (name.equals("year")){
                    int year = reader.nextInt();
                    objetMusee.setYear(""+year);

                } else if (name.equals("timeFrame")){
                    reader.beginArray();

                    String timeFrame = "";
                    while (reader.hasNext()){
                        timeFrame += reader.nextLong() + " ";
                    }

                    reader.endArray();

                    objetMusee.setTimeFrame(timeFrame);

                } else if (name.equals("technicalDetails")){
                    reader.beginArray();

                    String technical = "";
                    while (reader.hasNext()){
                        technical += reader.nextString() + "\n ";
                    }

                    reader.endArray();

                    objetMusee.setTechnicalDetails(technical);

                } else if (name.equals("working")){
                    boolean working;
                    working = reader.nextBoolean();

                    if (working){
                        objetMusee.setWorking("True");
                    } else {
                        objetMusee.setWorking("False");
                    }

                } else if (name.equals("pictures")){
                    reader.beginObject();

                    String pictureInfos = "";
                    while (reader.hasNext()){

                        String pictureID = reader.nextName();
                        String pictureDetail = reader.nextString();

                        pictureInfos += pictureID + ":" + pictureDetail + "|";
                    }

                    reader.endObject();
                    objetMusee.setPicture(pictureInfos);

                } else{
                    reader.skipValue();
                }

            }
            reader.endObject();
            objetDbHelper.updateObj(objetMusee);
        }
        reader.endObject();
    }

    public void readCategory(JsonReader reader) throws IOException  {
        reader.beginArray();
        int i = 0;
        while (reader.hasNext()){
            MainActivity.categories.add(reader.nextString());
        }
        reader.endArray();
    }
}
