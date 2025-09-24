package com.example.examen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<UserInfo> users;
    private OnUserClickListener listener;

    public interface OnUserClickListener {
        void onUserClick(UserInfo userInfo);
    }

    public UserAdapter(List<UserInfo> userInfo, OnUserClickListener listener) {
        this.users = userInfo;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserInfo user = users.get(position);
        holder.tvName.setText(user.name.title + " " + user.name.first + " " + user.name.last);
        holder.tvCountry.setText(user.location.country);
        holder.tvEmail.setText(user.email);
        holder.tvPhone.setText(user.phone); // Nuevo
        Picasso.get().load(user.picture.thumbnail).into(holder.ivPhoto);
        holder.itemView.setOnClickListener(v -> listener.onUserClick(user));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        TextView tvName, tvCountry, tvEmail, tvPhone;

        ViewHolder(View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvName = itemView.findViewById(R.id.tvName);
            tvCountry = itemView.findViewById(R.id.tvCountry);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
        }
    }
}
