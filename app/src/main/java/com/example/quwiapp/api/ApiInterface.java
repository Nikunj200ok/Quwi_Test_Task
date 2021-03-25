package com.example.quwiapp.api;

import com.example.quwiapp.model.ProjectDetailModel;
import com.example.quwiapp.model.ProjectListModel;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("auth/login")
    Observable<Response<ResponseBody>> postLogin(@Field("email") String email,
                                                 @Field("password") String password);

    @GET("projects-manage/index")
    Observable<Response<ProjectListModel>> getProjectAPI(@Header("Authorization") String token);

    @GET("projects-manage/{user_id}")
    Observable<Response<ProjectDetailModel>> getProjectDetailAPI(@Header("Authorization") String token,
                                                                 @Path(value = "user_id", encoded = true) String userId);

    @FormUrlEncoded
    @POST("projects-manage/update")
    Observable<Response<ProjectDetailModel>> editProjectDetailAPI(@Header("Authorization") String token,
                                                                  @Query("id") String id,
                                                                  @Field("name") String name);

}