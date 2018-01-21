package skankhunt.com.androidboot.di_old;

import android.app.Activity;
import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by skankhunt on 2018/1/19.
 */


@Module
public class ActivityModule {
    //如果不标明的话，因为ActivityModule和ContextModule都是返回Context类型的，不区分会报错
    private final Activity context;

    public ActivityModule(Activity context){
        this.context = context;
    }

    @Provides
    @GithubApplicationScope
    @Named("Activity_context")
    public Context context(){
        return context;
    }


}
