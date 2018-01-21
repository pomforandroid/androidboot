package skankhunt.com.androidboot.dagger;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wupengcong on 2018/1/21.
 */

@Module
public class AModule {

    @Named("A")
    @AScope
    @Provides
    public Poetry getPoetry(){
        return new Poetry("万物美好");
    }

    @Named("B")
    @AScope
    @Provides
    public Poetry getOtherPoetry(){
        return new Poetry("我在中间");
    }
}
