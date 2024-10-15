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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder> {
    private final List<Complaint> complaintList;
    private final Map<String, Boolean> likedComplaints = new HashMap<>();

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

        String complaintId = complaint.getId();

        holder.tvComplaintType.setText(complaint.getType());
        holder.tvComplaintDetails.setText(complaint.getDetails());
        holder.tvLikesCount.setText(complaint.getLikes() + "Likes");

        boolean isLiked = likedComplaints.containsKey(complaintId) && likedComplaints.get(complaintId);
        if (isLiked) {
            holder.btnLike.setText("unlike");
        } else {
            holder.btnLike.setText("like");
        }

        holder.btnLike.setOnClickListener(v -> {
            if (isLiked) {
                // User has already liked the complaint, so unlike it
                int newLikes = complaint.getLikes() - 1;
                complaint.setLikes(newLikes);
                holder.tvLikesCount.setText(newLikes + " Likes");

                // Update the likes in Firebase
                DatabaseReference complaintRef = FirebaseDatabase.getInstance().getReference("complaints").child(complaintId);
                complaintRef.child("likes").setValue(newLikes);

                // Remove the complaint from the likedComplaints map
                likedComplaints.remove(complaintId);
            } else {
                // User has not yet liked the complaint, so like it
                int newLikes = complaint.getLikes() + 1;
                complaint.setLikes(newLikes);
                holder.tvLikesCount.setText(newLikes + " Likes");

                // Update the likes in Firebase
                DatabaseReference complaintRef = FirebaseDatabase.getInstance().getReference("complaints").child(complaintId);
                complaintRef.child("likes").setValue(newLikes);

                // Add the complaint to the likedComplaints map
                likedComplaints.put(complaintId, true);
            }

            // Update the button text
            if (isLiked) {
                holder.btnLike.setText("Like");
            } else {
                holder.btnLike.setText("Unlike");
            }
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
