package pe.edu.upc.incident.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by proyecto on 10/04/2017.
 */

public class Incident {
    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String language;
    private String country;
    private String dateTime;


    public String getId() {
        return id;
    }

    public Incident setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Incident setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Incident setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Incident setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public Incident setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getLanguage() {
        return language;
    }

    public Incident setLanguage(String language) {
        this.language = language;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Incident setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Incident setDateTime(String dateTime) {
        this.dateTime = dateTime;
        return this;
    }


    public static Incident build(JSONObject jsonSource) {
        if(jsonSource == null) return null;
        Incident incident = new Incident();
        try {

            incident.setId(jsonSource.getString("id"))
                  .setName(jsonSource.getString("type"))
                  .setDescription(jsonSource.getString("text"))
                  .setUrl(jsonSource.getString("username"))
                  .setCategory(jsonSource.getString("name"))
                  .setDateTime(jsonSource.getString("date_time"))
                  .setCountry(jsonSource.getString("address"))
                  .setLanguage(jsonSource.getString("imagecode"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return incident;
    }


    public static List<Incident> build(JSONArray jsonSources) {
        if(jsonSources == null) return null;
        int length = jsonSources.length();
        List<Incident> incidents = new ArrayList<>();
        for(int i = 0; i < length; i++) {
            try {
                incidents.add(0, Incident.build(jsonSources.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return incidents;
    }

}
