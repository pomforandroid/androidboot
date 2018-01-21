package skankhunt.com.androidboot.dagger;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wupengcong on 2018/1/21.
 */

@Module
public  class MainModule {

    /**
     * @provides 注解便是这个方法是用来创建某个实例对象的，这里我们能创建返回Gson对象
     * 方法名随便，一般用provideXXX结构
     * @return 返回注入对象
     */
//
//    @Provides
//    public Gson provideGson(){
//        return new Gson();
//    }
}
