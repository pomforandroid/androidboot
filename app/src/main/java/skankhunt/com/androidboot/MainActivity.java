package skankhunt.com.androidboot;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import skankhunt.com.androidboot.db.LoginDao;
import skankhunt.com.androidboot.models.User;
import skankhunt.com.androidboot.ui.common.NavigationController;
import skankhunt.com.androidboot.ui.login.LoginViewModel;
import skankhunt.com.androidboot.vo.Login;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    NavigationController navigationController;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private LoginViewModel loginViewModel;

    @Inject
    LoginDao loginDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);

            loginViewModel.getLoginByDao().observe(this, new Observer<Login>() {
                @Override
                public void onChanged(@Nullable Login login) {
                    if(login!=null){
                        boolean loginState = login.loginState;
                        if(loginState){
                            navigationController.navigateToMain();
                        }else{
                            navigationController.navigateToLogin();
                        }
                    }else {
                        navigationController.navigateToLogin();
                    }
                }
            });


//            loginViewModel.getLoginByDao().observe(this, new Observer<Login>() {
//                @Override
//                public void onChanged(@Nullable Login login) {
//                    if(login!=null){
//                        boolean loginState = login.loginState;
//                        if(loginState){
//                            navigationController.navigateToMain();
//                        }else {
//                            navigationController.navigateToLogin();
//                        }
//                    }
//                }
//            });

        }/*else {
            loginViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
            loginViewModel.getIsLogin().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    if(aBoolean!=null){
                        if(aBoolean){
                            navigationController.navigateToMain();
                        }else {
                            navigationController.navigateToLogin();
                        }
                    }else{
                        navigationController.navigateToLogin();
                    }
                }
            });
        }*/
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
