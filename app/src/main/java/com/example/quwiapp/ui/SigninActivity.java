package com.example.quwiapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.quwiapp.R;
import com.example.quwiapp.api.ApiClient;
import com.example.quwiapp.api.ApiInterface;
import com.example.quwiapp.databinding.ActivitySigninBinding;
import com.example.quwiapp.utils.AlertDailogView;
import com.example.quwiapp.utils.Utils;

import org.json.JSONObject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener{

    ActivitySigninBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signin);

        binding.btnSignin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_signin:
                Utils.hideKeyboard(SigninActivity.this);
                if (Utils.isOnline(SigninActivity.this)){
                    if (validate()){
                        loginAPI(binding.etEmail.getText().toString(), binding.etPassword.getText().toString());
                    }
                } else{
                    AlertDailogView.showAlert(SigninActivity.this,
                            getString(android.R.string.dialog_alert_title),
                            getString(R.string.please_check_your_internet_connection),
                            getString(R.string.ok), null).show();
                }
                break;
        }
    }

    public boolean validate() {

        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            AlertDailogView.showAlert(this,
                    getString(android.R.string.dialog_alert_title),
                    getString(R.string.error_email),
                    getString(R.string.ok), null).show();
            return false;
        }

        if (!Utils.isValidEmail(email)) {
            AlertDailogView.showAlert(this,
                    getString(android.R.string.dialog_alert_title),
                    getString(R.string.error_email_valid),
                    getString(R.string.ok), null).show();
            return false;
        }

        if (password.isEmpty()) {
            AlertDailogView.showAlert(this,
                    getString(android.R.string.dialog_alert_title),
                    getString(R.string.error_password),
                    getString(R.string.ok), null).show();
            return false;
        }

        return true;
    }


    @SuppressLint("CheckResult")
    private void loginAPI(final String email, final String password) {

        if (Utils.isOnline(this)) {
            ApiInterface apiInterface = ApiClient.getClient(this).create(ApiInterface.class);


            Utils.showProgress(this);
            apiInterface.postLogin(email, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new Observer<Response<ResponseBody>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Response<ResponseBody> responseBodyResponse) {
                            Utils.hideProgress();
                            if(responseBodyResponse.isSuccessful()){
                                try {
                                    String response_str = responseBodyResponse.body().string();
                                    JSONObject response_json = new JSONObject(response_str);

                                    String Token = response_json.getString(ApiClient.API_TOKEN);

                                    Utils.setStringPreference(SigninActivity.this, Utils.UserToken, Token);

                                    startActivity(new Intent(SigninActivity.this, ProjectListActivity.class));
                                    finish();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }else {
                                try {
                                    String error_str = responseBodyResponse.errorBody().string();
                                    JSONObject error_json = new JSONObject(error_str);
                                    JSONObject error_json1 = new JSONObject(error_json.getString(ApiClient.MESSAGE));


                                    AlertDailogView.showAlert(SigninActivity.this,
                                            getString(android.R.string.dialog_alert_title),
                                            error_json1.getString(ApiClient.MESSAGE1),
                                            getString(R.string.ok), null).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Utils.hideProgress();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        }else{
            AlertDailogView.showAlert(this,
                    getString(android.R.string.dialog_alert_title),
                    getString(R.string.please_check_your_internet_connection),
                    getString(R.string.ok), null).show();
        }
    }


}
