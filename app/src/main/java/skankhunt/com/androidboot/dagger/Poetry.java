package skankhunt.com.androidboot.dagger;

import javax.inject.Inject;

/**
 * Created by skankhunt on 2018/1/21.
 */

public class Poetry {

    private String mPemo;

    //用Inject标记构造函数，表示用它来注入到目标对象中去
    @Inject
    public  Poetry(){
        mPemo = "生活就像海洋";
    }

    public String getmPemo(){
        return mPemo;
    }
}
