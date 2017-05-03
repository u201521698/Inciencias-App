package pe.edu.upc.incident.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.edu.upc.incident.R;
import pe.edu.upc.incident.adapters.IncidentsAdapter;
import pe.edu.upc.incident.models.Incident;
import pe.edu.upc.incident.network.IncidentApi;

public class MainActivity extends AppCompatActivity {
    RecyclerView sourcesRecyclerView;
    RecyclerView.LayoutManager sourcesLayoutManager;
    IncidentsAdapter incidentsAdapter;
    List<Incident> incidents;
    private static String TAG = "Indicent";
    private static String username = "drodriguez";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        sourcesRecyclerView = (RecyclerView) findViewById(R.id.sourcesRecyclerView);
        sourcesLayoutManager = new LinearLayoutManager(this);
        incidentsAdapter = new IncidentsAdapter();
        incidents = new ArrayList<>();
        incidentsAdapter.setIncidents(incidents);
        sourcesRecyclerView.setLayoutManager(sourcesLayoutManager);
        sourcesRecyclerView.setAdapter(incidentsAdapter);
        updateSources();
    }

    private void updateSources() {
        AndroidNetworking.get(IncidentApi.SOURCES_URL)
                .addHeaders("cookie","uname="+username+ "; path=/;admin=0; path=/;")
                .setPriority(Priority.LOW)
                .setTag(TAG)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            incidents = Incident.build(response.getJSONObject("data").getJSONArray("conversations"));
                            incidentsAdapter.setIncidents(incidents);
                            incidentsAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(MainActivity.this, anError.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, anError.getLocalizedMessage());
                    }

                });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            try {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            } catch (Exception e) {
                Toast.makeText(MainActivity.this, e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        Log.d(TAG, "Orientation Changed:" + Integer.toString(newConfig.orientation));
//        //spanCount = (newConfig.orientation == ORIENTATION_LANDSCAPE) ? 3 : 2;
//        ((GridLayoutManager)sourcesRecyclerView.getLayoutManager()).setSpanCount(spanCount);
//    }
}
