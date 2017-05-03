package pe.edu.upc.incident.network;

import pe.edu.upc.incident.models.Incident;
import pe.edu.upc.incident.models.Message;

/**
 * Created by proyecto on 10/04/2017.
 */

public class IncidentApi {
    public static String SOURCES_URL = "http://10.0.2.2:9090/data/conversations";
    public static String ARTICLES_URL = "http://10.0.2.2:9090/data/messages";
    public static String CONVERSATION_URL = "http://10.0.2.2:9090/data/conversation";
    public static String MESSAGE_URL = "http://10.0.2.2:9090/data/message";
    private Incident currentIncident;
    private Message currentMessage;

    public Incident getCurrentIncident() {
        return currentIncident;
    }

    public void setCurrentIncident(Incident currentIncident) {
        this.currentIncident = currentIncident;
    }

    public Message getCurrentMessage() {
        return currentMessage;
    }

    public void setCurrentMessage(Message currentMessage) {
        this.currentMessage = currentMessage;
    }
}
