package com.example.quwiapp.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.avatarfirst.avatargenlib.AvatarConstants;
import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.bumptech.glide.Glide;
import com.example.quwiapp.R;
import com.example.quwiapp.adapter.UserAdapter;
import com.example.quwiapp.api.ApiClient;
import com.example.quwiapp.api.ApiInterface;
import com.example.quwiapp.databinding.ActivityProjectDetailBinding;
import com.example.quwiapp.databinding.EditPopupDailogBinding;
import com.example.quwiapp.model.ProjectDetailModel;
import com.example.quwiapp.utils.AlertDailogView;
import com.example.quwiapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ProjectDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityProjectDetailBinding binding;

    int id;
    private String token;

    private List<ProjectDetailModel.Project> projectList;
    private List<ProjectDetailModel.User> userList;

    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_project_detail);
        projectList = new ArrayList<>();

        binding.headerLayout.imgLogout.setVisibility(View.GONE);
        binding.headerLayout.tvHeaderTitle.setText(R.string.project_detail);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recycleruser.setLayoutManager(linearLayoutManager);
        projectList = new ArrayList<>();
        userList = new ArrayList<>();

        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey("id")) {
                id = getIntent().getIntExtra("id", 0);
            }
        }

        if (Utils.isContainPreference(this, Utils.UserToken)) {
            token = Utils.getStringPreference(this, Utils.UserToken, "");
        }

        getProjectListDetailAPI(token);

        binding.headerLayout.imgBack.setOnClickListener(this);
        binding.imgEdit.setOnClickListener(this);

    }

    @SuppressLint("CheckResult")
    private void getProjectListDetailAPI(String token) {

        if (Utils.isOnline(this)) {
            ApiInterface apiInterface = ApiClient.getClient(this).create(ApiInterface.class);

            projectList.clear();
            Utils.showProgress(this);
            apiInterface.getProjectDetailAPI("Bearer " + token, String.valueOf(id))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new Observer<Response<ProjectDetailModel>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(Response<ProjectDetailModel> responseBodyResponse) {
                            Utils.hideProgress();
                            setupView(responseBodyResponse.body());
                        }

                        @Override
                        public void onError(Throwable e) {
                            Utils.hideProgress();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } else {
            AlertDailogView.showAlert(this,
                    getString(android.R.string.dialog_alert_title),
                    getString(R.string.please_check_your_internet_connection),
                    getString(R.string.ok), null).show();
        }
    }


    @SuppressLint("CheckResult")
    private void editProjectDetailAPI(String projectId, String token, String text) {

        if (Utils.isOnline(this)) {
            ApiInterface apiInterface = ApiClient.getClient(this).create(ApiInterface.class);

            projectList.clear();
            Utils.showProgress(this);
            apiInterface.editProjectDetailAPI("Bearer " + token, projectId, text)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new Observer<Response<ProjectDetailModel>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(Response<ProjectDetailModel> responseBodyResponse) {
                            Utils.hideProgress();
                            Log.v("..Response..", "..code.." + responseBodyResponse.code());
                            setupView(responseBodyResponse.body());
                        }

                        @Override
                        public void onError(Throwable e) {
                            Utils.hideProgress();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } else {
            AlertDailogView.showAlert(this,
                    getString(android.R.string.dialog_alert_title),
                    getString(R.string.please_check_your_internet_connection),
                    getString(R.string.ok), null).show();
        }
    }


    public void setupView(ProjectDetailModel projectDetailModel) {

        if (projectDetailModel != null) {

            Glide.with(this)
                    .load(projectDetailModel.getProject().getLogoUrl()).circleCrop()
                    .placeholder(AvatarGenerator.Companion.avatarImage(this, 200, AvatarConstants.Companion.getCIRCLE(), projectDetailModel.getProject().getName()))
                    .into(binding.imgPropic);
            binding.txtName.setText(projectDetailModel.getProject().getName());
            binding.imgEdit.setBackgroundResource(R.drawable.ic_edit_black_24dp);
            binding.lblTitle.setText("Workers");

            if (projectDetailModel.getProject().getUsers().size() != 0) {
                binding.llTitle.setVisibility(View.GONE);
                binding.recycleruser.setVisibility(View.VISIBLE);
                userList.clear();
                userList.addAll(projectDetailModel.getProject().getUsers());
                userAdapter = new UserAdapter(ProjectDetailActivity.this, userList);
                binding.recycleruser.setAdapter(userAdapter);
            } else {
                binding.recycleruser.setVisibility(View.GONE);
                binding.llTitle.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;

            case R.id.img_edit:
                editProjectAlert(ProjectDetailActivity.this, String.valueOf(id), token, binding.txtName.getText().toString()).show();
                break;
        }
    }


    public Dialog editProjectAlert(final Context context, final String projectId, final String token, String title) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final EditPopupDailogBinding editPopupDailogBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.edit_popup_dailog, null, false);
        dialog.setContentView(editPopupDailogBinding.getRoot());
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.90);
        dialog.getWindow().setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        editPopupDailogBinding.etName.setText(title);
        editPopupDailogBinding.etName.setSelection(editPopupDailogBinding.etName.getText().length());


        editPopupDailogBinding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        editPopupDailogBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        editPopupDailogBinding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(editPopupDailogBinding.etName.getText().toString().trim())) {
                    dialog.dismiss();
                    editProjectDetailAPI(projectId, token, editPopupDailogBinding.etName.getText().toString().trim());
                }
            }
        });


        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                }
                return true;
            }
        });

        return dialog;
    }

    public boolean validate(String name) {
        if (name.isEmpty()) {
            AlertDailogView.showAlert(this,
                    getString(android.R.string.dialog_alert_title),
                    getString(R.string.error_name),
                    getString(R.string.ok), null).show();
            return false;
        }
        return true;
    }


}
