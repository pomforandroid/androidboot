package skankhunt.com.androidboot.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by skankhunt on 2018/1/19.
 */


@Module
public class ContextModule {

    private final Context context;

    public ContextModule(Context context){
        this.context = context.getApplicationContext();
    }

    @Provides
    @GithubApplicationScope
    @ApplicationContext
    public Context context(){
        return context;
    }


}
