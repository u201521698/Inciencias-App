package pe.edu.upc.incident;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

import pe.edu.upc.incident.models.Incident;
import pe.edu.upc.incident.models.Message;
import pe.edu.upc.incident.network.IncidentApi;

/**
 * Created by proyecto on 10/04/2017.
 */

public class IncidentApp extends Application {
    // Singleton Pattern Implementation
    private static IncidentApp instance;
    IncidentApi incidentApi = new IncidentApi();

    public IncidentApp() {
        super();
        instance = this;
    }

    public static IncidentApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidNetworking.initialize(getApplicationContext());
    }

    // Delegate Pattern Implementation

    public void setCurrentSource(Incident incident) {
        incidentApi.setCurrentIncident(incident);
    }

    public Incident getCurrentSource() {
        return incidentApi.getCurrentIncident();
    }

    public void setCurrentArticle(Message message) {
        incidentApi.setCurrentMessage(message);
    }

    public Message getCurrentArticle() {
        return incidentApi.getCurrentMessage();
    }

}
