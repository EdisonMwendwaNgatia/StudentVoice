package com.example.studentvoice;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private final boolean isAdmin;

    public ComplaintAdapter(List<Complaint> complaintList, boolean isAdmin) {
        this.complaintList = complaintList;
        this.isAdmin = isAdmin; // Set admin status
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
        holder.tvLikesCount.setText(complaint.getLikes() + " likes");

        // Show or hide delete button based on admin status
        if (isAdmin) {
            holder.btnDeleteComplaint.setVisibility(View.VISIBLE);
            holder.btnDeleteComplaint.setOnClickListener(v -> {
                // Prompt for admin password before deletion
                showDeleteConfirmationDialog(holder, complaintId);
            });
        } else {
            holder.btnDeleteComplaint.setVisibility(View.GONE);
        }

        // Handling likes (existing code)
        boolean isLiked = likedComplaints.containsKey(complaintId) && Boolean.TRUE.equals(likedComplaints.get(complaintId));
        if (isLiked) {
            holder.btnLike.setText(R.string.unlike);
        } else {
            holder.btnLike.setText(R.string.like);
        }

        holder.btnLike.setOnClickListener(v -> {
            if (isLiked) {
                // User has already liked the complaint, so unlike it
                int newLikes = complaint.getLikes() - 1;
                complaint.setLikes(newLikes);
                holder.tvLikesCount.setText(newLikes + " likes");
                updateLikesInFirebase(complaintId, newLikes);
                likedComplaints.remove(complaintId);
            } else {
                // User has not yet liked the complaint, so like it
                int newLikes = complaint.getLikes() + 1;
                complaint.setLikes(newLikes);
                holder.tvLikesCount.setText(newLikes + " likes");
                updateLikesInFirebase(complaintId, newLikes);
                likedComplaints.put(complaintId, true);
            }
            holder.btnLike.setText(isLiked ? R.string.like : R.string.unlike);
        });
    }

    private void updateLikesInFirebase(String complaintId, int newLikes) {
        DatabaseReference complaintRef = FirebaseDatabase.getInstance().getReference("complaints").child(complaintId);
        complaintRef.child("likes").setValue(newLikes);
    }

    private void showDeleteConfirmationDialog(ComplaintViewHolder holder, String complaintId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
        builder.setTitle("Admin Password Required");
        EditText input = new EditText(holder.itemView.getContext());
        builder.setView(input);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String password = input.getText().toString();
            if (password.equals("12345")) { // Hardcoded password
                DatabaseReference complaintRef = FirebaseDatabase.getInstance().getReference("complaints").child(complaintId);
                complaintRef.removeValue(); // Delete the complaint
                Toast.makeText(holder.itemView.getContext(), "Complaint deleted", Toast.LENGTH_SHORT).show();
                // Optionally, you can refresh the complaint list here if necessary
            } else {
                Toast.makeText(holder.itemView.getContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }


    @Override
    public int getItemCount() {
        return complaintList.size();
    }

    public static class ComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView tvComplaintType, tvComplaintDetails, tvLikesCount;
        Button btnLike, btnDeleteComplaint; // Add the delete button

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            tvComplaintType = itemView.findViewById(R.id.tvComplaintType);
            tvComplaintDetails = itemView.findViewById(R.id.tvComplaintDetails);
            tvLikesCount = itemView.findViewById(R.id.tvLikesCount);
            btnLike = itemView.findViewById(R.id.btnLike);
            btnDeleteComplaint = itemView.findViewById(R.id.btnDelete); // Initialize delete button
        }
    }
}
