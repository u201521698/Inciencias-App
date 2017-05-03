package pe.edu.upc.incident.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by proyecto on 17/04/2017.
 */

public class Message {
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String conversationId;
    private Incident incident;
    private String longitude;
    private String latitude;
    private String toUser;

    public String getAuthor() {
        return author;
    }

    public Message setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getTitle() {return title;}

    public Message setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getConversationId() {return conversationId;}

    public Message setConversationId(String conversationId) {
        this.conversationId = conversationId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Message setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Message setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getLatitude() {
        return latitude;
    }

    public Message setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String  getLongitude() {
        return longitude;
    }

    public Message setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public Message setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
        return this;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public Message setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
        return this;
    }

    public String getToUser() {
        return toUser;
    }

    public Message setToUser(String toUser) {
        this.toUser = toUser;
        return this;
    }


    public Incident getIncident() {
        return incident;
    }

    public Message setIncident(Incident incident) {
        this.incident = incident;
        return this;
    }



    public static Message build(JSONObject jsonArticle, Incident incident) {
        if(jsonArticle == null) return null;
        if(incident == null) return null;
        Message message = new Message();
        try {
            message.setAuthor(jsonArticle.getString("from_user"))
                    .setTitle(jsonArticle.getString("name"))
                    .setDescription(jsonArticle.getString("text"))
                    .setUrlToImage(jsonArticle.getString("imagecode"))
                    .setUrl(jsonArticle.getString("id"))
                    .setPublishedAt(jsonArticle.getString("date_time"))
                    .setToUser(jsonArticle.getString("to_user"))
                    .setConversationId(jsonArticle.getString("conversationId"))
                    .setLongitude(jsonArticle.getString("longitude"))
                    .setLatitude(jsonArticle.getString("latitude"))
                    .setIncident(incident);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static List<Message> build(JSONArray jsonArticles, Incident incident) {
        if(jsonArticles == null) return null;
        if(incident == null) return null;
        int length = jsonArticles.length();
        List<Message> messages = new ArrayList<>();
        for(int i = 0; i < length; i++) {
            try {
                if(incident.getId()==jsonArticles.getJSONObject(i).getString("conversationId")) {
                    String wid = jsonArticles.getJSONObject(i).getString("id");
                    //for(int j = 0; j < messages.length(); j++){

                    //}

                    messages.add(0, Message.build(jsonArticles.getJSONObject(i), incident));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return messages;
    }
}
