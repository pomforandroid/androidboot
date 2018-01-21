package skankhunt.com.androidboot.dagger;

import dagger.Component;
import skankhunt.com.androidboot.GithubApp;
import skankhunt.com.androidboot.MainActivity;
import skankhunt.com.androidboot.OtherActivity;

/**
 * Created by wupengcong on 2018/1/21.
 */
//这里表示Component会从MainModule类中拿到那些用@provides注解的方法来生成需要注入的实例
    //@module的优先级比@inject要高

@PoetryScope
//@Component(modules ={ MainModule.class,PoetryModule.class})
@Component(dependencies = ApplicationComponent.class, modules = {MainModule.class,PoetryModule.class})
public abstract class MainComponent {
    /**
     * 需要用到这个连接器的对象，就是这个对象里面有需要注入的属性
     * （被标记为@Inject的属性）
     * 这里inject表示注入的意思，这个方法名可以随意更改，但建议就
     * 用inject即可。
     */
    public abstract void inject(MainActivity Activity);

    public abstract void inject(OtherActivity Activity);

    private static MainComponent sComponent;

    public static MainComponent getInstance(){
        if(sComponent == null){
            //sComponent = DaggerMainComponent.builder().build();
            if (sComponent == null){
                sComponent = DaggerMainComponent.builder()
                        .applicationComponent(GithubApp.getInstance()
                                .getApplicationComponent())
                        .build();
            }
        }
        return sComponent;

    }
}
