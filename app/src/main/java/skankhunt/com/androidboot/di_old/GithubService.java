package skankhunt.com.androidboot.di_old;

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
     Call<User> getUser( );

}
