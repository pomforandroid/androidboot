package skankhunt.com.androidboot.di;

import android.content.Context;

import java.io.File;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;


/**
 * Created by skankhunt on 2018/1/19.
 */


@Module(includes = {ContextModule.class,ActivityModule.class})
public class NetWorkModule {

    @Provides
    @GithubApplicationScope
    public Cache cache(File cacheFile){
        return new Cache(cacheFile,10*1000*1000);
    }

    @Provides
    @GithubApplicationScope
    //public File cacheFile(@Named("Activity_context")Context context){
    public File cacheFile(@ApplicationContext Context context){
        return new File(context.getCacheDir(),"okhttp_cache");
    }

    @Provides
    @GithubApplicationScope
    public OkHttpClient okHttpClient(Cache cache){
        return new OkHttpClient.Builder()
                .cache(cache)
                .build();
    }
}
