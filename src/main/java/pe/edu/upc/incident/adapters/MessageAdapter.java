package pe.edu.upc.incident.adapters;

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

import pe.edu.upc.incident.R;
import pe.edu.upc.incident.models.Message;

/**
 * Created by proyecto on 17/04/2017.
 */

public class MessageAdapter extends
        RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<Message> messages;

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_message, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, final int position) {
//        holder.pictureANImageView.setErrorImageResId(R.mipmap.ic_launcher);
//        holder.pictureANImageView.setDefaultImageResId(R.mipmap.ic_launcher);
//        holder.pictureANImageView.setImageUrl(messages.get(position).getUrlToImage());
        holder.titleTextView.setText(messages.get(position).getTitle());
        holder.dateTimeTextView.setText(messages.get(position).getPublishedAt());
        holder.descriptionTextView.setText(messages.get(position).getDescription());
        if(position!=-1) {
            String base64str;
            base64str = messages.get(position).getUrlToImage();
            base64str = base64str.substring(base64str.indexOf(",") + 1);

            byte[] decodedString = Base64.decode(base64str, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            try {
                holder.pictureANImageView.setImageBitmap(decodedByte);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView articleCardView;
        TextView titleTextView;
        TextView dateTimeTextView;
        TextView descriptionTextView;
        ImageView pictureANImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            dateTimeTextView = (TextView) itemView.findViewById(R.id.dateTimeTextView);
            descriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
            pictureANImageView = (ImageView) itemView.findViewById(R.id.pictureANImageView);
            articleCardView = (CardView) itemView.findViewById(R.id.articleCardView);
        }
    }
}
