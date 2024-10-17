package com.example.studentvoice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddressedComplaintAdapter extends RecyclerView.Adapter<AddressedComplaintAdapter.ViewHolder> {
    private final List<AddressedComplaint> addressedComplaints;

    public AddressedComplaintAdapter(List<AddressedComplaint> addressedComplaints) {
        this.addressedComplaints = addressedComplaints;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Use your custom layout for the card
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_addressed_complaint, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddressedComplaint addressedComplaint = addressedComplaints.get(position);
        holder.tvComplaintDetails.setText(addressedComplaint.getDetails());
    }

    @Override
    public int getItemCount() {
        return addressedComplaints.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvComplaintId, tvComplaintDetails;

        ViewHolder(View itemView) {
            super(itemView);
            // Link the TextViews to your card layout
            tvComplaintDetails = itemView.findViewById(R.id.tvComplaintDetails);
        }
    }
}
