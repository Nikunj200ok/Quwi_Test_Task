package com.example.quwiapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.avatarfirst.avatargenlib.AvatarConstants;
import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.bumptech.glide.Glide;
import com.example.quwiapp.R;
import com.example.quwiapp.databinding.RowProjectLayoutBinding;
import com.example.quwiapp.model.ProjectListModel;
import com.example.quwiapp.ui.ProjectDetailActivity;
import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.MyViewHolder>  {

    private Context context;
    private List<ProjectListModel.Project> projectList;
    private LayoutInflater layoutInflater;

    public ProjectAdapter(Context context, List<ProjectListModel.Project> projectList) {
        this.context = context;
        this.projectList = projectList;
    }

    @NonNull
    @Override
    public ProjectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RowProjectLayoutBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.row_project_layout, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectAdapter.MyViewHolder holder, final int position) {
        final ProjectListModel.Project project = projectList.get(position);
        Glide.with(context)
                .load(project.getLogoUrl()).circleCrop()
                .placeholder(AvatarGenerator.Companion.avatarImage(context, 200, AvatarConstants.Companion.getCIRCLE(), project.getName()))
                .into(holder.binding.imgPropic);
        holder.binding.txtName.setText(project.getName());

        holder.binding.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ProjectDetailActivity.class)
                        .putExtra("id", project.getId()).
                                putExtra("name", project.getName()));
            }
        });


    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private RowProjectLayoutBinding binding;

        public MyViewHolder(@NonNull RowProjectLayoutBinding rowProjectLayoutBinding) {
            super(rowProjectLayoutBinding.getRoot());
            this.binding = rowProjectLayoutBinding;
        }
    }
}
