package skankhunt.com.androidboot.dagger;

import dagger.Subcomponent;
import skankhunt.com.androidboot.AActivity;

/**
 * Created by wupengcong on 2018/1/21.
 */

@AScope
@Subcomponent(modules = AModule.class)
public interface AComponent {
    void inject(AActivity activity);
}
