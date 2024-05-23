package com.application.a1_sit305_91p;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdvertAdapter extends RecyclerView.Adapter<AdvertAdapter.AdvertViewHolder> {
    private List<Advert> advertList;
    private OnItemClickListener listener;

    public AdvertAdapter(List<Advert> advertList, OnItemClickListener listener) {
        this.advertList = advertList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdvertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new AdvertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertViewHolder holder, int position) {
        Advert advert = advertList.get(position);
        holder.bind(advert, listener);
        holder.typeTextView.setText(advert.getType());
        holder.nameTextView.setText(advert.getName());
        holder.phoneTextView.setText(advert.getPhone());
        holder.descriptionTextView.setText(advert.getDescription());
        holder.dateTextView.setText(advert.getDate());
        holder.locationTextView.setText(advert.getLocation());
    }

    @Override
    public int getItemCount() {
        return advertList.size();
    }

    public static class AdvertViewHolder extends RecyclerView.ViewHolder {
        TextView typeTextView;
        TextView nameTextView;
        TextView phoneTextView;
        TextView descriptionTextView;
        TextView dateTextView;
        TextView locationTextView;

        public AdvertViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTextView = itemView.findViewById(R.id.typeTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
        }

        public void bind(final Advert advert, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(advert);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Advert advert);
    }

    public void remove(Advert advert) {
        int position = advertList.indexOf(advert);
        if (position != -1) {
            advertList.remove(position);
            notifyItemRemoved(position);
        }
    }
}
