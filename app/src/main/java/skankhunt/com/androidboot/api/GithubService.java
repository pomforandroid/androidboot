package skankhunt.com.androidboot.api;

import android.arch.lifecycle.LiveData;

import retrofit2.Call;
import retrofit2.http.POST;
import skankhunt.com.androidboot.models.User;

/**
 * Created by skankhunt on 2018/1/19.
 */

public interface GithubService {

//    @POST("order/myorder")
//    Call<MyOrder> getOrder( );

     @POST("user")
     LiveData<ApiResponse<User>> getUser();

     @POST("user")
     LiveData<User> getdUser();

}
