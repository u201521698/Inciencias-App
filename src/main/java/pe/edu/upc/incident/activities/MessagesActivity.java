package pe.edu.upc.incident.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.incident.IncidentApp;
import pe.edu.upc.incident.R;
import pe.edu.upc.incident.adapters.MessageAdapter;
import pe.edu.upc.incident.models.Message;
import pe.edu.upc.incident.models.Incident;
import pe.edu.upc.incident.network.IncidentApi;

public class MessagesActivity extends AppCompatActivity {
    RecyclerView                articlesRecyclerView;
    MessageAdapter messageAdapter;
    RecyclerView.LayoutManager  articlesLayoutManager;
    List<Message> messages;
    Incident incident;
    int                         spanCount;
    private static String       TAG = "Indicent";
    private static String       username = "drodriguez";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        articlesRecyclerView = (RecyclerView) findViewById(R.id.articlesRecyclerView);
        messageAdapter = new MessageAdapter();
        messages = new ArrayList<>();
        messageAdapter.setMessages(messages);

        articlesLayoutManager = new LinearLayoutManager(this);
        articlesRecyclerView.setAdapter(messageAdapter);
        articlesRecyclerView.setLayoutManager(articlesLayoutManager);
        incident = IncidentApp.getInstance().getCurrentSource();
        updateArticles();

   //     new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        Toast.makeText(MessagesActivity.this, "timer", Toast.LENGTH_LONG).show();
//                    }
//                },
//                3000);


    }

    private void updateArticles() {
        AndroidNetworking.get(IncidentApi.ARTICLES_URL)
                .addQueryParameter("incident", incident.getId())
                .addHeaders("cookie","uname="+username+ "; path=/;admin=0; path=/;")
                .setTag(TAG)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            messages = Message.build(response.getJSONObject("data").getJSONArray("messages"), incident);
                            if(messages.size()!= messageAdapter.getMessages().size()) {
                                Toast.makeText(MessagesActivity.this, "timer", Toast.LENGTH_LONG).show();
                                messageAdapter.setMessages(messages);
                                messageAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(MessagesActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                         //   return;
                        }

                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        updateArticles()    ;
                                    }
                                },
                                4000);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(MessagesActivity.this, anError.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, anError.getLocalizedMessage());

                    }
                });
    }
    public void onClick(View view) {
        String wid = ((EditText)findViewById(R.id.editText)).getText().toString();
        ((EditText)findViewById(R.id.editText)).setText("");
        AndroidNetworking.post(IncidentApi.MESSAGE_URL)
                .addQueryParameter("text", wid)
                .addQueryParameter("from_user", username)
                .addQueryParameter("to_user", "*")
                .addQueryParameter("conversationid", incident.getId())
                .addHeaders("cookie","uname="+username+ "; path=/;admin=0; path=/;")
                .setTag(TAG)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            messageAdapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            Toast.makeText(MessagesActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            return;
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(MessagesActivity.this, anError.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, anError.getLocalizedMessage());

                    }
                });

    }

}
