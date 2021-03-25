package com.example.quwiapp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.quwiapp.R;
import com.example.quwiapp.adapter.ProjectAdapter;
import com.example.quwiapp.api.ApiClient;
import com.example.quwiapp.api.ApiInterface;
import com.example.quwiapp.databinding.ActivityProjectListBinding;
import com.example.quwiapp.model.ProjectListModel;
import com.example.quwiapp.utils.AlertDailogView;
import com.example.quwiapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ProjectListActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{

    ActivityProjectListBinding binding;

    String token;

    private List<ProjectListModel.Project> projectList;

    ProjectAdapter projectAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_project_list);

        if(Utils.isContainPreference(this, Utils.UserToken)){
            token = Utils.getStringPreference(this, Utils.UserToken, "");
        }

        binding.headerLayout.imgBack.setVisibility(View.GONE);
        binding.headerLayout.tvHeaderTitle.setText(R.string.project_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.recyclerFiles.setLayoutManager(linearLayoutManager);
        projectList = new ArrayList<>();

        binding.headerLayout.imgLogout.setOnClickListener(this);
        binding.swipeContainer.setOnRefreshListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getProjectListAPI(token, false);
    }

    @SuppressLint("CheckResult")
    private void getProjectListAPI(String token, final boolean isPullToRefresh) {

        if (Utils.isOnline(this)) {
            ApiInterface apiInterface = ApiClient.getClient(this).create(ApiInterface.class);

            projectList.clear();
            Utils.showProgress(this);
            if(isPullToRefresh){
                binding.swipeContainer.setRefreshing(true);
            }
            apiInterface.getProjectAPI("Bearer " + token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new Observer<Response<ProjectListModel>>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(Response<ProjectListModel> responseBodyResponse) {
                            Utils.hideProgress();
                            if(isPullToRefresh){
                                binding.swipeContainer.setRefreshing(false);
                            }
                            Log.v("..Response..", "..code.." + responseBodyResponse.code());
                            setupView(responseBodyResponse.body());
                        }

                        @Override
                        public void onError(Throwable e) {
                            Utils.hideProgress();
                            if(isPullToRefresh){
                                binding.swipeContainer.setRefreshing(false);
                            }
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


    public void setupView(ProjectListModel projectListModel) {

        if (projectListModel.getProjects() != null) {
            projectList.clear();
            projectList.addAll(projectListModel.getProjects());
            projectAdapter = new ProjectAdapter(ProjectListActivity.this, projectList);
            binding.recyclerFiles.setAdapter(projectAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_logout:
                Utils.logoutUser(ProjectListActivity.this);
                break;
        }
    }

    @Override
    public void onRefresh() {
        getProjectListAPI(token, true);
    }
}
