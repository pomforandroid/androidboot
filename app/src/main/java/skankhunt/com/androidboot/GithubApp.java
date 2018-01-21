package skankhunt.com.androidboot;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import javax.inject.Inject;

import skankhunt.com.androidboot.di.ContextModule;
import skankhunt.com.androidboot.di.DaggerGithubApplicationComponet;
import skankhunt.com.androidboot.di.GithubApplicationComponet;
import skankhunt.com.androidboot.di.GithubService;
import skankhunt.com.androidboot.di.GithubServiceModule;
import skankhunt.com.androidboot.di.NetWorkModule;

/**
 * Created by skankhunt on 2018/1/19.
 */

public class GithubApp extends Application{

    private GithubApplicationComponet componet;
    private GithubService githubService;

    public static GithubApp get(Activity activity) {
        return (GithubApp) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //创建GithubApplicationComponent的实例
//        componet = DaggerGithubApplicationComponet.builder()
//                .contextModule(new ContextModule(this))
//                .githubServiceModule(new GithubServiceModule())
//                .netWorkModule(new NetWorkModule())
//                .build();
        //事实上Dagger只需要传入一个ContextModule就行了，其余的Module都会自动帮你生成
        componet = DaggerGithubApplicationComponet.builder()
                .contextModule(new ContextModule(this))
                .build();
        //测试得出，每个新建得githubservice都是不同的对象
        //添加@GithubApplicationScope后才能获得同一个对象
        githubService = componet.getGithubService();
        Log.e("TAG","githubService:"+ githubService);
        githubService = componet.getGithubService();
        Log.e("TAG","githubService:"+ githubService);
        githubService = componet.getGithubService();
        Log.e("TAG","githubService:"+ githubService);



    }
    public GithubApplicationComponet component() {
        return componet;
    }
}
