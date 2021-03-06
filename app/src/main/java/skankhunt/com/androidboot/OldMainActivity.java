package skankhunt.com.androidboot;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import skankhunt.com.androidboot.models.User;
import skankhunt.com.androidboot.ui.UserViewModel;

public class OldMainActivity extends AppCompatActivity  implements HasSupportFragmentInjector {

    private TextView mTextView;

//    @Inject
//    GithubService githubService;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);

        mTextView = (TextView) findViewById(R.id.tv_poetry);
        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserViewModel.class);
        userViewModel.getUser().observe(this,userResource -> {
            User data = userResource.data;
            //一定要判断是否为null
            if(data!=null){
              mTextView.setText(data.getName());
            }
        });
    }


    private void initView() {



    }

//    @Inject
//    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return null;
    }
}
