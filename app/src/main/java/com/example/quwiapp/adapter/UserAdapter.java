package com.example.quwiapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.avatarfirst.avatargenlib.AvatarConstants;
import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.bumptech.glide.Glide;
import com.example.quwiapp.R;
import com.example.quwiapp.databinding.RowProjectLayoutBinding;
import com.example.quwiapp.model.ProjectDetailModel;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private Context context;
    private List<ProjectDetailModel.User> userList;
    private LayoutInflater layoutInflater;

    public UserAdapter(Context context, List<ProjectDetailModel.User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RowProjectLayoutBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.row_project_layout, parent, false);
        return new UserAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {
        final ProjectDetailModel.User user = userList.get(position);
        Glide.with(context)
                .load(user.getAvatarUrl()).circleCrop()
                .placeholder(AvatarGenerator.Companion.avatarImage(context, 200, AvatarConstants.Companion.getCIRCLE(), user.getName()))
                .into(holder.binding.imgPropic);
        holder.binding.txtName.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowProjectLayoutBinding binding;

        public MyViewHolder(@NonNull RowProjectLayoutBinding rowProjectLayoutBinding) {
            super(rowProjectLayoutBinding.getRoot());
            this.binding = rowProjectLayoutBinding;
        }
    }
}
