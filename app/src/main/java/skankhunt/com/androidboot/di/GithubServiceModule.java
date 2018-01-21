package skankhunt.com.androidboot.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by skankhunt on 2018/1/19.
 */

@Module(includes = NetWorkModule.class)
public class GithubServiceModule {

    @Provides
    @GithubApplicationScope
    public GithubService githubService(Retrofit githubRetrofit){
        return githubRetrofit.create(GithubService.class);
    }

    /*@Provides
    public Gson gson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(DateTime.class,new DatetimeConverter());
        return gsonBuilder.create();
    }*/

    @Provides
    @GithubApplicationScope
    public Retrofit retrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
//                .baseUrl("https://srv.swzcloud.com/motor/")
                .baseUrl("http://wxplay.swzcn.com/hello/")

                .build();

    }
}
