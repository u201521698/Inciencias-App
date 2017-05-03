package pe.edu.upc.incident.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import pe.edu.upc.incident.IncidentApp;
import pe.edu.upc.incident.R;
import pe.edu.upc.incident.activities.MessagesActivity;
import pe.edu.upc.incident.models.Incident;

/**
 * Created by proyecto on 10/04/2017.
 */

public class IncidentsAdapter extends RecyclerView.Adapter<IncidentsAdapter.ViewHolder> {
    private List<Incident> incidents;
    @Override
    public IncidentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_source,parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IncidentsAdapter.ViewHolder holder, final int position) {
        holder.nameTextView.setText(incidents.get(position).getName());
        holder.descriptionTextView.setText(incidents.get(position).getDescription());
        holder.dateTimeTextView.setText(incidents.get(position).getDateTime());
        holder.sourceCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncidentApp
                        .getInstance()
                        .setCurrentSource(incidents.get(position));
                v.getContext().startActivity(
                        new Intent(
                            v.getContext(),
                            MessagesActivity.class));
            }
        });

//        holder.pictureImageView.setImageResource(R.drawable.huppic1);
if(position!=-1) {
    String base64str;
    base64str = incidents.get(position).getLanguage();
    base64str = base64str.substring(base64str.indexOf(",") + 1);

    byte[] decodedString = Base64.decode(base64str, Base64.DEFAULT);
    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

    try {
        holder.pictureImageView.setImageBitmap(decodedByte);
    } catch (Exception e) {
        e.printStackTrace();
    }

}

    }

    @Override
    public int getItemCount() {
        return incidents.size();
    }

    public List<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(List<Incident> incidents) {
        this.incidents = incidents;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView sourceCardView;
        TextView nameTextView;
        TextView descriptionTextView;
        TextView dateTimeTextView;
        ImageView pictureImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            sourceCardView = (CardView) itemView.findViewById(R.id.sourceCardView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            descriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
            dateTimeTextView = (TextView) itemView.findViewById(R.id.dateTimeTextView);
            pictureImageView= (ImageView) itemView.findViewById(R.id.imageView3);
        }
    }
}
