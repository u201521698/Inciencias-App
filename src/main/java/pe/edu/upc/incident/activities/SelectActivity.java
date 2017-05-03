package pe.edu.upc.incident.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import pe.edu.upc.incident.R;
import pe.edu.upc.incident.models.Incident;
import pe.edu.upc.incident.network.IncidentApi;

import static android.app.PendingIntent.getActivity;
import static pe.edu.upc.incident.IncidentApp.*;

public class SelectActivity extends AppCompatActivity {
    private static String TAG = "CatchUp";
    private static String username = "drodriguez";
    private static String cType = "";
    private static String cId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);


    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton8:
                try {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(SelectActivity.this, "" , Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                break;
            default:
                AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Theme_AppCompat_DayNight));
                builder.setTitle( ((Button)view).getText().toString());
                cType = ((Button)view).getText().toString();
                builder.setMessage("Ingrese el motivo de su llamada. Si la direccion indicada no es correcta, puede cambiarla");
                LinearLayout layout = new LinearLayout(this);
                LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setLayoutParams(parms);

                layout.setGravity(Gravity.CLIP_VERTICAL);
                layout.setPadding(2, 2, 2, 2);


                final EditText et = new EditText(this);
                et.setTextSize(20);
                et.setHeight(200);

                final EditText et2 = new EditText(this);
                et2.setTextSize(20);
                et2.setHeight(200);
                et2.setText("Av. Primavera 2400");


                layout.addView(et, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                layout.addView(et2, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                builder.setView(layout);


                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {


                                    AndroidNetworking.post(IncidentApi.CONVERSATION_URL)
                                            .addQueryParameter("text", et.getText().toString())
                                            .addQueryParameter("username", username)
                                            .addQueryParameter("type", cType)
                                            .addQueryParameter("address", et2.getText().toString())
                                            .addHeaders("cookie","uname="+username+ "; path=/;admin=0; path=/;")
                                            .setPriority(Priority.LOW)
                                            .setTag(TAG)
                                            .build()
                                            .getAsJSONObject(new JSONObjectRequestListener() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    try {
                                                        cId = response.getString("id");
                                                        startMessages(cId);
                                                    } catch (Exception e  ) {
                                                        Toast.makeText(SelectActivity.this, "error", Toast.LENGTH_LONG).show();
                                                        e.printStackTrace();
                                                    }


                                                }

                                                @Override
                                                public void onError(ANError anError) {
                                                    Toast.makeText(SelectActivity.this, anError.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                                    Log.d(TAG, anError.getLocalizedMessage());
                                                }

                                            });




                                    // User clicked OK button
                                        }
                        });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
        }
    }

    public void startMessages(String cId){
        Incident incident = new Incident();
        incident.setId(cId);
        getInstance().setCurrentSource(incident);
        try {
            Intent intent = new Intent(getApplicationContext(), MessagesActivity.class);
            intent.putExtra("cid",cId);
            startActivity(intent);

        } catch (Exception e) {
            Toast.makeText(SelectActivity.this, "" , Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }




    }




}
