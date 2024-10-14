package com.example.studentvoice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {
    private final List<Complaint> complaintList;

    public ComplaintAdapter(List<Complaint> complaintList) {
        this.complaintList = complaintList;
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_item, parent, false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        Complaint complaint = complaintList.get(position);
        holder.tvComplaintType.setText(complaint.getType());
        holder.tvComplaintDetails.setText(complaint.getDetails());
        holder.tvLikesCount.setText(complaint.getLikes() + " Likes");

        holder.btnLike.setOnClickListener(v -> {
            int newLikes = complaint.getLikes() + 1;
            complaint.setLikes(newLikes);
            holder.tvLikesCount.setText(newLikes + " Likes");

            // Update the likes in Firebase
            DatabaseReference complaintRef = FirebaseDatabase.getInstance().getReference("complaints").child(complaint.getId());
            complaintRef.child("likes").setValue(newLikes);
        });
    }

    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    public static class ComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView tvComplaintType, tvComplaintDetails, tvLikesCount;
        Button btnLike;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            tvComplaintType = itemView.findViewById(R.id.tvComplaintType);
            tvComplaintDetails = itemView.findViewById(R.id.tvComplaintDetails);
            tvLikesCount = itemView.findViewById(R.id.tvLikesCount);
            btnLike = itemView.findViewById(R.id.btnLike);
        }
    }
}

