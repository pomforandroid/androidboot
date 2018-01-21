package skankhunt.com.androidboot.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wupengcong on 2018/1/21.
 */

@Module
public class PoetryModule {

    //这个方法需要一个String参数，在Dagger2注入中，这些参数也是注入形式的，也就是
    //要有其他地方提供参数poeams的生成，不然会造成贬义出错
    @PoetryScope
    @Provides
    public Poetry providePoetry(String poems){
        return new Poetry(poems);
    }

    //这里提供了一个生成String的方法，在这个Module里生成poetry实例时，会查找到这里
    //可以为上面提供String类型的参数
    @PoetryScope
    @Provides
    public String providePoems(){
        return "只有意志坚强的人，才能到达彼岸";
    }

}
