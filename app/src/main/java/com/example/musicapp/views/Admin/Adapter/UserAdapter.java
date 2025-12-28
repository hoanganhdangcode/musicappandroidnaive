package com.example.musicapp.views.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.DTO.UserDTO;
import com.example.musicapp.R;
import com.example.musicapp.commons.ItemLongClickListener;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final List<UserDTO> users = new ArrayList<>();
    private final Context context;
    private ItemLongClickListener<UserDTO> itemLongClickListener;

    public UserAdapter(Context context) {
        this.context = context;
    }
    public void SetItemListener(ItemLongClickListener<UserDTO> itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    public void setData(List<UserDTO> data) {
        users.clear();
        if (data != null) users.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserDTO u = users.get(position);

        holder.tvName.setText(u.getName());
        holder.tvEmail.setText(u.getEmail());
        holder.tvRole.setText(u.getRole());

        Glide.with(context)
                .load(u.getAvatarUrl())
                .placeholder(R.drawable.ic_more_vert)
                .error(R.drawable.ic_user)
                .into(holder.imgAvatar);
        holder.itemView.setOnLongClickListener(v->{
            if(itemLongClickListener !=null) itemLongClickListener.OnItemLongClick(u);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView tvName, tvEmail, tvRole;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvRole = itemView.findViewById(R.id.tvRole);
        }
    }
}

